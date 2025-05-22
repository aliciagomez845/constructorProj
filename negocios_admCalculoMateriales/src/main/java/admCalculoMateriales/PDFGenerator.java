/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admCalculoMateriales;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import negocio_dto.CalculoDTO;
import negocio_dto.MaterialCalculoDTO;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

/**
 * Clase utilitaria para generar reportes PDF de cálculos de materiales.
 *
 * Esta clase se encarga de crear documentos PDF profesionales con la
 * información de los cálculos realizados, incluyendo detalles del elemento,
 * materiales calculados y proporciones recomendadas.
 *
 * @author Alejandra García Preciado - 252444   
 */
public class PDFGenerator {

    /**
     * Genera un PDF con el reporte de cálculo de materiales.
     *
     * @param calculo Objeto CalculoDTO con la información del cálculo
     * @return Array de bytes que representa el PDF generado
     * @throws Exception Si ocurre un error durante la generación del PDF
     */
    public static byte[] generarReportePDF(CalculoDTO calculo) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // Crear el documento PDF
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Configurar fuentes
            PdfFont titleFont = PdfFontFactory.createFont();
            PdfFont normalFont = PdfFontFactory.createFont();

            // Título principal
            Paragraph title = new Paragraph("BUILDCONTROL")
                    .setFont(titleFont)
                    .setFontSize(24)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE)
                    .setMarginBottom(10);
            document.add(title);

            // Subtítulo
            Paragraph subtitle = new Paragraph("Reporte de Cálculo de Materiales")
                    .setFont(titleFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(subtitle);

            // Información general del cálculo
            document.add(new Paragraph("INFORMACIÓN GENERAL")
                    .setFont(titleFont)
                    .setFontSize(14)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMarginBottom(10));

            // Tabla de información general
            Table infoTable = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                    .setWidth(UnitValue.createPercentValue(100));

            // Obra
            if (calculo.getObra() != null) {
                infoTable.addCell(new Cell().add(new Paragraph("Dirección de Obra:").setFont(normalFont).setBold()));
                infoTable.addCell(new Cell().add(new Paragraph(calculo.getObra().getDireccion()).setFont(normalFont)));
            }

            // Fecha
            if (calculo.getFecha() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                infoTable.addCell(new Cell().add(new Paragraph("Fecha del Cálculo:").setFont(normalFont).setBold()));
                infoTable.addCell(new Cell().add(new Paragraph(calculo.getFecha().format(formatter)).setFont(normalFont)));
            }

            // Tipo de elemento
            if (calculo.getElemento() != null) {
                infoTable.addCell(new Cell().add(new Paragraph("Tipo de Elemento:").setFont(normalFont).setBold()));
                infoTable.addCell(new Cell().add(new Paragraph(calculo.getElemento().getTipo().toString().replace("_", " ")).setFont(normalFont)));
            }

            // Volumen calculado
            if (calculo.getVolumenCalculado() != null) {
                infoTable.addCell(new Cell().add(new Paragraph("Volumen Calculado:").setFont(normalFont).setBold()));
                infoTable.addCell(new Cell().add(new Paragraph(String.format("%.2f m³", calculo.getVolumenCalculado())).setFont(normalFont)));
            }

            document.add(infoTable);
            document.add(new Paragraph("\n"));

            // Dimensiones del elemento
            if (calculo.getElemento() != null) {
                document.add(new Paragraph("DIMENSIONES DEL ELEMENTO")
                        .setFont(titleFont)
                        .setFontSize(14)
                        .setFontColor(ColorConstants.DARK_GRAY)
                        .setMarginBottom(10));

                Table dimensionTable = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                        .setWidth(UnitValue.createPercentValue(100));

                if (calculo.getElemento().getLargo() != null && calculo.getElemento().getLargo() > 0) {
                    dimensionTable.addCell(new Cell().add(new Paragraph("Largo:").setFont(normalFont).setBold()));
                    dimensionTable.addCell(new Cell().add(new Paragraph(String.format("%.2f m", calculo.getElemento().getLargo())).setFont(normalFont)));
                }

                if (calculo.getElemento().getAncho() != null && calculo.getElemento().getAncho() > 0) {
                    dimensionTable.addCell(new Cell().add(new Paragraph("Ancho:").setFont(normalFont).setBold()));
                    dimensionTable.addCell(new Cell().add(new Paragraph(String.format("%.2f m", calculo.getElemento().getAncho())).setFont(normalFont)));
                }

                if (calculo.getElemento().getAlto() != null && calculo.getElemento().getAlto() > 0) {
                    dimensionTable.addCell(new Cell().add(new Paragraph("Alto:").setFont(normalFont).setBold()));
                    dimensionTable.addCell(new Cell().add(new Paragraph(String.format("%.2f m", calculo.getElemento().getAlto())).setFont(normalFont)));
                }

                if (calculo.getElemento().getEspesor() != null && calculo.getElemento().getEspesor() > 0) {
                    dimensionTable.addCell(new Cell().add(new Paragraph("Espesor:").setFont(normalFont).setBold()));
                    dimensionTable.addCell(new Cell().add(new Paragraph(String.format("%.2f m", calculo.getElemento().getEspesor())).setFont(normalFont)));
                }

                document.add(dimensionTable);
                document.add(new Paragraph("\n"));
            }

            // Materiales calculados
            if (calculo.getMaterialesCalculados() != null && !calculo.getMaterialesCalculados().isEmpty()) {
                document.add(new Paragraph("MATERIALES CALCULADOS")
                        .setFont(titleFont)
                        .setFontSize(14)
                        .setFontColor(ColorConstants.DARK_GRAY)
                        .setMarginBottom(10));

                Table materialsTable = new Table(UnitValue.createPercentArray(new float[]{40, 30, 30}))
                        .setWidth(UnitValue.createPercentValue(100));

                // Headers
                materialsTable.addHeaderCell(new Cell().add(new Paragraph("Material").setFont(normalFont).setBold()));
                materialsTable.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setFont(normalFont).setBold()));
                materialsTable.addHeaderCell(new Cell().add(new Paragraph("Unidad").setFont(normalFont).setBold()));

                // Datos
                for (MaterialCalculoDTO material : calculo.getMaterialesCalculados()) {
                    materialsTable.addCell(new Cell().add(new Paragraph(material.getTipo().toString()).setFont(normalFont)));
                    materialsTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", material.getCantidadCalculada())).setFont(normalFont)));
                    materialsTable.addCell(new Cell().add(new Paragraph(getUnidadTexto(material.getUnidad().toString())).setFont(normalFont)));
                }

                document.add(materialsTable);
                document.add(new Paragraph("\n"));
            }

            // Agregar proporciones según el tipo de elemento
            agregarProporciones(document, calculo, titleFont, normalFont);

            // Nota importante
            document.add(new Paragraph("NOTA IMPORTANTE")
                    .setFont(titleFont)
                    .setFontSize(14)
                    .setFontColor(ColorConstants.RED)
                    .setMarginTop(20)
                    .setMarginBottom(10));

            document.add(new Paragraph("Las cantidades mostradas no incluyen el porcentaje de desperdicio, "
                    + "el cual puede variar según las condiciones específicas de la obra.")
                    .setFont(normalFont)
                    .setItalic());

            // Cerrar el documento
            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new Exception("Error al generar el PDF: " + e.getMessage(), e);
        } finally {
            outputStream.close();
        }
    }

    /**
     * Agrega las proporciones recomendadas según el tipo de elemento.
     */
    private static void agregarProporciones(Document document, CalculoDTO calculo, PdfFont titleFont, PdfFont normalFont) {
        if (calculo.getElemento() == null) {
            return;
        }

        document.add(new Paragraph("PROPORCIONES RECOMENDADAS")
                .setFont(titleFont)
                .setFontSize(14)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setMarginBottom(10));

        String proporciones = "";

        switch (calculo.getElemento().getTipo()) {
            case COLUMNA_CUADRADA:
            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
            case VIGA:
                // Concreto
                proporciones = "Para concreto estructural:\n"
                        + "• Por 1 porción de cemento\n"
                        + "• Usar 1.6 porciones de arena\n"
                        + "• Usar 1.8 porciones de grava\n"
                        + "• Resistencia: 3000 psi / 210 kg/cm² / 21 MPa";
                break;

            case NIVELACION_MUROS_VERTICAL:
            case NIVELACION_PISOS_HORIZONTAL:
                // Morteros de nivelación
                proporciones = "Para morteros de nivelación:\n"
                        + "• Por 1 porción de cemento\n"
                        + "• Usar 2.8 porciones de arena\n"
                        + "• Resistencia recomendada para nivelación";
                break;

            case MURO_LADRILLO:
                // Mampostería
                proporciones = "Para mortero de mampostería:\n"
                        + "• Por 1 porción de cemento\n"
                        + "• Por 0.25 porciones de cal\n"
                        + "• Usar 3.5 porciones de arena\n"
                        + "• Resistencia: 1800 psi / 125 kg/cm² / 12.5 MPa / Tipo S";
                break;
        }

        document.add(new Paragraph(proporciones).setFont(normalFont));
    }

    /**
     * Convierte el enum de unidad a texto legible.
     */
    private static String getUnidadTexto(String unidad) {
        switch (unidad) {
            case "KILOGRAMO":
                return "kg";
            case "METRO_CUBICO":
                return "m³";
            case "PIEZA":
                return "piezas";
            case "LITRO":
                return "litros";
            case "METRO":
                return "m";
            default:
                return unidad.toLowerCase();
        }
    }

}
