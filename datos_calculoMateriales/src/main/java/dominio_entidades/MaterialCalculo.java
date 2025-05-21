/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio_entidades;

import dominio_enums.TipoMaterialDatos;
import dominio_enums.UnidadMaterialDatos;

/**
 * Clase que representa un material específico calculado para un elemento
 * constructivo.
 *
 * Esta entidad almacena la información sobre un material que se ha calculado
 * como necesario para un elemento de construcción, incluyendo su tipo, unidad
 * de medida y cantidad requerida.
 *
 * @author Alejandra García Preciado - 252444
 */
public class MaterialCalculo {

    /**
     * Unidad de medida utilizada para este material.
     */
    private UnidadMaterialDatos unidad;

    /**
     * Cantidad calculada del material necesaria para el elemento.
     */
    private Double cantidad;

    /**
     * Tipo específico de material.
     */
    private TipoMaterialDatos tipo;

    /**
     * Constructor por defecto.
     */
    public MaterialCalculo() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param unidad unidad de medida del material
     * @param cantidad cantidad calculada del material
     * @param tipo tipo específico del material
     */
    public MaterialCalculo(UnidadMaterialDatos unidad, Double cantidad, TipoMaterialDatos tipo) {
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    /**
     * Obtiene la unidad de medida del material.
     *
     * @return la unidad de medida
     */
    public UnidadMaterialDatos getUnidad() {
        return unidad;
    }

    /**
     * Establece la unidad de medida del material.
     *
     * @param unidad la nueva unidad de medida
     */
    public void setUnidad(UnidadMaterialDatos unidad) {
        this.unidad = unidad;
    }

    /**
     * Obtiene la cantidad calculada del material.
     *
     * @return la cantidad del material
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad calculada del material.
     *
     * @param cantidad la nueva cantidad del material
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el tipo específico del material.
     *
     * @return el tipo de material
     */
    public TipoMaterialDatos getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo específico del material.
     *
     * @param tipo el nuevo tipo de material
     */
    public void setTipo(TipoMaterialDatos tipo) {
        this.tipo = tipo;
    }

}
