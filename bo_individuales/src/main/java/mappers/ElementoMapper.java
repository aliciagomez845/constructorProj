/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dominio_entidades.Elemento;
import dominio_enums.TipoElementoDatos;
import java.util.ArrayList;
import java.util.List;
import negocio_dto.ElementoDTO;
import negocio_enums.TipoElementoNegocio;

/**
 * Clase encargada de convertir entre las representaciones de Elemento.
 *
 * Esta clase facilita la transformación de datos entre capas, para que la
 * lógica de negocio trabaje con entidades y las capas externas usen DTOs.
 *
 * @author Alejandra García Preciado - 252444
 */
public class ElementoMapper {

    /**
     * Convierte un objeto Elemento de la capa de dominio a un ElementoDTO.
     *
     * @param elemento Objeto Elemento a convertir
     * @return Objeto ElementoDTO equivalente
     */
    public static ElementoDTO toDTO(Elemento elemento) {
        if (elemento == null) {
            return null;
        }

        TipoElementoNegocio tipoNegocio = convertirTipoElemento(elemento.getTipo());

        return new ElementoDTO(
                tipoNegocio,
                elemento.getProfundidad(),
                elemento.getLargo(),
                elemento.getAlto(),
                elemento.getAncho(),
                elemento.getEspesor()
        );
    }

    /**
     * Convierte un objeto ElementoDTO de la capa de negocio a un Elemento.
     *
     * @param dto Objeto ElementoDTO a convertir
     * @return Objeto Elemento equivalente
     */
    public static Elemento toEntity(ElementoDTO dto) {
        if (dto == null) {
            return null;
        }

        TipoElementoDatos tipoDatos = convertirTipoElemento(dto.getTipo());

        return new Elemento(
                tipoDatos,
                dto.getProfundidad(),
                dto.getLargo(),
                dto.getAlto(),
                dto.getAncho(),
                dto.getEspesor()
        );
    }

    /**
     * Convierte una lista de Elemento a una lista de ElementoDTO.
     *
     * @param elementos Lista de objetos Elemento a convertir
     * @return Lista de objetos ElementoDTO equivalentes
     */
    public static List<ElementoDTO> toDTOList(List<Elemento> elementos) {
        if (elementos == null) {
            return null;
        }

        List<ElementoDTO> dtos = new ArrayList<>();
        for (Elemento elemento : elementos) {
            dtos.add(toDTO(elemento));
        }

        return dtos;
    }

    /**
     * Convierte una lista de ElementoDTO a una lista de Elemento.
     *
     * @param dtos Lista de objetos ElementoDTO a convertir
     * @return Lista de objetos Elemento equivalentes
     */
    public static List<Elemento> toEntityList(List<ElementoDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<Elemento> elementos = new ArrayList<>();
        for (ElementoDTO dto : dtos) {
            elementos.add(toEntity(dto));
        }

        return elementos;
    }

    /**
     * Convierte un tipo de elemento de la capa de dominio a un tipo de la capa
     * de negocio.
     *
     * @param tipoDatos Tipo de elemento de dominio
     * @return Tipo de elemento de negocio equivalente
     */
    public static TipoElementoNegocio convertirTipoElemento(TipoElementoDatos tipoDatos) {
        if (tipoDatos == null) {
            return null;
        }

        switch (tipoDatos) {
            case COLUMNA_CUADRADA:
                return TipoElementoNegocio.COLUMNA_CUADRADA;
            case LOSA_CONTRAPISO:
                return TipoElementoNegocio.LOSA_CONTRAPISO;
            case LOSA_ENTREPISO:
                return TipoElementoNegocio.LOSA_ENTREPISO;
            case VIGA:
                return TipoElementoNegocio.VIGA;
            case NIVELACION_MUROS_VERTICAL:
                return TipoElementoNegocio.NIVELACION_MUROS_VERTICAL;
            case NIVELACION_PISOS_HORIZONTAL:
                return TipoElementoNegocio.NIVELACION_PISOS_HORIZONTAL;
            case MURO_LADRILLO:
                return TipoElementoNegocio.MURO_LADRILLO;
            default:
                throw new IllegalArgumentException("Tipo de elemento no reconocido: " + tipoDatos);
        }
    }

    /**
     * Convierte un tipo de elemento de la capa de negocio a un tipo de la capa
     * de dominio.
     *
     * @param tipoNegocio Tipo de elemento de negocio
     * @return Tipo de elemento de dominio equivalente
     */
    public static TipoElementoDatos convertirTipoElemento(TipoElementoNegocio tipoNegocio) {
        if (tipoNegocio == null) {
            return null;
        }

        switch (tipoNegocio) {
            case COLUMNA_CUADRADA:
                return TipoElementoDatos.COLUMNA_CUADRADA;
            case LOSA_CONTRAPISO:
                return TipoElementoDatos.LOSA_CONTRAPISO;
            case LOSA_ENTREPISO:
                return TipoElementoDatos.LOSA_ENTREPISO;
            case VIGA:
                return TipoElementoDatos.VIGA;
            case NIVELACION_MUROS_VERTICAL:
                return TipoElementoDatos.NIVELACION_MUROS_VERTICAL;
            case NIVELACION_PISOS_HORIZONTAL:
                return TipoElementoDatos.NIVELACION_PISOS_HORIZONTAL;
            case MURO_LADRILLO:
                return TipoElementoDatos.MURO_LADRILLO;
            default:
                throw new IllegalArgumentException("Tipo de elemento no reconocido: " + tipoNegocio);
        }
    }

}
