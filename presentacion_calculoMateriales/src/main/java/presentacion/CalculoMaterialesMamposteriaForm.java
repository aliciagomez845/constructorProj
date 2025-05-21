/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import excepciones.PresentacionException;
import negocio_dto.CalculoDTO;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_enums.TipoMaterialNegocio;
import utilities.Utilities;

/**
 * Formulario de la capa de presentación que muestra los resultados del cálculo
 * de materiales para los elementos de mampostería.
 *
 * @author Alejandra García Preciado - 252444
 */
public class CalculoMaterialesMamposteriaForm extends javax.swing.JFrame {

    /**
     * Referencia al coordinador de aplicación. Permite la navegación entre los
     * distintos formularios del sistema.
     */
    private CoordinadorAplicacion coordinador;

    /**
     * Referencia al coordinador de negocio. Proporciona acceso a la lógica de
     * negocio para la gestión de cálculo de materiales.
     */
    private CoordinadorNegocio coordinadorNegocio;

    /**
     * Cálculo generado para el elemento actual.
     */
    private CalculoDTO calculoActual;

    /**
     * Creates new form CalculoMaterialesMamposteriaForm
     */
    public CalculoMaterialesMamposteriaForm() {
        initComponents();
        this.coordinador = CoordinadorAplicacion.getInstancia();
        this.coordinadorNegocio = CoordinadorNegocio.getInstancia();

        // Centrar la ventana
        this.setLocationRelativeTo(null);

        // Cargar los resultados del cálculo
        cargarResultadosCalculo();
    }

    /**
     * Carga los resultados del cálculo de materiales y los muestra en los
     * campos correspondientes.
     */
    private void cargarResultadosCalculo() {
        try {
            // Obtener el elemento actual del coordinador
            ElementoDTO elemento = coordinadorNegocio.getElementoActual();

            if (elemento == null) {
                Utilities.mostrarMensajeError("No se ha encontrado el elemento para realizar el cálculo.");
                this.dispose();
                coordinador.mostrarSeleccionDatos();
                return;
            }

            // Generar el reporte de cálculo
            this.calculoActual = coordinadorNegocio.generarReporteCalculo(elemento);

            if (calculoActual == null) {
                Utilities.mostrarMensajeError("Error al generar el cálculo de materiales.");
                this.dispose();
                coordinador.mostrarSeleccionDatos();
                return;
            }

            // Mostrar la información del cálculo
            campoTipoElemento.setText(elemento.getTipo().toString().replace("_", " "));

            // Calcular el área del muro
            double areaMuro = elemento.getLargo() * elemento.getAlto();
            campoAreaMuro.setText(Utilities.formatearDecimal(areaMuro) + " m²");

            // Calcular el volumen del mortero (aproximadamente 25% del volumen total)
            double volumenMortero = calculoActual.getVolumenCalculado() * 0.25;
            campoVolumenMortero.setText(Utilities.formatearDecimal(volumenMortero) + " m³");

            // Mostrar las cantidades de materiales calculados
            for (MaterialCalculoDTO material : calculoActual.getMaterialesCalculados()) {
                switch (material.getTipo()) {
                    case CEMENTO:
                        campoCementoRequerido.setText(Utilities.formatearDecimal(material.getCantidadCalculada()) + " kg");
                        break;
                    case AGUA:
                        campoAgua.setText(Utilities.formatearDecimal(material.getCantidadCalculada() * 0.5) + " litros"); // Estimación
                        break;
                    case ARENA:
                        capoArena.setText(Utilities.formatearDecimal(material.getCantidadCalculada()) + " m³");
                        break;
                    case LADRILLO:
                        campoLadrillosRequeridos.setText(Utilities.formatearDecimal(material.getCantidadCalculada()) + " piezas");
                        break;
                }
            }

            // Estimación de cal (común en mampostería)
            // Relación Cemento:Cal:Arena típica es 1:0.25:3
            for (MaterialCalculoDTO material : calculoActual.getMaterialesCalculados()) {
                if (material.getTipo() == TipoMaterialNegocio.CEMENTO) {
                    double cal = material.getCantidadCalculada() * 0.25; // Relación cal/cemento
                    campoCal.setText(Utilities.formatearDecimal(cal) + " kg");
                    break;
                }
            }

            // Si la cantidad de agua no se ha calculado, estimarla basada en el cemento
            if (campoAgua.getText().isEmpty()) {
                for (MaterialCalculoDTO material : calculoActual.getMaterialesCalculados()) {
                    if (material.getTipo() == TipoMaterialNegocio.CEMENTO) {
                        double agua = material.getCantidadCalculada() * 0.5; // Relación agua/cemento de 0.5
                        campoAgua.setText(Utilities.formatearDecimal(agua) + " litros");
                        break;
                    }
                }
            }

        } catch (PresentacionException ex) {
            Utilities.mostrarMensajeError("Error al cargar los resultados: " + ex.getMessage());
            this.dispose();
            coordinador.mostrarSeleccionDatos();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelResultados = new javax.swing.JPanel();
        lblCemento = new javax.swing.JLabel();
        lblVolumenMortero = new javax.swing.JLabel();
        lblTipoElemento = new javax.swing.JLabel();
        lblResistencia = new javax.swing.JLabel();
        lblAgua = new javax.swing.JLabel();
        lblCal = new javax.swing.JLabel();
        lblResultados = new javax.swing.JLabel();
        lblArena = new javax.swing.JLabel();
        lblNota = new javax.swing.JLabel();
        campoTipoElemento = new javax.swing.JTextField();
        campoVolumenMortero = new javax.swing.JTextField();
        campoCementoRequerido = new javax.swing.JTextField();
        campoCal = new javax.swing.JTextField();
        campoAgua = new javax.swing.JTextField();
        capoArena = new javax.swing.JTextField();
        lblCampoResistencia = new javax.swing.JLabel();
        campoLadrillosRequeridos = new javax.swing.JTextField();
        lblLadrillosRequeridos = new javax.swing.JLabel();
        campoAreaMuro = new javax.swing.JTextField();
        lblAreaMuro = new javax.swing.JLabel();
        jPanelProporciones = new javax.swing.JPanel();
        lblProporciones = new javax.swing.JLabel();
        lblPorcionCemento = new javax.swing.JLabel();
        lblPorcionCal = new javax.swing.JLabel();
        lblPorcionArena = new javax.swing.JLabel();
        nombreEmpresa = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnDescargarPDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanelResultados.setBackground(new java.awt.Color(231, 246, 255));

        lblCemento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblCemento.setForeground(new java.awt.Color(0, 0, 0));
        lblCemento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCemento.setText("Cemento requerido:");

        lblVolumenMortero.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVolumenMortero.setForeground(new java.awt.Color(0, 0, 0));
        lblVolumenMortero.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblVolumenMortero.setText("Volumen del Mortero:");

        lblTipoElemento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTipoElemento.setForeground(new java.awt.Color(0, 0, 0));
        lblTipoElemento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTipoElemento.setText("Tipo de elemento:");

        lblResistencia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblResistencia.setForeground(new java.awt.Color(0, 0, 0));
        lblResistencia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblResistencia.setText("Resistencia (por default):");

        lblAgua.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblAgua.setForeground(new java.awt.Color(0, 0, 0));
        lblAgua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAgua.setText("Agua:");

        lblCal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblCal.setForeground(new java.awt.Color(0, 0, 0));
        lblCal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCal.setText("Cal:");

        lblResultados.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblResultados.setForeground(new java.awt.Color(6, 108, 169));
        lblResultados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultados.setText("Resultados material peso seco");

        lblArena.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblArena.setForeground(new java.awt.Color(0, 0, 0));
        lblArena.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblArena.setText("Arena:");

        lblNota.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNota.setForeground(new java.awt.Color(6, 108, 169));
        lblNota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNota.setText("Nota: Las cantidades no incluyen el porcentaje de desperdicio.");

        campoTipoElemento.setEditable(false);
        campoTipoElemento.setBackground(new java.awt.Color(231, 246, 255));
        campoTipoElemento.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoTipoElemento.setForeground(new java.awt.Color(0, 0, 0));
        campoTipoElemento.setBorder(null);

        campoVolumenMortero.setEditable(false);
        campoVolumenMortero.setBackground(new java.awt.Color(231, 246, 255));
        campoVolumenMortero.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoVolumenMortero.setForeground(new java.awt.Color(0, 0, 0));
        campoVolumenMortero.setBorder(null);

        campoCementoRequerido.setEditable(false);
        campoCementoRequerido.setBackground(new java.awt.Color(231, 246, 255));
        campoCementoRequerido.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoCementoRequerido.setForeground(new java.awt.Color(0, 0, 0));
        campoCementoRequerido.setBorder(null);

        campoCal.setEditable(false);
        campoCal.setBackground(new java.awt.Color(231, 246, 255));
        campoCal.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoCal.setForeground(new java.awt.Color(0, 0, 0));
        campoCal.setBorder(null);

        campoAgua.setEditable(false);
        campoAgua.setBackground(new java.awt.Color(231, 246, 255));
        campoAgua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoAgua.setForeground(new java.awt.Color(0, 0, 0));
        campoAgua.setBorder(null);

        capoArena.setEditable(false);
        capoArena.setBackground(new java.awt.Color(231, 246, 255));
        capoArena.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        capoArena.setForeground(new java.awt.Color(0, 0, 0));
        capoArena.setBorder(null);

        lblCampoResistencia.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCampoResistencia.setForeground(new java.awt.Color(0, 0, 0));
        lblCampoResistencia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCampoResistencia.setText("1800 psi / 125 kg / cm2 / 12.5 MPa / Tipo S");

        campoLadrillosRequeridos.setEditable(false);
        campoLadrillosRequeridos.setBackground(new java.awt.Color(231, 246, 255));
        campoLadrillosRequeridos.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoLadrillosRequeridos.setForeground(new java.awt.Color(0, 0, 0));
        campoLadrillosRequeridos.setBorder(null);

        lblLadrillosRequeridos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblLadrillosRequeridos.setForeground(new java.awt.Color(0, 0, 0));
        lblLadrillosRequeridos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblLadrillosRequeridos.setText("Ladrillos requeridos:");

        campoAreaMuro.setEditable(false);
        campoAreaMuro.setBackground(new java.awt.Color(231, 246, 255));
        campoAreaMuro.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoAreaMuro.setForeground(new java.awt.Color(0, 0, 0));
        campoAreaMuro.setBorder(null);

        lblAreaMuro.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblAreaMuro.setForeground(new java.awt.Color(0, 0, 0));
        lblAreaMuro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAreaMuro.setText("Área del muro:");

        javax.swing.GroupLayout jPanelResultadosLayout = new javax.swing.GroupLayout(jPanelResultados);
        jPanelResultados.setLayout(jPanelResultadosLayout);
        jPanelResultadosLayout.setHorizontalGroup(
            jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResultadosLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelResultadosLayout.createSequentialGroup()
                                .addComponent(lblResistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCampoResistencia, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
                            .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelResultadosLayout.createSequentialGroup()
                                    .addComponent(lblAreaMuro, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(campoAreaMuro, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelResultadosLayout.createSequentialGroup()
                                    .addComponent(lblVolumenMortero, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(campoVolumenMortero))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelResultadosLayout.createSequentialGroup()
                                    .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblArena, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblAgua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanelResultadosLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(capoArena, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResultadosLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(campoAgua, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelResultadosLayout.createSequentialGroup()
                                    .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelResultadosLayout.createSequentialGroup()
                                            .addComponent(lblCal, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(campoCal, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblCemento, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(campoCementoRequerido, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelResultadosLayout.createSequentialGroup()
                                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                                        .addComponent(lblLadrillosRequeridos, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoLadrillosRequeridos, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                                        .addComponent(lblTipoElemento, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoTipoElemento, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(39, 39, 39))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResultadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
        );
        jPanelResultadosLayout.setVerticalGroup(
            jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResultadosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblResultados)
                .addGap(18, 18, 18)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoElemento)
                    .addComponent(campoTipoElemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLadrillosRequeridos)
                    .addComponent(campoLadrillosRequeridos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAreaMuro)
                    .addComponent(campoAreaMuro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVolumenMortero)
                    .addComponent(campoVolumenMortero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResistencia)
                    .addComponent(lblCampoResistencia))
                .addGap(10, 10, 10)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCemento)
                    .addComponent(campoCementoRequerido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCal)
                    .addComponent(campoCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAgua)
                    .addComponent(campoAgua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArena)
                    .addComponent(capoArena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblNota)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanelProporciones.setBackground(new java.awt.Color(231, 246, 255));

        lblProporciones.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblProporciones.setForeground(new java.awt.Color(6, 108, 169));
        lblProporciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProporciones.setText("Proporciones por volumen");

        lblPorcionCemento.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPorcionCemento.setForeground(new java.awt.Color(0, 0, 0));
        lblPorcionCemento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPorcionCemento.setText("Por 1 porción de cemento.");

        lblPorcionCal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPorcionCal.setForeground(new java.awt.Color(0, 0, 0));
        lblPorcionCal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPorcionCal.setText("Por 0.25 porción de cal.");

        lblPorcionArena.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPorcionArena.setForeground(new java.awt.Color(0, 0, 0));
        lblPorcionArena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPorcionArena.setText("Usar 3.5 porción de arena.");

        javax.swing.GroupLayout jPanelProporcionesLayout = new javax.swing.GroupLayout(jPanelProporciones);
        jPanelProporciones.setLayout(jPanelProporcionesLayout);
        jPanelProporcionesLayout.setHorizontalGroup(
            jPanelProporcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProporcionesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanelProporcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPorcionCemento, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProporciones, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelProporcionesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanelProporcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPorcionCal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPorcionArena, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanelProporcionesLayout.setVerticalGroup(
            jPanelProporcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProporcionesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblProporciones)
                .addGap(18, 18, 18)
                .addComponent(lblPorcionCemento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPorcionCal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPorcionArena)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        nombreEmpresa.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        nombreEmpresa.setForeground(new java.awt.Color(0, 0, 0));
        nombreEmpresa.setText("BuildControl");

        btnVolver.setBackground(new java.awt.Color(95, 168, 211));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver a inicio");
        btnVolver.setBorderPainted(false);
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnDescargarPDF.setBackground(new java.awt.Color(95, 168, 211));
        btnDescargarPDF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDescargarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnDescargarPDF.setText("Descargar PDF");
        btnDescargarPDF.setBorderPainted(false);
        btnDescargarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnVolver)
                            .addComponent(jPanelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelProporciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDescargarPDF, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(29, 29, 29))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(nombreEmpresa)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jPanelProporciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDescargarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        if (Utilities.mostrarConfirmacion("¿Desea volver a la selección de elementos?")) {
            this.dispose();
            coordinador.mostrarSeleccionDatos();
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnDescargarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarPDFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDescargarPDFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDescargarPDF;
    private javax.swing.JButton btnVolver;
    private javax.swing.JTextField campoAgua;
    private javax.swing.JTextField campoAreaMuro;
    private javax.swing.JTextField campoCal;
    private javax.swing.JTextField campoCementoRequerido;
    private javax.swing.JTextField campoLadrillosRequeridos;
    private javax.swing.JTextField campoTipoElemento;
    private javax.swing.JTextField campoVolumenMortero;
    private javax.swing.JTextField capoArena;
    private javax.swing.JPanel jPanelProporciones;
    private javax.swing.JPanel jPanelResultados;
    private javax.swing.JLabel lblAgua;
    private javax.swing.JLabel lblAreaMuro;
    private javax.swing.JLabel lblArena;
    private javax.swing.JLabel lblCal;
    private javax.swing.JLabel lblCampoResistencia;
    private javax.swing.JLabel lblCemento;
    private javax.swing.JLabel lblLadrillosRequeridos;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblPorcionArena;
    private javax.swing.JLabel lblPorcionCal;
    private javax.swing.JLabel lblPorcionCemento;
    private javax.swing.JLabel lblProporciones;
    private javax.swing.JLabel lblResistencia;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JLabel lblTipoElemento;
    private javax.swing.JLabel lblVolumenMortero;
    private javax.swing.JLabel nombreEmpresa;
    // End of variables declaration//GEN-END:variables
}
