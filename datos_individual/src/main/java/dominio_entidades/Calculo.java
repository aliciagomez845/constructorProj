/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio_entidades;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase que representa un cálculo de materiales para un elemento específico de
 * una obra.
 *
 * Esta entidad almacena la información sobre un cálculo de materiales
 * realizado, incluyendo el elemento para el que se calculó, la obra a la que
 * pertenece, la fecha del cálculo y la lista de materiales calculados con sus
 * cantidades.
 *
 * @author Alejandra García Preciado - 252444
 */
public class Calculo {

    /**
     * Identificador único del cálculo en la base de datos.
     */
    private ObjectId idCalculo;

    /**
     * Fecha en que se realizó el cálculo.
     */
    private Date fecha;

    /**
     * Elemento constructivo para el que se realizó el cálculo.
     */
    private Elemento elemento;

    /**
     * Obra a la que pertenece el elemento calculado.
     */
    private ObjectId idObra;

    /**
     * Lista de materiales calculados con sus cantidades.
     */
    private List<MaterialCalculo> materialesCalculados;

    /**
     * Constructor por defecto.
     */
    public Calculo() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param fecha fecha en que se realizó el cálculo
     * @param elemento elemento constructivo para el que se realizó el cálculo
     * @param idObra obra a la que pertenece el elemento
     * @param materialesCalculados lista de materiales calculados con sus
     * cantidades
     */
    public Calculo(Date fecha, Elemento elemento, ObjectId idObra, List<MaterialCalculo> materialesCalculados) {
        this.fecha = fecha;
        this.elemento = elemento;
        this.idObra = idObra;
        this.materialesCalculados = materialesCalculados;
    }

    /**
     * Obtiene el identificador único del cálculo.
     *
     * @return el identificador del cálculo
     */
    public ObjectId getIdCalculo() {
        return idCalculo;
    }

    /**
     * Establece el identificador único del cálculo.
     *
     * @param idCalculo el nuevo identificador del cálculo
     */
    public void setIdCalculo(ObjectId idCalculo) {
        this.idCalculo = idCalculo;
    }

    /**
     * Obtiene la fecha en que se realizó el cálculo.
     *
     * @return la fecha del cálculo
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en que se realizó el cálculo.
     *
     * @param fecha la nueva fecha del cálculo
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el elemento constructivo para el que se realizó el cálculo.
     *
     * @return el elemento constructivo
     */
    public Elemento getElemento() {
        return elemento;
    }

    /**
     * Establece el elemento constructivo para el que se realizó el cálculo.
     *
     * @param elemento el nuevo elemento constructivo
     */
    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    /**
     * Obtiene la obra a la que pertenece el elemento calculado.
     *
     * @return la obra
     */
    public ObjectId getObra() {
        return idObra;
    }

    /**
     * Establece la obra a la que pertenece el elemento calculado.
     *
     * @param idObra la nueva obra
     */
    public void setObra(ObjectId idObra) {
        this.idObra = idObra;
    }

    /**
     * Obtiene la lista de materiales calculados con sus cantidades.
     *
     * @return la lista de materiales calculados
     */
    public List<MaterialCalculo> getMaterialesCalculados() {
        return materialesCalculados;
    }

    /**
     * Establece la lista de materiales calculados con sus cantidades.
     *
     * @param materialesCalculados la nueva lista de materiales calculados
     */
    public void setMaterialesCalculados(List<MaterialCalculo> materialesCalculados) {
        this.materialesCalculados = materialesCalculados;
    }

}
