/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admCalculoMateriales;

/**
 * Facade FAdmCalculoMateriales.
 *
 * Clase que implementa la interfaz IAdmCalculoMateriales y actúa como una fachada
 * del subsistema encargado de la gestión de cálculo de materiales. Su propósito
 * es simplificar el acceso a la lógica de negocio encapsulada en la clase
 * ControlAdmCalculoMateriales, ocultando detalles de implementación y brindando
 * una interfaz clara y uniforme al resto del sistema.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class FAdmCalculoMateriales implements IAdmCalculoMateriales {
    
    /**
     * Instancia del controlador que contiene la lógica de negocio para el
     * cálculo de materiales.
     */
    private ControlAdmCalculoMateriales controlAdmCalculoMateriales;

    /**
     * Constructor por defecto de la clase.
     *
     * Inicializa la instancia del controlador que implementa las operaciones
     * necesarias para cumplir con el contrato definido por la interfaz.
     */
    public FAdmCalculoMateriales() {
        this.controlAdmCalculoMateriales = new ControlAdmCalculoMateriales();
    }
    
}
