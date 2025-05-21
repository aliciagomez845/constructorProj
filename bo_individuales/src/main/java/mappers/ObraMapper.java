/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dominio_entidades.Obra;
import java.util.ArrayList;
import java.util.List;
import negocio_dto.ObraDTO;
import org.bson.types.ObjectId;

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
     * Convierte un objeto Obra de la capa de dominio a un ObraDTO.
     *
     * @param obra Objeto Obra a convertir
     * @return Objeto ObraDTO equivalente
     */
    public static ObraDTO toDTO(Obra obra) {
        if (obra == null) {
            return null;
        }

        String id = null;
        if (obra.getIdObra() != null) {
            id = obra.getIdObra().toHexString();
        }

        return new ObraDTO(id, obra.getDireccion());
    }

    /**
     * Convierte un objeto ObraDTO de la capa de negocio a un Obra.
     *
     * @param dto Objeto ObraDTO a convertir
     * @return Objeto Obra equivalente
     */
    public static Obra toEntity(ObraDTO dto) {
        if (dto == null) {
            return null;
        }

        ObjectId id = null;
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            id = new ObjectId(dto.getId());
        }

        return new Obra(id, dto.getDireccion());
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
            dtos.add(toDTO(obra));
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
            obras.add(toEntity(dto));
        }

        return obras;
    }

}
