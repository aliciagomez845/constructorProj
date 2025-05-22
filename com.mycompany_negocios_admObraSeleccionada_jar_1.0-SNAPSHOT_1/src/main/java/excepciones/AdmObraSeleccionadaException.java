/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package excepciones;

/**
 * Excepción personalizada para errores en la administración de la sesión de una
 * obra.
 *
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public class AdmObraSeleccionadaException extends Exception {

    /**
     * Constructor por defecto. Crea una excepción sin mensaje asociado.
     */
    public AdmObraSeleccionadaException() {
    }

    /**
     * Constructor que permite definir un mensaje descriptivo del error.
     *
     * @param message Mensaje que describe la causa del error.
     */
    public AdmObraSeleccionadaException(String message) {
        super(message);
    }

    /**
     * Constructor que permite definir un mensaje y una causa del error. Se
     * utiliza cuando la excepción es causada por otra excepción interna.
     *
     * @param message Mensaje que describe el error.
     * @param cause Excepción original que causó el error.
     */
    public AdmObraSeleccionadaException(String message, Throwable cause) {
        super(message, cause);
    }
}
