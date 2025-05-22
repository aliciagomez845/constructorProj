/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admObraSeleccionada;

import excepciones.AdmObraSeleccionadaException;
import negocio_dto.ObraDTO;

/**
 * Fachada del subsistema de administración de obra seleccionada. Esta clase
 * proporciona una interfaz simplificada para gestionar la sesión de una obra,
 * delegando la lógica de almacenamiento a la clase SesionObraManager
 * (Singleton).
 *
 * Implementa la interfaz IAdmObraSeleccionada para garantizar un contrato de
 * uso uniforme dentro del sistema.
 *
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public class FAdmObraSeleccionada implements IAdmObraSeleccionada {

    /**
     * Controlador del subsistema admObraSeleccionada. Se encarga de gestionar
     * la lógica de negocio relacionada con la obra en sesión.
     */
    private ControlAdmObraSeleccionada controlAdmObraSeleccionada;

    /**
     * Constructor de la fachada. Inicializa la instancia de SesionObraManager
     * obteniéndola del Singleton.
     */
    public FAdmObraSeleccionada() {
        this.controlAdmObraSeleccionada = new ControlAdmObraSeleccionada();
    }

    /**
     * Activa la sesión de una obra en el sistema. Si no hay una obra en sesión,
     * se establece la nueva obra con su identificador.
     *
     * @throws AdmObraSeleccionadaException Si ocurre un error al activar la
     * sesión.
     */
    @Override
    public boolean activarSesionObra(String numero) throws AdmObraSeleccionadaException {
        return controlAdmObraSeleccionada.iniciarSesion(numero);
    }

    /**
     * Cierra la sesión de la obra actual. Si hay una sesión activa, la finaliza
     * eliminando el identificador de la obra en sesión.
     *
     * @throws AdmObraSeleccionadaException Si ocurre un error al cerrar la
     * sesión.
     */
    @Override
    public void cerrarSesionObra() throws AdmObraSeleccionadaException {
        controlAdmObraSeleccionada.cerrarSesion();
    }

    /**
     * Obtiene el identificador de la obra cuya sesión está activa.
     *
     * @return El ID de la obra en sesión o null si no hay ninguna.
     * @throws AdmObraSeleccionadaException Si ocurre un error al obtener el ID
     * de la obra.
     */
    @Override
    public String obtenerSesion() throws AdmObraSeleccionadaException {
        return controlAdmObraSeleccionada.obtenerIdObra();
    }
    
    /**
     * Obtiene la dirección de la obra actualmente en sesión.
     *
     * @return Dirección de la obra en sesión.
     * @throws AdmObraSeleccionadaException Si ocurre un error al obtener la
     * dirección.
     */
    @Override
    public String obtenerDireccionObra() throws AdmObraSeleccionadaException {
        return controlAdmObraSeleccionada.obtenerDireccionObra();
    }
    
    @Override
    public ObraDTO obtenerObra() throws AdmObraSeleccionadaException {
        return controlAdmObraSeleccionada.obtenerObra();
    }
}
