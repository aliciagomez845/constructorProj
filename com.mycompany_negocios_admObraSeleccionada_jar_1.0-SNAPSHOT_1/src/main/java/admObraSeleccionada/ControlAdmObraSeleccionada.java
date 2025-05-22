/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admObraSeleccionada;

import bo.ObraBO;
import excepciones.AdmObraSeleccionadaException;
import excepciones.NegocioException;
import negocio_dto.ObraDTO;

/**
 * Controlador del subsistema admObraSeleccionada.
 *
 * Esta clase gestiona la lógica de control relacionada con la obra seleccionada
 * por el administrador. Sirve como intermediario entre la capa de presentación
 * y las clases de negocio (BO), específicamente para iniciar y cerrar sesiones
 * de obra, validar su existencia y obtener información asociada.
 *
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public class ControlAdmObraSeleccionada {

    /**
     * Gestor de sesiones de obra. Encargado de mantener el estado de la obra
     * actualmente seleccionada por el usuario. Se implementa como singleton
     * para asegurar una única instancia global.
     */
    private final SesionObraManager sesionManager;

    /**
     * Objeto de negocio encargado de la lógica relacionada con las obras.
     * Permite validar su existencia, obtener identificadores y direcciones. Se
     * accede mediante el patrón singleton.
     */
    private final ObraBO obraBO;

    /**
     * Constructor del controlador. Inicializa los gestores necesarios para el
     * manejo de la sesión de obra.
     */
    public ControlAdmObraSeleccionada() {
        this.sesionManager = SesionObraManager.getInstance();
        this.obraBO = ObraBO.getInstance();
    }

    /**
     * Inicia una sesión de trabajo sobre una obra específica, dado su número
     * identificador.
     *
     * @param numero Número de obra proporcionado por el usuario.
     * @return true si la sesión se inicia correctamente, false en caso
     * contrario.
     * @throws AdmObraSeleccionadaException si la obra no existe o hay un error
     * al iniciar sesión.
     */
    public boolean iniciarSesion(String numero) throws AdmObraSeleccionadaException {
        try {
            if (!obraBO.validarObraExiste(numero)) {
                throw new AdmObraSeleccionadaException("No se encontró obra con número " + numero + ".");
            }

            String idObra = obtenerIdPorNumero(numero);

            return sesionManager.iniciarSesion(idObra);
        } catch (NegocioException e) {
            throw new AdmObraSeleccionadaException("No se pudo iniciar la sesión de la obra.", e);
        }
    }

    /**
     * Cierra la sesión de la obra actualmente seleccionada.
     */
    public void cerrarSesion() {
        sesionManager.cerrarSesion();
    }

    /**
     * Obtiene el ID interno de la obra actualmente en sesión.
     *
     * @return ID de la obra en sesión, o null si no hay ninguna activa.
     */
    public String obtenerIdObra() {
        return sesionManager.getIdObra();
    }

    /**
     * Verifica si una obra con el número dado existe en el sistema.
     *
     * @param numero Número de la obra a validar.
     * @return true si la obra existe, false en caso contrario.
     * @throws AdmObraSeleccionadaException si ocurre un error durante la
     * validación.
     */
    public boolean validarObraExiste(String numero) throws AdmObraSeleccionadaException {
        try {
            return obraBO.validarObraExiste(numero);
        } catch (NegocioException e) {
            throw new AdmObraSeleccionadaException("No se pudo verificar si la obra existe.", e);
        }
    }

    /**
     * Obtiene el ID interno de una obra dado su número identificador.
     *
     * @param numero Número de obra.
     * @return ID correspondiente a la obra.
     * @throws AdmObraSeleccionadaException si no se puede obtener el ID.
     */
    private String obtenerIdPorNumero(String numero) throws AdmObraSeleccionadaException {
        try {
            return obraBO.obtenerIdPorNumero(numero);
        } catch (Exception e) {
            throw new AdmObraSeleccionadaException("No se pudo obtener el id de la obra.", e);
        }
    }

    /**
     * Obtiene la dirección de la obra actualmente en sesión.
     *
     * @return Dirección de la obra.
     * @throws AdmObraSeleccionadaException si ocurre un error al obtener la
     * dirección.
     */
    public String obtenerDireccionObra() throws AdmObraSeleccionadaException {
        try {
            String idObra = sesionManager.getIdObra();
            return obraBO.obtenerDireccionObra(idObra);
        } catch (Exception e) {
            throw new AdmObraSeleccionadaException("No se pudo obtener la direccion de la obra.", e);
        }
    }
    
    /**
     * Obtiene la obra actualmente en sesión.
     * 
     * @return Objeto obra.
     * @throws AdmObraSeleccionadaException si ocurre un error al obtener la obra.
     */
    public ObraDTO obtenerObra() throws AdmObraSeleccionadaException {
        try {
            String idObra =sesionManager.getIdObra();
            return obraBO.obtenerObra(idObra);
        } catch (NegocioException e) {
            throw new AdmObraSeleccionadaException("No se pudo obtener la obra.", e);
        }
    }

}
