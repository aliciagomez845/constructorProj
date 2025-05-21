/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Clase para el manejo de Excepciones en el subsistema AdmCalculoMateriales.
 *
 * Esta clase extiende de Exception y se utiliza para encapsular y propagar
 * errores específicos que pueden ocurrir durante las operaciones en el
 * subsistema AdmCalculoMateriales.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class AdmCalculoMaterialesException extends Exception {
    
    /**
     * Constructor que recibe únicamente un mensaje.
     *
     * @param mensaje Mensaje que describe la raíz de la excepción.
     */
    public AdmCalculoMaterialesException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor que recibe el mensaje y la causa de la excepción, como un
     * objeto Throwable.
     *
     * @param mensaje Mensaje que describe la raíz de la excepción.
     * @param causa Objeto Throwable de la causa de la excepción.
     */
    public AdmCalculoMaterialesException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
