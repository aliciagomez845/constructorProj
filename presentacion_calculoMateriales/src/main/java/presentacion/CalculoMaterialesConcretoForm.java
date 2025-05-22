/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import excepciones.PresentacionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio_dto.CalculoDTO;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_enums.TipoMaterialNegocio;
import utilities.Utilities;

/**
 * Formulario de la capa de presentación que muestra los resultados del cálculo
 * de materiales para los elementos de concreto.
 *
 * @author Alejandra García Preciado - 252444
 */
public class CalculoMaterialesConcretoForm extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(CalculoMaterialesConcretoForm.class.getName());

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
     * Creates new form CalculoMaterialesForm
     */
    public CalculoMaterialesConcretoForm() {
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
            // ========== CAMBIO IMPORTANTE: Verificar si estamos en modo consulta de historial ==========
            if (coordinadorNegocio.isModoConsultaHistorial()) {
                // MODO CONSULTA: Usar el cálculo existente del historial
                LOGGER.info("Modo consulta de historial - usando cálculo existente");

                this.calculoActual = coordinadorNegocio.getCalculoActual();

                if (calculoActual == null) {
                    Utilities.mostrarMensajeError("No se ha encontrado el cálculo en el historial.");
                    this.dispose();
                    coordinador.mostrarInicioCalculos();
                    return;
                }

                // Mostrar los datos del cálculo existente sin generar ni guardar nada nuevo
                mostrarDatosCalculo(calculoActual);

            } else {
                // MODO NUEVO CÁLCULO: Generar y guardar nuevo cálculo
                LOGGER.info("Modo nuevo cálculo - generando cálculo");

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

                // Guardar el cálculo solo si es nuevo
                try {
                    this.calculoActual = coordinadorNegocio.guardarCalculo(calculoActual);
                    LOGGER.info("Cálculo guardado exitosamente en la base de datos");
                } catch (PresentacionException ex) {
                    // Si falla el guardado, mostrar advertencia pero continuar mostrando los resultados
                    Utilities.mostrarMensajeAdvertencia("El cálculo se generó correctamente, pero no se pudo guardar en el historial: " + ex.getMessage());
                    LOGGER.severe("Error al guardar cálculo: " + ex.getMessage());
                }

                // Mostrar los datos del nuevo cálculo
                mostrarDatosCalculo(calculoActual);
            }

        } catch (PresentacionException ex) {
            Utilities.mostrarMensajeError("Error al cargar los resultados: " + ex.getMessage());
            this.dispose();
            coordinador.mostrarSeleccionDatos();
        }
    }

    /**
     * Método auxiliar para mostrar los datos de un cálculo en la interfaz.
     * Centraliza la lógica de mostrar datos para evitar duplicación de código.
     *
     * @param calculo Cálculo cuyos datos se mostrarán
     */
    private void mostrarDatosCalculo(CalculoDTO calculo) {
        try {
            ElementoDTO elemento = calculo.getElemento();

            // Mostrar la información del cálculo
            campoTipoElemento.setText(elemento.getTipo().toString().replace("_", " "));
            campoVolumenElemento.setText(Utilities.formatearDecimal(calculo.getVolumenCalculado()) + " m³");

            // Mostrar las cantidades de materiales calculados
            for (MaterialCalculoDTO material : calculo.getMaterialesCalculados()) {
                switch (material.getTipo()) {
                    case CEMENTO:
                        campoCementoRequerido.setText(Utilities.formatearDecimal(material.getCantidadCalculada()) + " kg");
                        break;
                    case AGUA:
                        campoAgua.setText(Utilities.formatearDecimal(material.getCantidadCalculada() * 0.5) + " litros"); // Estimación
                        break;
                    case ARENA:
                        campoArena.setText(Utilities.formatearDecimal(material.getCantidadCalculada()) + " m³");
                        break;
                    case GRAVA:
                        campoGrava.setText(Utilities.formatearDecimal(material.getCantidadCalculada()) + " m³");
                        break;
                }
            }

            // Si la cantidad de agua no se ha calculado, estimarla basada en el cemento
            if (campoAgua.getText().isEmpty()) {
                for (MaterialCalculoDTO material : calculo.getMaterialesCalculados()) {
                    if (material.getTipo() == TipoMaterialNegocio.CEMENTO) {
                        double agua = material.getCantidadCalculada() * 0.5; // Relación agua/cemento de 0.5
                        campoAgua.setText(Utilities.formatearDecimal(agua) + " litros");
                        break;
                    }
                }
            }

            LOGGER.info("Datos del cálculo mostrados correctamente en la interfaz");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al mostrar datos del cálculo", ex);
            Utilities.mostrarMensajeError("Error al mostrar los datos del cálculo: " + ex.getMessage());
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

        jPanel2 = new javax.swing.JPanel();
        jPanelProporciones = new javax.swing.JPanel();
        lblProporciones = new javax.swing.JLabel();
        lblPorcionCemento = new javax.swing.JLabel();
        lblPorcionArena = new javax.swing.JLabel();
        lblPorcionGrava = new javax.swing.JLabel();
        nombreEmpresa = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnDescargarPDF = new javax.swing.JButton();
        jPanelResultados = new javax.swing.JPanel();
        lblCemento = new javax.swing.JLabel();
        lblVolumenElemento = new javax.swing.JLabel();
        lblTipoElemento = new javax.swing.JLabel();
        lblResistencia = new javax.swing.JLabel();
        lblArena = new javax.swing.JLabel();
        lblAgua = new javax.swing.JLabel();
        lblResultados = new javax.swing.JLabel();
        lblGrava = new javax.swing.JLabel();
        lblNota = new javax.swing.JLabel();
        campoTipoElemento = new javax.swing.JTextField();
        campoVolumenElemento = new javax.swing.JTextField();
        campoCementoRequerido = new javax.swing.JTextField();
        campoAgua = new javax.swing.JTextField();
        campoArena = new javax.swing.JTextField();
        campoGrava = new javax.swing.JTextField();
        lblCampoResistencia = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(231, 246, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanelProporciones.setBackground(new java.awt.Color(231, 246, 255));

        lblProporciones.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblProporciones.setForeground(new java.awt.Color(6, 108, 169));
        lblProporciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProporciones.setText("Proporciones por volumen");

        lblPorcionCemento.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPorcionCemento.setForeground(new java.awt.Color(0, 0, 0));
        lblPorcionCemento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPorcionCemento.setText("Por 1 porción de cemento.");

        lblPorcionArena.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPorcionArena.setForeground(new java.awt.Color(0, 0, 0));
        lblPorcionArena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPorcionArena.setText("Usar 1.6 porción de arena.");

        lblPorcionGrava.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPorcionGrava.setForeground(new java.awt.Color(0, 0, 0));
        lblPorcionGrava.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPorcionGrava.setText("Usar 1.8 porción de grava.");

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
                            .addComponent(lblPorcionArena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPorcionGrava, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))))
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
                .addComponent(lblPorcionArena)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPorcionGrava)
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

        jPanelResultados.setBackground(new java.awt.Color(231, 246, 255));

        lblCemento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblCemento.setForeground(new java.awt.Color(0, 0, 0));
        lblCemento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCemento.setText("Cemento requerido:");

        lblVolumenElemento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblVolumenElemento.setForeground(new java.awt.Color(0, 0, 0));
        lblVolumenElemento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblVolumenElemento.setText("Volumen del elemento:");

        lblTipoElemento.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTipoElemento.setForeground(new java.awt.Color(0, 0, 0));
        lblTipoElemento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTipoElemento.setText("Tipo de elemento:");

        lblResistencia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblResistencia.setForeground(new java.awt.Color(0, 0, 0));
        lblResistencia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblResistencia.setText("Resistencia (por default):");

        lblArena.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblArena.setForeground(new java.awt.Color(0, 0, 0));
        lblArena.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblArena.setText("Arena:");

        lblAgua.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblAgua.setForeground(new java.awt.Color(0, 0, 0));
        lblAgua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAgua.setText("Agua:");

        lblResultados.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblResultados.setForeground(new java.awt.Color(6, 108, 169));
        lblResultados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultados.setText("Resultados material peso seco");

        lblGrava.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblGrava.setForeground(new java.awt.Color(0, 0, 0));
        lblGrava.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGrava.setText("Grava:");

        lblNota.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNota.setForeground(new java.awt.Color(6, 108, 169));
        lblNota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNota.setText("Nota: Las cantidades no incluyen el porcentaje de desperdicio.");

        campoTipoElemento.setEditable(false);
        campoTipoElemento.setBackground(new java.awt.Color(231, 246, 255));
        campoTipoElemento.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoTipoElemento.setForeground(new java.awt.Color(0, 0, 0));
        campoTipoElemento.setBorder(null);

        campoVolumenElemento.setEditable(false);
        campoVolumenElemento.setBackground(new java.awt.Color(231, 246, 255));
        campoVolumenElemento.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoVolumenElemento.setForeground(new java.awt.Color(0, 0, 0));
        campoVolumenElemento.setBorder(null);

        campoCementoRequerido.setEditable(false);
        campoCementoRequerido.setBackground(new java.awt.Color(231, 246, 255));
        campoCementoRequerido.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoCementoRequerido.setForeground(new java.awt.Color(0, 0, 0));
        campoCementoRequerido.setBorder(null);

        campoAgua.setEditable(false);
        campoAgua.setBackground(new java.awt.Color(231, 246, 255));
        campoAgua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoAgua.setForeground(new java.awt.Color(0, 0, 0));
        campoAgua.setBorder(null);

        campoArena.setEditable(false);
        campoArena.setBackground(new java.awt.Color(231, 246, 255));
        campoArena.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoArena.setForeground(new java.awt.Color(0, 0, 0));
        campoArena.setBorder(null);

        campoGrava.setEditable(false);
        campoGrava.setBackground(new java.awt.Color(231, 246, 255));
        campoGrava.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        campoGrava.setForeground(new java.awt.Color(0, 0, 0));
        campoGrava.setBorder(null);

        lblCampoResistencia.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCampoResistencia.setForeground(new java.awt.Color(0, 0, 0));
        lblCampoResistencia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCampoResistencia.setText("3000 psi / 210 kg / cm2 / 21 MPa");

        javax.swing.GroupLayout jPanelResultadosLayout = new javax.swing.GroupLayout(jPanelResultados);
        jPanelResultados.setLayout(jPanelResultadosLayout);
        jPanelResultadosLayout.setHorizontalGroup(
            jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResultadosLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addComponent(lblResistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCampoResistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblGrava, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(lblArena, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoArena)
                            .addComponent(campoGrava)))
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addComponent(lblTipoElemento, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoTipoElemento))
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addComponent(lblCemento, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoCementoRequerido))
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addComponent(lblVolumenElemento, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoVolumenElemento))
                    .addGroup(jPanelResultadosLayout.createSequentialGroup()
                        .addComponent(lblAgua, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoAgua)))
                .addGap(39, 39, 39))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResultadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
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
                .addGap(7, 7, 7)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVolumenElemento)
                    .addComponent(campoVolumenElemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(lblAgua)
                    .addComponent(campoAgua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArena)
                    .addComponent(campoArena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGrava)
                    .addComponent(campoGrava, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblNota)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVolver)
                        .addGap(506, 506, 506)
                        .addComponent(btnDescargarPDF))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(jPanelProporciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(nombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(nombreEmpresa)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDescargarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jPanelProporciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
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
        try {
            // Verificar que el cálculo esté disponible
            if (calculoActual == null) {
                Utilities.mostrarMensajeError("No hay un cálculo disponible para descargar.");
                return;
            }

            // Verificar que el cálculo tenga la información mínima necesaria
            if (calculoActual.getObra() == null || calculoActual.getObra().getDireccion() == null) {
                Utilities.mostrarMensajeError("El cálculo debe tener una obra asociada para generar el PDF.");
                return;
            }

            if (calculoActual.getElemento() == null) {
                Utilities.mostrarMensajeError("El cálculo debe tener un elemento asociado para generar el PDF.");
                return;
            }

            if (calculoActual.getMaterialesCalculados() == null || calculoActual.getMaterialesCalculados().isEmpty()) {
                Utilities.mostrarMensajeError("El cálculo debe tener materiales calculados para generar el PDF.");
                return;
            }

            // Mostrar mensaje de progreso
            Utilities.mostrarMensajeInfo("Generando PDF, por favor espere...");

            // Generar el PDF usando el coordinador de negocio
            // Nota: Aquí usamos el coordinador para generar el PDF directamente del cálculo actual
            byte[] pdfBytes = coordinadorNegocio.generarPDFDirecto(calculoActual);

            if (pdfBytes != null && pdfBytes.length > 0) {
                // Generar nombre sugerido para el archivo
                String nombreSugerido = utilities.PDFUtilities.generarNombreEspecifico(
                        calculoActual.getElemento().getTipo().toString()
                );

                // Permitir al usuario guardar el PDF
                boolean guardado = utilities.PDFUtilities.guardarPDF(this, pdfBytes, nombreSugerido);

                if (guardado) {
                    System.out.println("PDF descargado exitosamente");
                }
            } else {
                Utilities.mostrarMensajeAdvertencia("No se pudo generar el PDF. El archivo está vacío.");
            }

        } catch (PresentacionException ex) {
            Utilities.mostrarMensajeError("Error al generar el PDF: " + ex.getMessage());
            System.err.println("Error en descarga de PDF: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            Utilities.mostrarMensajeError("Error inesperado al descargar el PDF: " + ex.getMessage());
            System.err.println("Error inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnDescargarPDFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDescargarPDF;
    private javax.swing.JButton btnVolver;
    private javax.swing.JTextField campoAgua;
    private javax.swing.JTextField campoArena;
    private javax.swing.JTextField campoCementoRequerido;
    private javax.swing.JTextField campoGrava;
    private javax.swing.JTextField campoTipoElemento;
    private javax.swing.JTextField campoVolumenElemento;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelProporciones;
    private javax.swing.JPanel jPanelResultados;
    private javax.swing.JLabel lblAgua;
    private javax.swing.JLabel lblArena;
    private javax.swing.JLabel lblCampoResistencia;
    private javax.swing.JLabel lblCemento;
    private javax.swing.JLabel lblGrava;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblPorcionArena;
    private javax.swing.JLabel lblPorcionCemento;
    private javax.swing.JLabel lblPorcionGrava;
    private javax.swing.JLabel lblProporciones;
    private javax.swing.JLabel lblResistencia;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JLabel lblTipoElemento;
    private javax.swing.JLabel lblVolumenElemento;
    private javax.swing.JLabel nombreEmpresa;
    // End of variables declaration//GEN-END:variables
}
