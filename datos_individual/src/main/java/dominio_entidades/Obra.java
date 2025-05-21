/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio_entidades;

import org.bson.types.ObjectId;

/**
 * Clase que representa una obra o proyecto de construcción.
 *
 * Esta entidad almacena la información básica de una obra, como su
 * identificador único y su dirección física. Sirve como referencia para los
 * diferentes cálculos de materiales que se realizan en el sistema.
 *
 * @author Alejandra García Preciado - 252444
 */
public class Obra {

    /**
     * Identificador único de la obra en la base de datos.
     */
    private ObjectId idObra;

    /**
     * Dirección física donde se encuentra la obra.
     */
    private String direccion;

    /**
     * Constructor por defecto.
     */
    public Obra() {
    }

    /**
     * Constructor con todos sus atriutos.
     *
     * @param idObra Identificador de la obra
     * @param direccion la dirección física de la obra
     */
    public Obra(ObjectId idObra, String direccion) {
        this.idObra = idObra;
        this.direccion = direccion;
    }

    /**
     * Constructor que inicializa el atributo principales de la obra.
     *
     * @param direccion la dirección física de la obra
     */
    public Obra(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el identificador único de la obra.
     *
     * @return el identificador de la obra
     */
    public ObjectId getIdObra() {
        return idObra;
    }

    /**
     * Establece el identificador único de la obra.
     *
     * @param idObra el nuevo identificador de la obra
     */
    public void setIdObra(ObjectId idObra) {
        this.idObra = idObra;
    }

    /**
     * Obtiene la dirección física de la obra.
     *
     * @return la dirección de la obra
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección física de la obra.
     *
     * @param direccion la nueva dirección de la obra
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
