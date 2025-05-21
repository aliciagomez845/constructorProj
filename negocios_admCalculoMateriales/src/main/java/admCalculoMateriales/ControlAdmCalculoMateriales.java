/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admCalculoMateriales;

import admObraSeleccionada.FAdmObraSeleccionada;
import admObraSeleccionada.IAdmObraSeleccionada;
import bo.CalculoBO;
import bo.ElementoBO;
import bo.MaterialCalculoBO;

/**
 * Controlador del subsistema admCalculoMateriales.
 *
 * Esta clase gestiona la lógica de control relacionada con los cálculos de
 * materiales para elementos constructivos. Sirve como intermediario entre la
 * capa de presentación y las clases de negocio (BO), específicamente para
 * calcular materiales, guardar resultados y generar reportes.
 *
 * @author Alejandra García Preciado - 252444
 */
public class ControlAdmCalculoMateriales {

    /**
     * Interfaz para acceder al subsistema de obra seleccionada.
     */
    private final IAdmObraSeleccionada admObraSeleccionada;

    /**
     * Objeto de negocio encargado de la lógica relacionada con los cálculos.
     */
    private final CalculoBO calculoBO;

    /**
     * Objeto de negocio encargado de la lógica relacionada con los elementos.
     */
    private final ElementoBO elementoBO;

    /**
     * Objeto de negocio encargado de la lógica relacionada con los materiales.
     */
    private final MaterialCalculoBO materialCalculoBO;

    /**
     * Constructor que inicializa las instancias de los BOs e interfaces
     * requeridas.
     */
    public ControlAdmCalculoMateriales() {
        this.admObraSeleccionada = new FAdmObraSeleccionada();

        this.calculoBO = CalculoBO.getInstance();
        this.elementoBO = ElementoBO.getInstance();
        this.materialCalculoBO = MaterialCalculoBO.getInstance();
    }

}
