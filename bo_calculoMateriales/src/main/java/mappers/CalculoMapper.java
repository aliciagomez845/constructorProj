/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import bo.ObraBO;
import dominio_entidades.Calculo;
import dominio_entidades.Elemento;
import dominio_entidades.MaterialCalculo;
import excepciones.NegocioException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio_dto.CalculoDTO;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_dto.ObraDTO;
import org.bson.types.ObjectId;

/**
 * Clase encargada de convertir entre las representaciones de Calculo.
 *
 * Esta clase facilita la transformación de datos entre capas, para que la
 * lógica de negocio trabaje con entidades y las capas externas usen DTOs.
 *
 * @author Alejandra García Preciado - 252444
 */
public class CalculoMapper {

    private static final Logger LOGGER = Logger.getLogger(CalculoMapper.class.getName());

    /**
     * Convierte un objeto Calculo de la capa de dominio a un CalculoDTO.
     *
     * @param calculo Objeto Calculo a convertir
     * @return Objeto CalculoDTO equivalente
     */
    public static CalculoDTO toDTO(Calculo calculo) {
        if (calculo == null) {
            LOGGER.warning("Intento de convertir un Calculo null a DTO");
            return null;
        }

        try {
            // Convertir la fecha
            LocalDate fecha = null;
            if (calculo.getFecha() != null) {
                fecha = calculo.getFecha().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            } else {
                LOGGER.warning("Cálculo sin fecha: " + (calculo.getId() != null ? calculo.getId().toHexString() : "ID null"));
            }

            // Convertir el elemento
            ElementoDTO elementoDTO = ElementoMapper.toDTO(calculo.getElemento());
            if (elementoDTO == null) {
                LOGGER.warning("No se pudo convertir el elemento del cálculo");
            }

            // Convertir la lista de materiales calculados
            List<MaterialCalculoDTO> materialesDTO = MaterialCalculoMapper.toDTOList(calculo.getMaterialesCalculados());
            if (materialesDTO == null || materialesDTO.isEmpty()) {
                LOGGER.warning("No se encontraron materiales calculados para el cálculo");
                materialesDTO = new ArrayList<>();
            }

            // Obtener la obra completa usando el BO
            ObraDTO obraDTO = null;
            if (calculo.getIdObra() != null) {
                try {
                    // Obtener la obra completa desde el BO
                    obraDTO = ObraBO.getInstance().obtenerObra(calculo.getIdObra().toHexString());
                    if (obraDTO != null) {
                        // Asegurar que el ID esté establecido
                        obraDTO.setId(calculo.getIdObra().toHexString());
                    }
                } catch (NegocioException ex) {
                    LOGGER.log(Level.WARNING, "No se pudo obtener la obra completa con ID: " + calculo.getIdObra().toHexString(), ex);
                    // Crear una obra con solo el ID si no pudo obtenerse
                    obraDTO = new ObraDTO();
                    obraDTO.setId(calculo.getIdObra().toHexString());
                    obraDTO.setDireccion("Dirección no disponible");
                }
            } else {
                LOGGER.warning("Cálculo sin ID de obra asociada");
                // Crear obra por defecto
                obraDTO = new ObraDTO();
                obraDTO.setDireccion("Obra no especificada");
            }

            // Calcular el volumen
            Double volumenCalculado = calcularVolumen(calculo.getElemento());

            CalculoDTO calculoDTO = new CalculoDTO(obraDTO, fecha, volumenCalculado, elementoDTO, materialesDTO);

            LOGGER.info("Conversión exitosa de Calculo a DTO");
            return calculoDTO;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al convertir Calculo a CalculoDTO", ex);
            return null;
        }
    }

    /**
     * Convierte un objeto CalculoDTO de la capa de negocio a un Calculo.
     *
     * @param dto Objeto CalculoDTO a convertir
     * @return Objeto Calculo equivalente
     */
    public static Calculo toEntity(CalculoDTO dto) {
        if (dto == null) {
            LOGGER.warning("Intento de convertir un CalculoDTO null a entidad");
            return null;
        }

        try {
            // Convertir la fecha
            Date fecha = null;
            if (dto.getFecha() != null) {
                fecha = Date.from(dto.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else {
                // Si no hay fecha, usar la fecha actual
                fecha = new Date();
                LOGGER.info("DTO sin fecha, usando fecha actual");
            }

            // Convertir el elemento
            Elemento elemento = ElementoMapper.toEntity(dto.getElemento());
            if (elemento == null) {
                LOGGER.warning("No se pudo convertir el elemento del DTO");
                throw new IllegalArgumentException("El elemento del cálculo es requerido");
            }

            // Convertir la lista de materiales calculados
            List<MaterialCalculo> materiales = MaterialCalculoMapper.toEntityList(dto.getMaterialesCalculados());
            if (materiales == null) {
                materiales = new ArrayList<>();
                LOGGER.warning("DTO sin materiales calculados, inicializando lista vacía");
            }

            // Crear el ID de la obra
            ObjectId idObra = null;
            if (dto.getObra() != null && dto.getObra().getId() != null && !dto.getObra().getId().trim().isEmpty()) {
                try {
                    idObra = new ObjectId(dto.getObra().getId());
                } catch (IllegalArgumentException ex) {
                    LOGGER.log(Level.WARNING, "ID de obra inválido en DTO: " + dto.getObra().getId(), ex);
                    throw new IllegalArgumentException("ID de obra inválido: " + dto.getObra().getId());
                }
            } else {
                LOGGER.warning("DTO sin ID de obra válido");
                throw new IllegalArgumentException("El ID de la obra es requerido");
            }

            // Crear el cálculo sin especificar ID (se generará automáticamente)
            Calculo calculo = new Calculo(fecha, elemento, idObra, materiales);

            LOGGER.info("Conversión exitosa de DTO a Calculo");
            return calculo;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al convertir CalculoDTO a Calculo", ex);
            throw new RuntimeException("Error en conversión de DTO a entidad: " + ex.getMessage(), ex);
        }
    }

    /**
     * Convierte una lista de Calculo a una lista de CalculoDTO.
     *
     * @param calculos Lista de objetos Calculo a convertir
     * @return Lista de objetos CalculoDTO equivalentes
     */
    public static List<CalculoDTO> toDTOList(List<Calculo> calculos) {
        if (calculos == null) {
            LOGGER.warning("Lista de cálculos null, retornando lista vacía");
            return new ArrayList<>();
        }

        List<CalculoDTO> dtos = new ArrayList<>();
        for (int i = 0; i < calculos.size(); i++) {
            Calculo calculo = calculos.get(i);
            try {
                CalculoDTO dto = toDTO(calculo);
                if (dto != null) {
                    dtos.add(dto);
                } else {
                    LOGGER.warning("No se pudo convertir el cálculo en posición " + i);
                }
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error al convertir cálculo en posición " + i, ex);
            }
        }

        LOGGER.info("Conversión de lista: " + calculos.size() + " cálculos -> " + dtos.size() + " DTOs");
        return dtos;
    }

    /**
     * Convierte una lista de CalculoDTO a una lista de Calculo.
     *
     * @param dtos Lista de objetos CalculoDTO a convertir
     * @return Lista de objetos Calculo equivalentes
     */
    public static List<Calculo> toEntityList(List<CalculoDTO> dtos) {
        if (dtos == null) {
            LOGGER.warning("Lista de DTOs null, retornando lista vacía");
            return new ArrayList<>();
        }

        List<Calculo> calculos = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            CalculoDTO dto = dtos.get(i);
            try {
                Calculo calculo = toEntity(dto);
                if (calculo != null) {
                    calculos.add(calculo);
                } else {
                    LOGGER.warning("No se pudo convertir el DTO en posición " + i);
                }
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error al convertir DTO en posición " + i, ex);
            }
        }

        LOGGER.info("Conversión de lista: " + dtos.size() + " DTOs -> " + calculos.size() + " cálculos");
        return calculos;
    }

    /**
     * Calcula el volumen de un elemento constructivo basado en su tipo y
     * dimensiones.
     *
     * @param elemento Elemento para el que se calculará el volumen
     * @return Volumen calculado en metros cúbicos
     */
    private static Double calcularVolumen(Elemento elemento) {
        if (elemento == null) {
            LOGGER.warning("Elemento null para cálculo de volumen");
            return 0.0;
        }

        Double volumen = 0.0;

        try {
            switch (elemento.getTipo()) {
                case COLUMNA_CUADRADA:
                    // Para columnas cuadradas se usa alto * ancho * ancho (área de la sección * altura)
                    if (elemento.getAlto() != null && elemento.getAncho() != null) {
                        volumen = elemento.getAlto() * elemento.getAncho() * elemento.getAncho();
                    }
                    break;

                case LOSA_CONTRAPISO:
                case LOSA_ENTREPISO:
                    // Para losas: largo * ancho * espesor
                    if (elemento.getLargo() != null && elemento.getAncho() != null && elemento.getEspesor() != null) {
                        volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                    }
                    break;

                case VIGA:
                    // Para vigas: largo * ancho * alto
                    if (elemento.getLargo() != null && elemento.getAncho() != null && elemento.getAlto() != null) {
                        volumen = elemento.getLargo() * elemento.getAncho() * elemento.getAlto();
                    }
                    break;

                case MURO_LADRILLO:
                    // Para muros: largo * alto * espesor
                    if (elemento.getLargo() != null && elemento.getAlto() != null && elemento.getEspesor() != null) {
                        volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                    }
                    break;

                case NIVELACION_MUROS_VERTICAL:
                    // Para nivelación vertical: largo * alto * espesor
                    if (elemento.getLargo() != null && elemento.getAlto() != null && elemento.getEspesor() != null) {
                        volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                    }
                    break;

                case NIVELACION_PISOS_HORIZONTAL:
                    // Para nivelación horizontal: largo * ancho * espesor
                    if (elemento.getLargo() != null && elemento.getAncho() != null && elemento.getEspesor() != null) {
                        volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                    }
                    break;

                default:
                    LOGGER.warning("Tipo de elemento no reconocido: " + elemento.getTipo());
                    volumen = 0.0;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error al calcular volumen del elemento", ex);
            volumen = 0.0;
        }

        LOGGER.info("Volumen calculado: " + volumen + " m³ para elemento tipo " + elemento.getTipo());
        return volumen;
    }

}
