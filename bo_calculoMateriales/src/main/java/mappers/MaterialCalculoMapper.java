/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dominio_entidades.MaterialCalculo;
import dominio_enums.TipoMaterialDatos;
import dominio_enums.UnidadMaterialDatos;
import java.util.ArrayList;
import java.util.List;
import negocio_dto.MaterialCalculoDTO;
import negocio_enums.TipoMaterialNegocio;
import negocio_enums.UnidadMaterialNegocio;

/**
 * Clase encargada de convertir entre las representaciones de MaterialCalculo.
 *
 * Esta clase facilita la transformación de datos entre capas, para que la
 * lógica de negocio trabaje con entidades y las capas externas usen DTOs.
 *
 * @author Alejandra García Preciado - 252444
 */
public class MaterialCalculoMapper {

    /**
     * Convierte un objeto MaterialCalculo de la capa de dominio a un
     * MaterialCalculoDTO.
     *
     * @param material Objeto MaterialCalculo a convertir
     * @return Objeto MaterialCalculoDTO equivalente
     */
    public static MaterialCalculoDTO toDTO(MaterialCalculo material) {
        if (material == null) {
            return null;
        }

        TipoMaterialNegocio tipoNegocio = convertirTipoMaterial(material.getTipo());
        UnidadMaterialNegocio unidadNegocio = convertirUnidadMaterial(material.getUnidad());

        return new MaterialCalculoDTO(tipoNegocio, unidadNegocio, material.getCantidad());
    }

    /**
     * Convierte un objeto MaterialCalculoDTO de la capa de negocio a un
     * MaterialCalculo.
     *
     * @param dto Objeto MaterialCalculoDTO a convertir
     * @return Objeto MaterialCalculo equivalente
     */
    public static MaterialCalculo toEntity(MaterialCalculoDTO dto) {
        if (dto == null) {
            return null;
        }

        TipoMaterialDatos tipoDatos = convertirTipoMaterial(dto.getTipo());
        UnidadMaterialDatos unidadDatos = convertirUnidadMaterial(dto.getUnidad());

        return new MaterialCalculo(unidadDatos, dto.getCantidadCalculada(), tipoDatos);
    }

    /**
     * Convierte una lista de MaterialCalculo a una lista de MaterialCalculoDTO.
     *
     * @param materiales Lista de objetos MaterialCalculo a convertir
     * @return Lista de objetos MaterialCalculoDTO equivalentes
     */
    public static List<MaterialCalculoDTO> toDTOList(List<MaterialCalculo> materiales) {
        if (materiales == null) {
            return null;
        }

        List<MaterialCalculoDTO> dtos = new ArrayList<>();
        for (MaterialCalculo material : materiales) {
            dtos.add(toDTO(material));
        }

        return dtos;
    }

    /**
     * Convierte una lista de MaterialCalculoDTO a una lista de MaterialCalculo.
     *
     * @param dtos Lista de objetos MaterialCalculoDTO a convertir
     * @return Lista de objetos MaterialCalculo equivalentes
     */
    public static List<MaterialCalculo> toEntityList(List<MaterialCalculoDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<MaterialCalculo> materiales = new ArrayList<>();
        for (MaterialCalculoDTO dto : dtos) {
            materiales.add(toEntity(dto));
        }

        return materiales;
    }

    /**
     * Convierte un tipo de material de la capa de dominio a un tipo de la capa
     * de negocio.
     *
     * @param tipoDatos Tipo de material de dominio
     * @return Tipo de material de negocio equivalente
     */
    public static TipoMaterialNegocio convertirTipoMaterial(TipoMaterialDatos tipoDatos) {
        if (tipoDatos == null) {
            return null;
        }

        switch (tipoDatos) {
            case CEMENTO:
                return TipoMaterialNegocio.CEMENTO;
            case ARENA:
                return TipoMaterialNegocio.ARENA;
            case GRAVA:
                return TipoMaterialNegocio.GRAVA;
            case LADRILLO:
                return TipoMaterialNegocio.LADRILLO;
            case CLAVO:
                return TipoMaterialNegocio.CLAVO;
            case PINTURA:
                return TipoMaterialNegocio.PINTURA;
            case MADERA:
                return TipoMaterialNegocio.MADERA;
            case RESINA:
                return TipoMaterialNegocio.RESINA;
            default:
                throw new IllegalArgumentException("Tipo de material no reconocido: " + tipoDatos);
        }
    }

    /**
     * Convierte un tipo de material de la capa de negocio a un tipo de la capa
     * de dominio.
     *
     * @param tipoNegocio Tipo de material de negocio
     * @return Tipo de material de dominio equivalente
     */
    public static TipoMaterialDatos convertirTipoMaterial(TipoMaterialNegocio tipoNegocio) {
        if (tipoNegocio == null) {
            return null;
        }

        switch (tipoNegocio) {
            case CEMENTO:
                return TipoMaterialDatos.CEMENTO;
            case ARENA:
                return TipoMaterialDatos.ARENA;
            case GRAVA:
                return TipoMaterialDatos.GRAVA;
            case LADRILLO:
                return TipoMaterialDatos.LADRILLO;
            case CLAVO:
                return TipoMaterialDatos.CLAVO;
            case PINTURA:
                return TipoMaterialDatos.PINTURA;
            case MADERA:
                return TipoMaterialDatos.MADERA;
            case RESINA:
                return TipoMaterialDatos.RESINA;
            default:
                throw new IllegalArgumentException("Tipo de material no reconocido: " + tipoNegocio);
        }
    }

    /**
     * Convierte una unidad de material de la capa de dominio a una unidad de la
     * capa de negocio.
     *
     * @param unidadDatos Unidad de material de dominio
     * @return Unidad de material de negocio equivalente
     */
    public static UnidadMaterialNegocio convertirUnidadMaterial(UnidadMaterialDatos unidadDatos) {
        if (unidadDatos == null) {
            return null;
        }

        switch (unidadDatos) {
            case KILOGRAMO:
                return UnidadMaterialNegocio.KILOGRAMO;
            case METRO_CUBICO:
                return UnidadMaterialNegocio.METRO_CUBICO;
            case PIEZA:
                return UnidadMaterialNegocio.PIEZA;
            case LITRO:
                return UnidadMaterialNegocio.LITRO;
            case METRO:
                return UnidadMaterialNegocio.METRO;
            default:
                throw new IllegalArgumentException("Unidad de material no reconocida: " + unidadDatos);
        }
    }

    /**
     * Convierte una unidad de material de la capa de negocio a una unidad de la
     * capa de dominio.
     *
     * @param unidadNegocio Unidad de material de negocio
     * @return Unidad de material de dominio equivalente
     */
    public static UnidadMaterialDatos convertirUnidadMaterial(UnidadMaterialNegocio unidadNegocio) {
        if (unidadNegocio == null) {
            return null;
        }

        switch (unidadNegocio) {
            case KILOGRAMO:
                return UnidadMaterialDatos.KILOGRAMO;
            case METRO_CUBICO:
                return UnidadMaterialDatos.METRO_CUBICO;
            case PIEZA:
                return UnidadMaterialDatos.PIEZA;
            case LITRO:
                return UnidadMaterialDatos.LITRO;
            case METRO:
                return UnidadMaterialDatos.METRO;
            default:
                throw new IllegalArgumentException("Unidad de material no reconocida: " + unidadNegocio);
        }
    }

}
