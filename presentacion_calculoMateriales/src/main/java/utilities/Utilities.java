/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase de utilidades para la capa de presentación.
 *
 * Proporciona funciones de utilidad comunes para la interfaz de usuario, como
 * validaciones de entrada, formateo de datos y diálogos de mensaje.
 *
 * @author Alejandra García Preciado - 252444
 */
public class Utilities {

    /**
     * Valida si una cadena de texto puede convertirse en un número decimal.
     *
     * @param texto Texto a validar
     * @return true si el texto es un número decimal válido, false en caso
     * contrario
     */
    public static boolean esNumeroDecimal(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Valida si una cadena de texto puede convertirse en un número entero.
     *
     * @param texto Texto a validar
     * @return true si el texto es un número entero válido, false en caso
     * contrario
     */
    public static boolean esNumeroEntero(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Valida si un campo de texto contiene un número decimal válido y positivo.
     *
     * @param textField Campo de texto a validar
     * @param nombreCampo Nombre del campo para el mensaje de error
     * @return true si el campo contiene un número decimal válido y positivo
     */
    public static boolean validarCampoNumericoPositivo(JTextField textField, String nombreCampo) {
        String texto = textField.getText().trim();

        if (!esNumeroDecimal(texto)) {
            mostrarMensajeError("El campo " + nombreCampo + " debe ser un número válido.");
            textField.requestFocus();
            return false;
        }

        double valor = Double.parseDouble(texto);
        if (valor <= 0) {
            mostrarMensajeError("El campo " + nombreCampo + " debe ser un número positivo.");
            textField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Valida si un campo de texto no está vacío.
     *
     * @param textField Campo de texto a validar
     * @param nombreCampo Nombre del campo para el mensaje de error
     * @return true si el campo no está vacío
     */
    public static boolean validarCampoNoVacio(JTextField textField, String nombreCampo) {
        String texto = textField.getText().trim();

        if (texto.isEmpty()) {
            mostrarMensajeError("El campo " + nombreCampo + " no puede estar vacío.");
            textField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Muestra un mensaje de error en un diálogo.
     *
     * @param mensaje Mensaje de error a mostrar
     */
    public static void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de información en un diálogo.
     *
     * @param mensaje Mensaje de información a mostrar
     */
    public static void mostrarMensajeInfo(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de advertencia en un diálogo.
     *
     * @param mensaje Mensaje de advertencia a mostrar
     */
    public static void mostrarMensajeAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un diálogo de confirmación con opciones Sí/No.
     *
     * @param mensaje Mensaje de confirmación
     * @return true si el usuario selecciona Sí, false en caso contrario
     */
    public static boolean mostrarConfirmacion(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Formatea un número decimal a un formato con 2 decimales.
     *
     * @param valor Valor a formatear
     * @return Cadena formateada con 2 decimales
     */
    public static String formatearDecimal(double valor) {
        return String.format("%.2f", valor);
    }

    /**
     * Convierte un texto a un valor decimal, devolviendo 0 si el texto no es
     * válido.
     *
     * @param texto Texto a convertir
     * @return Valor decimal o 0 si el texto no es válido
     */
    public static double textoADecimal(String texto) {
        if (esNumeroDecimal(texto)) {
            return Double.parseDouble(texto);
        }
        return 0.0;
    }

    /**
     * Limpia los campos de texto especificados.
     *
     * @param textFields Campos de texto a limpiar
     */
    public static void limpiarCampos(JTextField... textFields) {
        for (JTextField textField : textFields) {
            textField.setText("");
        }
    }

}
