/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase utilitaria para manejar la descarga y guardado de archivos PDF.
 *
 * Proporciona métodos para permitir al usuario seleccionar dónde guardar un
 * archivo PDF y para guardarlo en el sistema de archivos.
 *
 * @author Alejandra García Preciado - 252444
 */
public class PDFUtilities {

    /**
     * Muestra un diálogo para que el usuario seleccione dónde guardar el PDF y
     * lo guarda en la ubicación seleccionada.
     *
     * @param parent Ventana padre para el diálogo
     * @param pdfBytes Array de bytes que representa el PDF
     * @param nombreSugerido Nombre sugerido para el archivo (opcional)
     * @return true si el archivo se guardó exitosamente, false en caso
     * contrario
     */
    public static boolean guardarPDF(JFrame parent, byte[] pdfBytes, String nombreSugerido) {
        if (pdfBytes == null || pdfBytes.length == 0) {
            Utilities.mostrarMensajeError("No hay contenido PDF para guardar.");
            return false;
        }

        try {
            // Crear el JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte PDF");

            // Configurar filtro para archivos PDF
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");
            fileChooser.setFileFilter(filter);

            // Establecer nombre sugerido
            String nombreArchivo = nombreSugerido != null ? nombreSugerido : generarNombrePorDefecto();
            if (!nombreArchivo.toLowerCase().endsWith(".pdf")) {
                nombreArchivo += ".pdf";
            }
            fileChooser.setSelectedFile(new File(nombreArchivo));

            // Mostrar el diálogo
            int userSelection = fileChooser.showSaveDialog(parent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoDestino = fileChooser.getSelectedFile();

                // Asegurar que tenga extensión .pdf
                if (!archivoDestino.getName().toLowerCase().endsWith(".pdf")) {
                    archivoDestino = new File(archivoDestino.getAbsolutePath() + ".pdf");
                }

                // Verificar si el archivo ya existe
                if (archivoDestino.exists()) {
                    boolean sobrescribir = Utilities.mostrarConfirmacion(
                            "El archivo '" + archivoDestino.getName() + "' ya existe. ¿Desea sobrescribirlo?"
                    );
                    if (!sobrescribir) {
                        return false;
                    }
                }

                // Guardar el archivo
                try (FileOutputStream fos = new FileOutputStream(archivoDestino)) {
                    fos.write(pdfBytes);
                    fos.flush();
                }

                Utilities.mostrarMensajeInfo(
                        "PDF guardado exitosamente en:\n" + archivoDestino.getAbsolutePath()
                );
                return true;
            }

        } catch (IOException e) {
            Utilities.mostrarMensajeError(
                    "Error al guardar el archivo PDF: " + e.getMessage()
            );
            System.err.println("Error al guardar PDF: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Utilities.mostrarMensajeError(
                    "Error inesperado al guardar el PDF: " + e.getMessage()
            );
            System.err.println("Error inesperado al guardar PDF: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Genera un nombre por defecto para el archivo PDF basado en la fecha y
     * hora actual.
     *
     * @return Nombre por defecto para el archivo
     */
    private static String generarNombrePorDefecto() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return "Reporte_Calculo_Materiales_" + ahora.format(formatter);
    }

    /**
     * Genera un nombre específico para el PDF basado en el tipo de elemento y
     * la fecha.
     *
     * @param tipoElemento Tipo de elemento calculado
     * @return Nombre específico para el archivo
     */
    public static String generarNombreEspecifico(String tipoElemento) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        String tipoLimpio = tipoElemento != null
                ? tipoElemento.replace("_", "-").toLowerCase() : "elemento";

        return "Calculo_" + tipoLimpio + "_" + ahora.format(formatter);
    }

}
