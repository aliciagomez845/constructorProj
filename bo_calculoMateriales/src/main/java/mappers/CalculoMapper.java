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
            return null;
        }

        try {
            // Convertir la fecha
            LocalDate fecha = null;
            if (calculo.getFecha() != null) {
                fecha = calculo.getFecha().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }

            // Convertir el elemento
            ElementoDTO elementoDTO = ElementoMapper.toDTO(calculo.getElemento());

            // Convertir la lista de materiales calculados
            List<MaterialCalculoDTO> materialesDTO = MaterialCalculoMapper.toDTOList(calculo.getMaterialesCalculados());

            // Obtener la obra completa usando el BO
            ObraDTO obraDTO = null;
            if (calculo.getIdObra()!= null) {
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
            }

            // Calcular el volumen
            Double volumenCalculado = calcularVolumen(calculo.getElemento());

            CalculoDTO calculoDTO = new CalculoDTO(obraDTO, fecha, volumenCalculado, elementoDTO, materialesDTO);

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
            return null;
        }

        try {
            // Convertir la fecha
            Date fecha = null;
            if (dto.getFecha() != null) {
                fecha = Date.from(dto.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            // Convertir el elemento
            Elemento elemento = ElementoMapper.toEntity(dto.getElemento());

            // Convertir la lista de materiales calculados
            List<MaterialCalculo> materiales = MaterialCalculoMapper.toEntityList(dto.getMaterialesCalculados());

            // Crear el ID de la obra
            ObjectId idObra = null;
            if (dto.getObra() != null && dto.getObra().getId() != null) {
                idObra = new ObjectId(dto.getObra().getId());
            }

            return new Calculo(fecha, elemento, idObra, materiales);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al convertir CalculoDTO a Calculo", ex);
            return null;
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
            return new ArrayList<>();
        }

        List<CalculoDTO> dtos = new ArrayList<>();
        for (Calculo calculo : calculos) {
            CalculoDTO dto = toDTO(calculo);
            if (dto != null) {
                dtos.add(dto);
            }
        }

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
            return new ArrayList<>();
        }

        List<Calculo> calculos = new ArrayList<>();
        for (CalculoDTO dto : dtos) {
            Calculo calculo = toEntity(dto);
            if (calculo != null) {
                calculos.add(calculo);
            }
        }

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
                    // Para vigas: largo * ancho * espesor
                    if (elemento.getLargo() != null && elemento.getAncho() != null && elemento.getEspesor() != null) {
                        volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
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
                    volumen = 0.0;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error al calcular volumen del elemento", ex);
            volumen = 0.0;
        }

        return volumen;
    }

}
