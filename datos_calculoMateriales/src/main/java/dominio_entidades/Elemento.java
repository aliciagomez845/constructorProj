/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio_entidades;

import dominio_enums.TipoElementoDatos;

/**
 * Clase que representa un elemento constructivo de una obra.
 *
 * Esta entidad almacena las características físicas y el tipo de un elemento de
 * construcción, como sus dimensiones (largo, ancho, alto), profundidad, espesor
 * y el tipo específico de elemento que representa. Es utilizada para realizar
 * cálculos de materiales requeridos.
 *
 * @author Alejandra García Preciado - 252444
 */
public class Elemento {

    /**
     * Tipo específico de elemento constructivo.
     */
    private TipoElementoDatos tipo;

    /**
     * Profundidad del elemento en metros.
     */
    private Double profundidad;

    /**
     * Largo del elemento en metros.
     */
    private Double largo;

    /**
     * Alto del elemento en metros.
     */
    private Double alto;

    /**
     * Ancho del elemento en metros.
     */
    private Double ancho;

    /**
     * Espesor del elemento en metros, utilizado principalmente para losas y
     * muros.
     */
    private Double espesor;

    /**
     * Constructor por defecto.
     */
    public Elemento() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param tipo tipo específico de elemento constructivo
     * @param profundidad profundidad del elemento en metros
     * @param largo largo del elemento en metros
     * @param alto alto del elemento en metros
     * @param ancho ancho del elemento en metros
     * @param espesor espesor del elemento en metros
     */
    public Elemento(TipoElementoDatos tipo, Double profundidad, Double largo, Double alto, Double ancho, Double espesor) {
        this.tipo = tipo;
        this.profundidad = profundidad;
        this.largo = largo;
        this.alto = alto;
        this.ancho = ancho;
        this.espesor = espesor;
    }

    /**
     * Obtiene el tipo específico del elemento constructivo.
     *
     * @return el tipo de elemento
     */
    public TipoElementoDatos getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo específico del elemento constructivo.
     *
     * @param tipo el nuevo tipo de elemento
     */
    public void setTipo(TipoElementoDatos tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la profundidad del elemento en metros.
     *
     * @return la profundidad del elemento
     */
    public Double getProfundidad() {
        return profundidad;
    }

    /**
     * Establece la profundidad del elemento en metros.
     *
     * @param profundidad la nueva profundidad del elemento
     */
    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }

    /**
     * Obtiene el largo del elemento en metros.
     *
     * @return el largo del elemento
     */
    public Double getLargo() {
        return largo;
    }

    /**
     * Establece el largo del elemento en metros.
     *
     * @param largo el nuevo largo del elemento
     */
    public void setLargo(Double largo) {
        this.largo = largo;
    }

    /**
     * Obtiene el alto del elemento en metros.
     *
     * @return el alto del elemento
     */
    public Double getAlto() {
        return alto;
    }

    /**
     * Establece el alto del elemento en metros.
     *
     * @param alto el nuevo alto del elemento
     */
    public void setAlto(Double alto) {
        this.alto = alto;
    }

    /**
     * Obtiene el ancho del elemento en metros.
     *
     * @return el ancho del elemento
     */
    public Double getAncho() {
        return ancho;
    }

    /**
     * Establece el ancho del elemento en metros.
     *
     * @param ancho el nuevo ancho del elemento
     */
    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    /**
     * Obtiene el espesor del elemento en metros.
     *
     * @return el espesor del elemento
     */
    public Double getEspesor() {
        return espesor;
    }

    /**
     * Establece el espesor del elemento en metros.
     *
     * @param espesor el nuevo espesor del elemento
     */
    public void setEspesor(Double espesor) {
        this.espesor = espesor;
    }

}
