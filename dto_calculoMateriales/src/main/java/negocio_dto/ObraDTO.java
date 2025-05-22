/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio_dto;

import java.util.List;

/**
 * Clase ObraDTO
 *
 * Data Transfer Object (DTO) que representa un proyecto de construcción u obra.
 * Esta clase encapsula la información básica que identifica y caracteriza a una
 * obra específica, facilitando su transporte entre las diferentes capas de la
 * aplicación.
 *
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public class ObraDTO {

    private String id;

    /**
     * Identificador único de la obra.
     */
    private String numero;

    /**
     * Dirección física donde se ubica la obra.
     */
    private String direccion;

    /**
     * Volumen de construcción en metros cúbicos.
     */
    private Double metrosCubicos;

    private Integer capacidadEmpleados;

    //private List<RecursoDTO> recursos;

    private List<String> maquinarias;

    private List<String> herramientas;

    /**
     * Constructor por defecto. Inicializa una instancia de ObraDTO sin datos.
     */
    public ObraDTO() {
    }

    /**
     * Constructor parametrizado completo. Crea una instancia de ObraDTO con
     * todos sus atributos.
     *
     * @param id
     * @param numero
     * @param direccion La dirección física de la obra
     * @param metrosCubicos El volumen en metros cúbicos
     */
    public ObraDTO(String id, String numero, String direccion, Double metrosCubicos, Integer capacidadEmpleados) {
        this.id = id;
        this.numero = numero;
        this.direccion = direccion;
        this.metrosCubicos = metrosCubicos;
        this.capacidadEmpleados = capacidadEmpleados;
    }

    /**
     * Constructor parametrizado sin identificador.
     *
     * @param numero
     * @param direccion La dirección física de la obra
     * @param metrosCubicos El volumen en metros cúbicos
     */
    public ObraDTO(String numero, String direccion, Double metrosCubicos, Integer capacidadEmpleados) {
        this.numero = numero;
        this.direccion = direccion;
        this.metrosCubicos = metrosCubicos;
        this.capacidadEmpleados = capacidadEmpleados;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador de la obra.
     *
     * @return El ID único de la obra
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el identificador de la obra.
     *
     * @param numero El nuevo ID para la obra
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene la dirección de la obra.
     *
     * @return La dirección física donde se ubica
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la obra.
     *
     * @param direccion La nueva dirección para la obra
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el volumen de la obra en metros cúbicos.
     *
     * @return El tamaño de la obra en metros cúbicos
     */
    public Double getMetrosCubicos() {
        return metrosCubicos;
    }

    /**
     * Establece el volumen de la obra en metros cúbicos.
     *
     * @param metrosCubicos El nuevo volumen para la obra
     */
    public void setMetrosCubicos(Double metrosCubicos) {
        this.metrosCubicos = metrosCubicos;
    }

    public Integer getCapacidadEmpleados() {
        return capacidadEmpleados;
    }

    public void setCapacidadEmpleados(Integer capacidadEmpleados) {
        this.capacidadEmpleados = capacidadEmpleados;
    }

    /**
     * Genera una representación en cadena de texto del objeto ObraDTO.
     *
     * @return Una cadena representando los atributos del objeto
     */
    @Override
    public String toString() {
        return "ObraDTO{" + "idObra=" + numero + ", direccion=" + direccion + ", metrosCubicos=" + metrosCubicos + '}';
    }

}
