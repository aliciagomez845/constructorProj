/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dominio_entidades.Calculo;
import dominio_entidades.Elemento;
import dominio_entidades.MaterialCalculo;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        // Crear una ObraDTO con el ID de la obra
        ObraDTO obraDTO = null;
        if (calculo.getObra() != null) {
            obraDTO = new ObraDTO();
            obraDTO.setId(calculo.getObra().toHexString());
        }

        // Calcular el volumen
        Double volumenCalculado = calcularVolumen(calculo.getElemento());

        CalculoDTO calculoDTO = new CalculoDTO(obraDTO, fecha, volumenCalculado, elementoDTO, materialesDTO);

        return calculoDTO;
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
    }

    /**
     * Convierte una lista de Calculo a una lista de CalculoDTO.
     *
     * @param calculos Lista de objetos Calculo a convertir
     * @return Lista de objetos CalculoDTO equivalentes
     */
    public static List<CalculoDTO> toDTOList(List<Calculo> calculos) {
        if (calculos == null) {
            return null;
        }

        List<CalculoDTO> dtos = new ArrayList<>();
        for (Calculo calculo : calculos) {
            dtos.add(toDTO(calculo));
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
            return null;
        }

        List<Calculo> calculos = new ArrayList<>();
        for (CalculoDTO dto : dtos) {
            calculos.add(toEntity(dto));
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

        switch (elemento.getTipo()) {
            case COLUMNA_CUADRADA:
                volumen = elemento.getAlto() * elemento.getAncho() * elemento.getAncho();
                break;

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            case VIGA:
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getAlto();
                break;

            case MURO_LADRILLO:
                volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                break;

            case NIVELACION_MUROS_VERTICAL:
            case NIVELACION_PISOS_HORIZONTAL:
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            default:
                volumen = 0.0;
        }

        return volumen;
    }

}
