/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dominio_entidades.Obra;
import java.util.ArrayList;
import java.util.List;
import negocio_dto.ObraDTO;

/**
 * Clase encargada de convertir entre las representaciones de Obra.
 *
 * Esta clase facilita la transformación de datos entre capas, para que la
 * lógica de negocio trabaje con entidades y las capas externas usen DTOs.
 *
 * @author Alejandra García Preciado - 252444
 */
public class ObraMapper {

    /**
     * Convierte una entidad Obra a su DTO correspondiente.
     *
     * @param obra La entidad Obra que será convertida
     * @return Un ObraDTO con los datos de la entidad
     */
    public static ObraDTO toDTO(Obra obra) {
        if (obra == null) {
            return null;
        }

        ObraDTO obraDTO = new ObraDTO(
                obra.getId() != null ? obra.getId().toHexString() : null, 
                obra.getNumero(),
                obra.getDireccion(),
                obra.getMetrosCubicos(),
                obra.getCapacidadEmpleados()
        );

        return obraDTO;
    }

    /**
     * Convierte un ObraDTO a la entidad Obra.
     *
     * @param obraDTO El DTO que contiene los datos para la entidad
     * @return La entidad Obra creada a partir del DTO
     */
    public static Obra toEntity(ObraDTO obraDTO) {
        if (obraDTO == null) {
            return null;
        }

        Obra obra = new Obra(
                obraDTO.getNumero(),
                obraDTO.getDireccion(),
                obraDTO.getMetrosCubicos(),
                obraDTO.getCapacidadEmpleados()
        );

        // Establecer el ID si está presente
        if (obraDTO.getId() != null && !obraDTO.getId().isEmpty()) {
            obra.setObjectString(obraDTO.getId());
        }

        return obra;
    }

    /**
     * Convierte una lista de Obra a una lista de ObraDTO.
     *
     * @param obras Lista de objetos Obra a convertir
     * @return Lista de objetos ObraDTO equivalentes
     */
    public static List<ObraDTO> toDTOList(List<Obra> obras) {
        if (obras == null) {
            return null;
        }

        List<ObraDTO> dtos = new ArrayList<>();
        for (Obra obra : obras) {
            ObraDTO dto = toDTO(obra);
            if (dto != null) {
                dtos.add(dto);
            }
        }

        return dtos;
    }

    /**
     * Convierte una lista de ObraDTO a una lista de Obra.
     *
     * @param dtos Lista de objetos ObraDTO a convertir
     * @return Lista de objetos Obra equivalentes
     */
    public static List<Obra> toEntityList(List<ObraDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<Obra> obras = new ArrayList<>();
        for (ObraDTO dto : dtos) {
            Obra obra = toEntity(dto);
            if (obra != null) {
                obras.add(obra);
            }
        }

        return obras;
    }

}
