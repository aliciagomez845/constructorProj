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
import bo.ObraBO;

/**
 * Clase control ControlAdmCalculoMateriales.
 *
 * Clase que controla la lógica del caso de uso "Cálculo de Materiales" en el sistema.
 * Utiliza diversas clases BO (Business Object) para delegar operaciones
 * específicas, y mantiene comunicación con el subsistema de obra seleccionada.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class ControlAdmCalculoMateriales {
    
    /**
     * Instancia del subsistema de obra seleccionada de la sesión. Se utiliza
     * para determinar a qué obra se está asociando el cálculo de materiales.
     */
    private IAdmObraSeleccionada admObraSeleccionada;

    /**
     * BO para las operaciones de relacionadas a los calculos.
     */
    private CalculoBO calculoBO;

    /**
     * BO para las operaciones relacionadas a elementos.
     */
    private ElementoBO elementoBO;

    /**
     * BO para las operaciones relacionadas a los materiales necesarios.
     */
    private MaterialCalculoBO materialCalculoBO;

    /**
     * BO para las operaciones relacionadas a las obras.
     */
    private ObraBO obraBO;

    /**
     * Constructor que inicializa las instancias de los BOs e interfaces
     * requeridas.
     */
    public ControlAdmCalculoMateriales() {
        this.admObraSeleccionada = new FAdmObraSeleccionada();

        this.calculoBO = CalculoBO.getInstance();
        this.elementoBO = ElementoBO.getInstance();
        this.materialCalculoBO = MaterialCalculoBO.getInstance();
        this.obraBO = ObraBO.getInstance();
    }
    
}
