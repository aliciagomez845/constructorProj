/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import negocio_dto.ElementoDTO;
import negocio_enums.TipoElementoNegocio;
import utilities.Utilities;

/**
 * Formulario de la capa de presentación que permite al usuario continuar con el
 * proceso de cálculo de materiales por elemento constructivo seleccionando el
 * elemento que desea calcular e ingresando sus dimensiones.
 *
 * @author Alejandra García Preciado - 252444
 */
public class IngresoDimensionesNivelacionForm extends javax.swing.JFrame {

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
     * Creates new form IngresoDimensionesNivelacion
     */
    public IngresoDimensionesNivelacionForm() {
        initComponents();
        this.coordinador = CoordinadorAplicacion.getInstancia();
        this.coordinadorNegocio = CoordinadorNegocio.getInstancia();

        // Centrar la ventana
        this.setLocationRelativeTo(null);

        // Agregar listeners para los checkbox para que solo se pueda seleccionar uno
        agregarListenerCheckboxes();

        // Inicialmente deshabilitar todos los campos de dimensiones
        deshabilitarTodosCampos();
    }

    /**
     * Agrega listeners a los checkbox para que solo sea posible seleccionar uno
     * a la vez y habilitar los campos correspondientes.
     */
    private void agregarListenerCheckboxes() {
        jcbNivelacionMuros.addActionListener((e) -> {
            if (jcbNivelacionMuros.isSelected()) {

                // Deshabilitar el otro checkbox
                jcbNivelacionPisos.setSelected(false);

                // Deshabilitar todos los campos primero
                deshabilitarTodosCampos();

                // Habilitar solo los campos necesarios para nivelación de muros
                habilitarCamposNivelacionMuros();
            } else {
                // Si se "des selecciona", deshabilitar los campos
                deshabilitarCamposNivelacionMuros();
            }
        });

        jcbNivelacionPisos.addActionListener((e) -> {
            if (jcbNivelacionPisos.isSelected()) {

                // Deshabilitar el otro checkbox
                jcbNivelacionMuros.setSelected(false);

                // Deshabilitar todos los campos primero
                deshabilitarTodosCampos();

                // Habilitar solo los campos necesarios para nivelación de pisos
                habilitarCamposNivelacionPisos();
            } else {
                // Si se "des selecciona", deshabilitar los campos
                deshabilitarCamposNivelacionPisos();
            }
        });
    }

    // Métodos para habilitar y deshabilitar campos según el elemento seleccionado.
    private void deshabilitarTodosCampos() {
        // Nivelación de muros
        campoAltoNivelacionMuros.setEnabled(false);
        campoLargoNivelacionMuros.setEnabled(false);
        campoEspesorNivelacionMuros.setEnabled(false);

        // Nivelación de pisos
        campoLargoNivelacionPisos.setEnabled(false);
        campoAnchoNivelacionPisos.setEnabled(false);
        campoaEspesorNivelacionPisos.setEnabled(false);
    }

    private void habilitarCamposNivelacionMuros() {
        campoAltoNivelacionMuros.setEnabled(true);
        campoLargoNivelacionMuros.setEnabled(true);
        campoEspesorNivelacionMuros.setEnabled(true);
    }

    private void deshabilitarCamposNivelacionMuros() {
        campoAltoNivelacionMuros.setEnabled(false);
        campoLargoNivelacionMuros.setEnabled(false);
        campoEspesorNivelacionMuros.setEnabled(false);
    }

    private void habilitarCamposNivelacionPisos() {
        campoLargoNivelacionPisos.setEnabled(true);
        campoAnchoNivelacionPisos.setEnabled(true);
        campoaEspesorNivelacionPisos.setEnabled(true);
    }

    private void deshabilitarCamposNivelacionPisos() {
        campoLargoNivelacionPisos.setEnabled(false);
        campoAnchoNivelacionPisos.setEnabled(false);
        campoaEspesorNivelacionPisos.setEnabled(false);
    }

    /**
     * Valida que se haya seleccionado un elemento y que se hayan ingresado
     * todas las dimensiones requeridas.
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatos() {
        // Validar que se haya seleccionado un elemento
        if (!jcbNivelacionMuros.isSelected() && !jcbNivelacionPisos.isSelected()) {
            Utilities.mostrarMensajeError("Debe seleccionar un elemento.");
            return false;
        }

        // Validar que se hayan ingresado todas las dimensiones requeridas
        if (jcbNivelacionMuros.isSelected()) {
            if (!Utilities.validarCampoNumericoPositivo(campoAltoNivelacionMuros, "Alto (Nivelación Muros)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoLargoNivelacionMuros, "Largo (Nivelación Muros)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoEspesorNivelacionMuros, "Espesor (Nivelación Muros)")) {
                return false;
            }
        } else if (jcbNivelacionPisos.isSelected()) {
            if (!Utilities.validarCampoNumericoPositivo(campoLargoNivelacionPisos, "Largo (Nivelación Pisos)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoAnchoNivelacionPisos, "Ancho (Nivelación Pisos)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoaEspesorNivelacionPisos, "Espesor (Nivelación Pisos)")) {
                return false;
            }
        }

        return true;
    }

    /**
     * Crea un objeto ElementoDTO a partir de los datos ingresados.
     *
     * @return ElementoDTO con los datos del elemento seleccionado
     */
    private ElementoDTO crearElementoDTO() {
        ElementoDTO elemento = new ElementoDTO();

        // Establecer el tipo de elemento y las dimensiones correspondientes
        if (jcbNivelacionMuros.isSelected()) {
            elemento.setTipo(TipoElementoNegocio.NIVELACION_MUROS_VERTICAL);
            elemento.setAlto(Double.valueOf(campoAltoNivelacionMuros.getText().trim()));
            elemento.setLargo(Double.valueOf(campoLargoNivelacionMuros.getText().trim()));
            elemento.setEspesor(Double.valueOf(campoEspesorNivelacionMuros.getText().trim()));
            elemento.setAncho(0.0);
            elemento.setProfundidad(0.0);
        } else if (jcbNivelacionPisos.isSelected()) {
            elemento.setTipo(TipoElementoNegocio.NIVELACION_PISOS_HORIZONTAL);
            elemento.setLargo(Double.valueOf(campoLargoNivelacionPisos.getText().trim()));
            elemento.setAncho(Double.valueOf(campoAnchoNivelacionPisos.getText().trim()));
            elemento.setEspesor(Double.valueOf(campoaEspesorNivelacionPisos.getText().trim()));
            elemento.setAlto(0.0);
            elemento.setProfundidad(0.0);
        }

        return elemento;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombreEmpresa = new javax.swing.JLabel();
        lblSeleccion = new javax.swing.JLabel();
        jcbNivelacionMuros = new javax.swing.JCheckBox();
        jcbNivelacionPisos = new javax.swing.JCheckBox();
        lblAltoNivelacionMuros = new javax.swing.JLabel();
        campoAltoNivelacionMuros = new javax.swing.JTextField();
        lblLargoNivelacionPisos = new javax.swing.JLabel();
        campoLargoNivelacionPisos = new javax.swing.JTextField();
        campoAnchoNivelacionPisos = new javax.swing.JTextField();
        campoaEspesorNivelacionPisos = new javax.swing.JTextField();
        lblEspesorNivelacionPisos = new javax.swing.JLabel();
        lblAnchoNivelacionPisos = new javax.swing.JLabel();
        campoLargoNivelacionMuros = new javax.swing.JTextField();
        campoEspesorNivelacionMuros = new javax.swing.JTextField();
        lblEspesorNivelacionMuros = new javax.swing.JLabel();
        lblLargoNivelacionMuros = new javax.swing.JLabel();
        btnCalcular = new javax.swing.JButton();
        lblSeleccion1 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        nombreEmpresa.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        nombreEmpresa.setForeground(new java.awt.Color(0, 0, 0));
        nombreEmpresa.setText("BuildControl");

        lblSeleccion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSeleccion.setForeground(new java.awt.Color(0, 0, 0));
        lblSeleccion.setText("Elige que elemento quieres construir (uno a la vez):");

        jcbNivelacionMuros.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbNivelacionMuros.setForeground(new java.awt.Color(0, 0, 0));
        jcbNivelacionMuros.setText("Nivelación de muros (Vertical.)");

        jcbNivelacionPisos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbNivelacionPisos.setForeground(new java.awt.Color(0, 0, 0));
        jcbNivelacionPisos.setText("Nivelación de pisos (Horizontal.)");

        lblAltoNivelacionMuros.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAltoNivelacionMuros.setForeground(new java.awt.Color(0, 0, 0));
        lblAltoNivelacionMuros.setText("Alto:");

        campoAltoNivelacionMuros.setBackground(new java.awt.Color(255, 255, 255));
        campoAltoNivelacionMuros.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoAltoNivelacionMuros.setForeground(new java.awt.Color(0, 0, 0));

        lblLargoNivelacionPisos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLargoNivelacionPisos.setForeground(new java.awt.Color(0, 0, 0));
        lblLargoNivelacionPisos.setText("Largo:");

        campoLargoNivelacionPisos.setBackground(new java.awt.Color(255, 255, 255));
        campoLargoNivelacionPisos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoLargoNivelacionPisos.setForeground(new java.awt.Color(0, 0, 0));

        campoAnchoNivelacionPisos.setBackground(new java.awt.Color(255, 255, 255));
        campoAnchoNivelacionPisos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoAnchoNivelacionPisos.setForeground(new java.awt.Color(0, 0, 0));

        campoaEspesorNivelacionPisos.setBackground(new java.awt.Color(255, 255, 255));
        campoaEspesorNivelacionPisos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoaEspesorNivelacionPisos.setForeground(new java.awt.Color(0, 0, 0));

        lblEspesorNivelacionPisos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEspesorNivelacionPisos.setForeground(new java.awt.Color(0, 0, 0));
        lblEspesorNivelacionPisos.setText("Espesor:");

        lblAnchoNivelacionPisos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAnchoNivelacionPisos.setForeground(new java.awt.Color(0, 0, 0));
        lblAnchoNivelacionPisos.setText("Ancho:");

        campoLargoNivelacionMuros.setBackground(new java.awt.Color(255, 255, 255));
        campoLargoNivelacionMuros.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoLargoNivelacionMuros.setForeground(new java.awt.Color(0, 0, 0));

        campoEspesorNivelacionMuros.setBackground(new java.awt.Color(255, 255, 255));
        campoEspesorNivelacionMuros.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoEspesorNivelacionMuros.setForeground(new java.awt.Color(0, 0, 0));

        lblEspesorNivelacionMuros.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEspesorNivelacionMuros.setForeground(new java.awt.Color(0, 0, 0));
        lblEspesorNivelacionMuros.setText("Espesor:");

        lblLargoNivelacionMuros.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLargoNivelacionMuros.setForeground(new java.awt.Color(0, 0, 0));
        lblLargoNivelacionMuros.setText("Largo:");

        btnCalcular.setBackground(new java.awt.Color(95, 168, 211));
        btnCalcular.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCalcular.setForeground(new java.awt.Color(255, 255, 255));
        btnCalcular.setText("Calcular");
        btnCalcular.setBorderPainted(false);
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        lblSeleccion1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSeleccion1.setForeground(new java.awt.Color(0, 0, 0));
        lblSeleccion1.setText("Nota: Asegúrate de ingresar las dimensiones en metros.");

        btnVolver.setBackground(new java.awt.Color(95, 168, 211));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.setBorderPainted(false);
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSeleccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)
                        .addGap(18, 18, 18)
                        .addComponent(btnCalcular)
                        .addGap(66, 66, 66))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAltoNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAltoNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLargoNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoLargoNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoEspesorNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEspesorNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLargoNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoLargoNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAnchoNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAnchoNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoaEspesorNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEspesorNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(153, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(nombreEmpresa)
                .addGap(40, 40, 40)
                .addComponent(lblSeleccion)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLargoNivelacionPisos)
                            .addComponent(jcbNivelacionPisos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoLargoNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEspesorNivelacionMuros)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoEspesorNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblAltoNivelacionMuros)
                                    .addComponent(jcbNivelacionMuros))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoAltoNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLargoNivelacionMuros)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoLargoNivelacionMuros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblAnchoNivelacionPisos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoAnchoNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblEspesorNivelacionPisos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoaEspesorNivelacionPisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeleccion1)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        if (!validarDatos()) {
            return;
        }

        try {
            // Crear el ElementoDTO con los datos ingresados
            ElementoDTO elemento = crearElementoDTO();
            coordinadorNegocio.setElementoActual(elemento);
            
            this.dispose();
            coordinador.mostrarCalculoMaterialesNivelacion();
        } catch (Exception ex) {
            Utilities.mostrarMensajeError("Error al calcular: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        if (Utilities.mostrarConfirmacion("¿Desea volver a la selección de datos? Los datos ingresados se perderán.")) {
            this.dispose();
            coordinador.mostrarSeleccionDatos();
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnVolver;
    private javax.swing.JTextField campoAltoNivelacionMuros;
    private javax.swing.JTextField campoAnchoNivelacionPisos;
    private javax.swing.JTextField campoEspesorNivelacionMuros;
    private javax.swing.JTextField campoLargoNivelacionMuros;
    private javax.swing.JTextField campoLargoNivelacionPisos;
    private javax.swing.JTextField campoaEspesorNivelacionPisos;
    private javax.swing.JCheckBox jcbNivelacionMuros;
    private javax.swing.JCheckBox jcbNivelacionPisos;
    private javax.swing.JLabel lblAltoNivelacionMuros;
    private javax.swing.JLabel lblAnchoNivelacionPisos;
    private javax.swing.JLabel lblEspesorNivelacionMuros;
    private javax.swing.JLabel lblEspesorNivelacionPisos;
    private javax.swing.JLabel lblLargoNivelacionMuros;
    private javax.swing.JLabel lblLargoNivelacionPisos;
    private javax.swing.JLabel lblSeleccion;
    private javax.swing.JLabel lblSeleccion1;
    private javax.swing.JLabel nombreEmpresa;
    // End of variables declaration//GEN-END:variables
}
