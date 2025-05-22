/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio_entidades;

import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase Obra
 *
 * Clase constructora de una obra de construcción o proyecto. Esta clase está
 * diseñada para ser almacenada en una base de datos MongoDB, contiene
 * información como su identificador, número, dirección y volumen total de
 * metros cúbicos.
 *
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public class Obra {

    /**
     * Identificador único de la obra (clave primaria en MongoDB).
     */
    private ObjectId id;

    /**
     * Número de la obra.
     */
    private String numero;

    /**
     * Dirección física donde se localiza la obra.
     */
    private String direccion;

    /**
     * Cantidad total de metros cúbicos asignados a la obra.
     */
    private Double metrosCubicos;

    /**
     * Cantidad de empleados que puede haber en una obra.
     */
    private Integer capacidadEmpleados;

    /**
     * Lista de recursos que tiene una obra.
     */
    //private List<Recurso> recursos;

    /**
     * Lista de herramientas que tiene una obra.
     */
    private List<ObjectId> herramientas;

    /**
     * Lista de maquinarias que tiene una obra.
     */
    private List<ObjectId> maquinarias;

    /**
     * Constructor vacío requerido para deserialización.
     */
    public Obra() {
    }

    /**
     * Constructor que inicializa los atributos principales de la obra, incluido
     * id.
     *
     * @param id Identificador de la obra.
     * @param numero Número de la obra.
     * @param direccion Dirección física de la obra.
     * @param metrosCubicos Volumen en metros cúbicos asignado a la obra.
     * @param capacidadEmpleados
     */
    public Obra(ObjectId id, String numero, String direccion, Double metrosCubicos, Integer capacidadEmpleados) {
        this.id = id;
        this.numero = numero;
        this.direccion = direccion;
        this.metrosCubicos = metrosCubicos;
        this.capacidadEmpleados = capacidadEmpleados;
    }

    /**
     * Constructor que inicializa los atributos principales de la obra.
     *
     * @param numero Número de la obra.
     * @param direccion Dirección física de la obra.
     * @param metrosCubicos Volumen en metros cúbicos asignado a la obra.
     * @param capacidadEmpleados
     */
    public Obra(String numero, String direccion, Double metrosCubicos, Integer capacidadEmpleados) {
        this.numero = numero;
        this.direccion = direccion;
        this.metrosCubicos = metrosCubicos;
        this.capacidadEmpleados = capacidadEmpleados;
    }

    /**
     * Obtiene el identificador de una obra.
     *
     * @return Identificador de una obra.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Establece el identificador de una obra.
     *
     * @param id Identificador de la obra.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Obtiene el número de la obra.
     *
     * @return El número de la obra.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la obra.
     *
     * @param numero El número a asignar.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene la dirección de la obra.
     *
     * @return La dirección de la obra.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la obra.
     *
     * @param direccion La dirección a asignar.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el volumen de metros cúbicos de la obra.
     *
     * @return Metros cúbicos de la obra.
     */
    public double getMetrosCubicos() {
        return metrosCubicos;
    }

    /**
     * Establece el volumen de metros cúbicos de la obra.
     *
     * @param metrosCubicos Metros cúbicos a asignar.
     */
    public void setMetrosCubicos(Double metrosCubicos) {
        this.metrosCubicos = metrosCubicos;
    }

    /**
     * Obtiene la capacidad de empleados de una obra.
     *
     * @return Capacidad de empleados de la obra.
     */
    public Integer getCapacidadEmpleados() {
        return capacidadEmpleados;
    }

    /**
     * Establece la capacidad de empleados de una obra.
     *
     * @param capacidadEmpleados Capacidad de empleados de una obra.
     */
    public void setCapacidadEmpleados(Integer capacidadEmpleados) {
        this.capacidadEmpleados = capacidadEmpleados;
    }

//    /**
//     * Obtiene la lista de recursos que hay en una obra.
//     *
//     * @return Lista de recursos dentro de una obra.
//     */
//    public List<Recurso> getRecursos() {
//        return recursos;
//    }
//
//    /**
//     * Establece la lista de recursos dentro de una obra.
//     *
//     * @param recursos Lista de recursos dentro de una obra.
//     */
//    public void setRecursos(List<Recurso> recursos) {
//        this.recursos = recursos;
//    }

    /**
     * Obtiene la lista de herramientas que hay en una obra.
     *
     * @return Lista de herramientas dentro de una obra.
     */
    public List<ObjectId> getHerramientas() {
        return herramientas;
    }

    /**
     * Establece la lista de herramientas dentro de una obra.
     *
     * @param herramientas Lista de herramientas dentro de una obra.
     */
    public void setHerramientas(List<ObjectId> herramientas) {
        this.herramientas = herramientas;
    }

    /**
     * Obtiene la lista de maquinaria que hay en una obra.
     *
     * @return Lista de maquinaria dentro de la obra.
     */
    public List<ObjectId> getMaquinarias() {
        return maquinarias;
    }

    /**
     * Establece la lista de maquinaria en una obra.
     *
     * @param maquinarias Lista de maquinaria dentro de la obra.
     */
    public void setMaquinarias(List<ObjectId> maquinarias) {
        this.maquinarias = maquinarias;
    }

    /**
     * Devuelve el ID del curso en formato String (hexadecimal), útil para capas
     * que no deben manipular directamente ObjectId.
     *
     * @return ID como cadena de texto o null si no está definido.
     */
    public String getObjectString() {
        // return id != null ? id.toHexString() : null;
        return this.id.toString();
    }

    /**
     * Establece el ObjectId del curso a partir de una cadena hexadecimal.
     *
     * @param idStr ID como cadena. Si es null vacío, se asigna null.
     */
    public void setObjectString(String idStr) {
        this.id = (idStr != null && !idStr.isBlank()) ? new ObjectId(idStr) : null;
    }

    /**
     * Devuelve una representación en forma de cadena de Obra.
     *
     * @return Cadena con los atributos de la obra.
     */
    @Override
    public String toString() {
        return "Obra{"
                + "id=" + id
                + ", numero=" + numero
                + ", direccion=" + direccion
                + ", metrosCubicos=" + metrosCubicos
                + ", capacidadEmpleados=" + capacidadEmpleados
                //+ ", recursos=" + recursos
                + ", herramientas=" + herramientas
                + ", maquinarias=" + maquinarias
                + '}';
    }

}
