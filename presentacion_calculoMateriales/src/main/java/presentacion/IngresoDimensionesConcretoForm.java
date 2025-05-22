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
public class IngresoDimensionesConcretoForm extends javax.swing.JFrame {

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
     * Creates new form IngresoDimensionesForm
     */
    public IngresoDimensionesConcretoForm() {
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
        jcbColumna.addActionListener((e) -> {
            if (jcbColumna.isSelected()) {

                // Deshabilitar los otros checkbox
                jcbLosaContrapiso.setSelected(false);
                jcbLosaEntrepiso.setSelected(false);
                jcbVigas.setSelected(false);

                // Deshabilitar todos los campos primero
                deshabilitarTodosCampos();

                // Habilitar solo los campos necesarios para columna
                habilitarCamposColumna();
            } else {
                // Si se "des selecciona", deshabilitar los campos
                deshabilitarCamposColumna();
            }
        });

        jcbLosaContrapiso.addActionListener((e) -> {
            if (jcbLosaContrapiso.isSelected()) {

                // Deshabilitar los otros checkbox
                jcbColumna.setSelected(false);
                jcbLosaEntrepiso.setSelected(false);
                jcbVigas.setSelected(false);

                // Deshabilitar todos los campos primero
                deshabilitarTodosCampos();

                // Habilitar solo los campos necesarios para losa de contrapiso
                habilitarCamposLosaContrapiso();
            } else {
                // Si se "des selecciona", deshabilitar los campos
                deshabilitarCamposLosaContrapiso();
            }
        });

        jcbLosaEntrepiso.addActionListener((e) -> {
            if (jcbLosaEntrepiso.isSelected()) {

                // Deshabilitar los otros checkbox
                jcbColumna.setSelected(false);
                jcbLosaContrapiso.setSelected(false);
                jcbVigas.setSelected(false);

                // Deshabilitar todos los campos primero
                deshabilitarTodosCampos();

                // Habilitar solo los campos necesarios para losa de entrepiso
                habilitarCamposLosaEntrepiso();
            } else {
                // Si se "des selecciona", deshabilitar los campos
                deshabilitarCamposLosaEntrepiso();
            }
        });

        jcbVigas.addActionListener((e) -> {
            if (jcbVigas.isSelected()) {

                // Deshabilitar los otros checkbox
                jcbColumna.setSelected(false);
                jcbLosaContrapiso.setSelected(false);
                jcbLosaEntrepiso.setSelected(false);

                // Deshabilitar todos los campos primero
                deshabilitarTodosCampos();

                // Habilitar solo los campos necesarios para vigas
                habilitarCamposVigas();
            } else {
                // Si se "des selecciona", deshabilitar los campos
                deshabilitarCamposVigas();
            }
        });
    }

    // Métodos para habilitar y deshabilitar campos según el elemento seleccionado
    private void deshabilitarTodosCampos() {
        // Columna
        campoAltoColumna.setEnabled(false);
        campoAnchoColumna.setEnabled(false);

        // Losa de contrapiso
        campoLargoLosaContrapiso.setEnabled(false);
        campoAnchoLosaContrapiso.setEnabled(false);
        campoaEspesorLosaContrapiso.setEnabled(false);

        // Losa de entrepiso
        campoLargoLosaEntrepiso.setEnabled(false);
        campoAnchoLosaEntrepiso.setEnabled(false);
        campoEspesorLosaEntrepiso.setEnabled(false);

        // Vigas
        campoLargoVigas.setEnabled(false);
        campoAnchoVigas.setEnabled(false);
        campoEspesorVigas.setEnabled(false);
    }

    private void habilitarCamposColumna() {
        campoAltoColumna.setEnabled(true);
        campoAnchoColumna.setEnabled(true);
    }

    private void deshabilitarCamposColumna() {
        campoAltoColumna.setEnabled(false);
        campoAnchoColumna.setEnabled(false);
    }

    private void habilitarCamposLosaContrapiso() {
        campoLargoLosaContrapiso.setEnabled(true);
        campoAnchoLosaContrapiso.setEnabled(true);
        campoaEspesorLosaContrapiso.setEnabled(true);
    }

    private void deshabilitarCamposLosaContrapiso() {
        campoLargoLosaContrapiso.setEnabled(false);
        campoAnchoLosaContrapiso.setEnabled(false);
        campoaEspesorLosaContrapiso.setEnabled(false);
    }

    private void habilitarCamposLosaEntrepiso() {
        campoLargoLosaEntrepiso.setEnabled(true);
        campoAnchoLosaEntrepiso.setEnabled(true);
        campoEspesorLosaEntrepiso.setEnabled(true);
    }

    private void deshabilitarCamposLosaEntrepiso() {
        campoLargoLosaEntrepiso.setEnabled(false);
        campoAnchoLosaEntrepiso.setEnabled(false);
        campoEspesorLosaEntrepiso.setEnabled(false);
    }

    private void habilitarCamposVigas() {
        campoLargoVigas.setEnabled(true);
        campoAnchoVigas.setEnabled(true);
        campoEspesorVigas.setEnabled(true);
    }

    private void deshabilitarCamposVigas() {
        campoLargoVigas.setEnabled(false);
        campoAnchoVigas.setEnabled(false);
        campoEspesorVigas.setEnabled(false);
    }

    /**
     * Valida que se haya seleccionado un elemento y que se hayan ingresado
     * todas las dimensiones requeridas.
     *
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatos() {
        // Validar que se haya seleccionado un elemento
        if (!jcbColumna.isSelected() && !jcbLosaContrapiso.isSelected()
                && !jcbLosaEntrepiso.isSelected() && !jcbVigas.isSelected()) {
            Utilities.mostrarMensajeError("Debe seleccionar un elemento.");
            return false;
        }

        // Validar que se hayan ingresado todas las dimensiones requeridas
        if (jcbColumna.isSelected()) {
            if (!Utilities.validarCampoNumericoPositivo(campoAltoColumna, "Alto (Columna)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoAnchoColumna, "Ancho (Columna)")) {
                return false;
            }
        } else if (jcbLosaContrapiso.isSelected()) {
            if (!Utilities.validarCampoNumericoPositivo(campoLargoLosaContrapiso, "Largo (Losa Contrapiso)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoAnchoLosaContrapiso, "Ancho (Losa Contrapiso)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoaEspesorLosaContrapiso, "Espesor (Losa Contrapiso)")) {
                return false;
            }
        } else if (jcbLosaEntrepiso.isSelected()) {
            if (!Utilities.validarCampoNumericoPositivo(campoLargoLosaEntrepiso, "Largo (Losa Entrepiso)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoAnchoLosaEntrepiso, "Ancho (Losa Entrepiso)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoEspesorLosaEntrepiso, "Espesor (Losa Entrepiso)")) {
                return false;
            }
        } else if (jcbVigas.isSelected()) {
            if (!Utilities.validarCampoNumericoPositivo(campoLargoVigas, "Largo (Vigas)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoAnchoVigas, "Ancho (Vigas)")) {
                return false;
            }
            if (!Utilities.validarCampoNumericoPositivo(campoEspesorVigas, "Espesor (Vigas)")) {
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
        if (jcbColumna.isSelected()) {
            elemento.setTipo(TipoElementoNegocio.COLUMNA_CUADRADA);
            elemento.setAlto(Double.valueOf(campoAltoColumna.getText().trim()));
            elemento.setAncho(Double.valueOf(campoAnchoColumna.getText().trim()));
            elemento.setEspesor(0.0);
            elemento.setLargo(0.0);
            elemento.setProfundidad(0.0);
        } else if (jcbLosaContrapiso.isSelected()) {
            elemento.setTipo(TipoElementoNegocio.LOSA_CONTRAPISO);
            elemento.setLargo(Double.valueOf(campoLargoLosaContrapiso.getText().trim()));
            elemento.setAncho(Double.valueOf(campoAnchoLosaContrapiso.getText().trim()));
            elemento.setEspesor(Double.valueOf(campoaEspesorLosaContrapiso.getText().trim()));
            elemento.setAlto(0.0);
            elemento.setProfundidad(0.0);
        } else if (jcbLosaEntrepiso.isSelected()) {
            elemento.setTipo(TipoElementoNegocio.LOSA_ENTREPISO);
            elemento.setLargo(Double.valueOf(campoLargoLosaEntrepiso.getText().trim()));
            elemento.setAncho(Double.valueOf(campoAnchoLosaEntrepiso.getText().trim()));
            elemento.setEspesor(Double.valueOf(campoEspesorLosaEntrepiso.getText().trim()));
            elemento.setAlto(0.0);
            elemento.setProfundidad(0.0);
        } else if (jcbVigas.isSelected()) {
            elemento.setTipo(TipoElementoNegocio.VIGA);
            elemento.setLargo(Double.valueOf(campoLargoVigas.getText().trim()));
            elemento.setAncho(Double.valueOf(campoAnchoVigas.getText().trim()));
            elemento.setEspesor(Double.valueOf(campoEspesorVigas.getText().trim()));
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
        jcbColumna = new javax.swing.JCheckBox();
        jcbLosaContrapiso = new javax.swing.JCheckBox();
        jcbLosaEntrepiso = new javax.swing.JCheckBox();
        jcbVigas = new javax.swing.JCheckBox();
        btnCalcular = new javax.swing.JButton();
        lblAltoColumna = new javax.swing.JLabel();
        campoAltoColumna = new javax.swing.JTextField();
        lblLargoLosaContrapiso = new javax.swing.JLabel();
        campoLargoLosaContrapiso = new javax.swing.JTextField();
        lblLargoLosaEntrepiso = new javax.swing.JLabel();
        campoLargoLosaEntrepiso = new javax.swing.JTextField();
        campoLargoVigas = new javax.swing.JTextField();
        lblLargoVigas = new javax.swing.JLabel();
        lblAnchoVigas = new javax.swing.JLabel();
        campoAnchoVigas = new javax.swing.JTextField();
        campoEspesorVigas = new javax.swing.JTextField();
        lblEspesorVigas = new javax.swing.JLabel();
        campoAnchoColumna = new javax.swing.JTextField();
        lblAnchoColumna = new javax.swing.JLabel();
        lblAnchoLosaContrapiso = new javax.swing.JLabel();
        campoAnchoLosaContrapiso = new javax.swing.JTextField();
        campoaEspesorLosaContrapiso = new javax.swing.JTextField();
        lblEspesorLosaContrapiso = new javax.swing.JLabel();
        campoAnchoLosaEntrepiso = new javax.swing.JTextField();
        lblAnchoLosaEntrepiso = new javax.swing.JLabel();
        campoEspesorLosaEntrepiso = new javax.swing.JTextField();
        lblEspesorLosaEntrepiso = new javax.swing.JLabel();
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

        jcbColumna.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbColumna.setForeground(new java.awt.Color(0, 0, 0));
        jcbColumna.setText("Columna cuadrada.");

        jcbLosaContrapiso.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbLosaContrapiso.setForeground(new java.awt.Color(0, 0, 0));
        jcbLosaContrapiso.setText("Losa de contrapiso.");

        jcbLosaEntrepiso.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbLosaEntrepiso.setForeground(new java.awt.Color(0, 0, 0));
        jcbLosaEntrepiso.setText("Losa de entrepiso.");

        jcbVigas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbVigas.setForeground(new java.awt.Color(0, 0, 0));
        jcbVigas.setText("Vigas.");

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

        lblAltoColumna.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAltoColumna.setForeground(new java.awt.Color(0, 0, 0));
        lblAltoColumna.setText("Alto:");

        campoAltoColumna.setBackground(new java.awt.Color(255, 255, 255));
        campoAltoColumna.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoAltoColumna.setForeground(new java.awt.Color(0, 0, 0));

        lblLargoLosaContrapiso.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLargoLosaContrapiso.setForeground(new java.awt.Color(0, 0, 0));
        lblLargoLosaContrapiso.setText("Largo:");

        campoLargoLosaContrapiso.setBackground(new java.awt.Color(255, 255, 255));
        campoLargoLosaContrapiso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoLargoLosaContrapiso.setForeground(new java.awt.Color(0, 0, 0));

        lblLargoLosaEntrepiso.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLargoLosaEntrepiso.setForeground(new java.awt.Color(0, 0, 0));
        lblLargoLosaEntrepiso.setText("Largo:");

        campoLargoLosaEntrepiso.setBackground(new java.awt.Color(255, 255, 255));
        campoLargoLosaEntrepiso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoLargoLosaEntrepiso.setForeground(new java.awt.Color(0, 0, 0));

        campoLargoVigas.setBackground(new java.awt.Color(255, 255, 255));
        campoLargoVigas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoLargoVigas.setForeground(new java.awt.Color(0, 0, 0));

        lblLargoVigas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLargoVigas.setForeground(new java.awt.Color(0, 0, 0));
        lblLargoVigas.setText("Largo:");

        lblAnchoVigas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAnchoVigas.setForeground(new java.awt.Color(0, 0, 0));
        lblAnchoVigas.setText("Ancho:");

        campoAnchoVigas.setBackground(new java.awt.Color(255, 255, 255));
        campoAnchoVigas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoAnchoVigas.setForeground(new java.awt.Color(0, 0, 0));

        campoEspesorVigas.setBackground(new java.awt.Color(255, 255, 255));
        campoEspesorVigas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoEspesorVigas.setForeground(new java.awt.Color(0, 0, 0));

        lblEspesorVigas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEspesorVigas.setForeground(new java.awt.Color(0, 0, 0));
        lblEspesorVigas.setText("Espesor:");

        campoAnchoColumna.setBackground(new java.awt.Color(255, 255, 255));
        campoAnchoColumna.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoAnchoColumna.setForeground(new java.awt.Color(0, 0, 0));

        lblAnchoColumna.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAnchoColumna.setForeground(new java.awt.Color(0, 0, 0));
        lblAnchoColumna.setText("Ancho:");

        lblAnchoLosaContrapiso.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAnchoLosaContrapiso.setForeground(new java.awt.Color(0, 0, 0));
        lblAnchoLosaContrapiso.setText("Ancho:");

        campoAnchoLosaContrapiso.setBackground(new java.awt.Color(255, 255, 255));
        campoAnchoLosaContrapiso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoAnchoLosaContrapiso.setForeground(new java.awt.Color(0, 0, 0));

        campoaEspesorLosaContrapiso.setBackground(new java.awt.Color(255, 255, 255));
        campoaEspesorLosaContrapiso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoaEspesorLosaContrapiso.setForeground(new java.awt.Color(0, 0, 0));

        lblEspesorLosaContrapiso.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEspesorLosaContrapiso.setForeground(new java.awt.Color(0, 0, 0));
        lblEspesorLosaContrapiso.setText("Espesor:");

        campoAnchoLosaEntrepiso.setBackground(new java.awt.Color(255, 255, 255));
        campoAnchoLosaEntrepiso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoAnchoLosaEntrepiso.setForeground(new java.awt.Color(0, 0, 0));

        lblAnchoLosaEntrepiso.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAnchoLosaEntrepiso.setForeground(new java.awt.Color(0, 0, 0));
        lblAnchoLosaEntrepiso.setText("Ancho:");

        campoEspesorLosaEntrepiso.setBackground(new java.awt.Color(255, 255, 255));
        campoEspesorLosaEntrepiso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        campoEspesorLosaEntrepiso.setForeground(new java.awt.Color(0, 0, 0));

        lblEspesorLosaEntrepiso.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEspesorLosaEntrepiso.setForeground(new java.awt.Color(0, 0, 0));
        lblEspesorLosaEntrepiso.setText("Espesor:");

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
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSeleccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)
                        .addGap(29, 29, 29)
                        .addComponent(btnCalcular)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbColumna, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbVigas, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(88, 88, 88)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoLargoVigas, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblLargoVigas, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAnchoVigas, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAnchoVigas, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoEspesorVigas, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEspesorVigas, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAltoColumna, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAltoColumna, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAnchoColumna, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAnchoColumna, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLargoLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoLargoLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAnchoLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAnchoLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoaEspesorLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEspesorLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLargoLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoLargoLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAnchoLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoAnchoLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoEspesorLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEspesorLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 96, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(nombreEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblLargoLosaContrapiso)
                                    .addComponent(jcbLosaContrapiso))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoLargoLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSeleccion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblAltoColumna)
                                            .addComponent(jcbColumna))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoAltoColumna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblAnchoColumna)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoAnchoColumna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblAnchoLosaContrapiso)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoAnchoLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblEspesorLosaContrapiso)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoaEspesorLosaContrapiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblLargoLosaEntrepiso)
                                    .addComponent(jcbLosaEntrepiso))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoLargoLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblEspesorLosaEntrepiso)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoEspesorLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblAnchoLosaEntrepiso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoAnchoLosaEntrepiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEspesorVigas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoEspesorVigas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAnchoVigas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoAnchoVigas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLargoVigas)
                            .addComponent(jcbVigas))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoLargoVigas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeleccion1))
                .addGap(30, 30, 30))
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
            coordinador.mostrarCalculoMaterialesConcreto();
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
    private javax.swing.JTextField campoAltoColumna;
    private javax.swing.JTextField campoAnchoColumna;
    private javax.swing.JTextField campoAnchoLosaContrapiso;
    private javax.swing.JTextField campoAnchoLosaEntrepiso;
    private javax.swing.JTextField campoAnchoVigas;
    private javax.swing.JTextField campoEspesorLosaEntrepiso;
    private javax.swing.JTextField campoEspesorVigas;
    private javax.swing.JTextField campoLargoLosaContrapiso;
    private javax.swing.JTextField campoLargoLosaEntrepiso;
    private javax.swing.JTextField campoLargoVigas;
    private javax.swing.JTextField campoaEspesorLosaContrapiso;
    private javax.swing.JCheckBox jcbColumna;
    private javax.swing.JCheckBox jcbLosaContrapiso;
    private javax.swing.JCheckBox jcbLosaEntrepiso;
    private javax.swing.JCheckBox jcbVigas;
    private javax.swing.JLabel lblAltoColumna;
    private javax.swing.JLabel lblAnchoColumna;
    private javax.swing.JLabel lblAnchoLosaContrapiso;
    private javax.swing.JLabel lblAnchoLosaEntrepiso;
    private javax.swing.JLabel lblAnchoVigas;
    private javax.swing.JLabel lblEspesorLosaContrapiso;
    private javax.swing.JLabel lblEspesorLosaEntrepiso;
    private javax.swing.JLabel lblEspesorVigas;
    private javax.swing.JLabel lblLargoLosaContrapiso;
    private javax.swing.JLabel lblLargoLosaEntrepiso;
    private javax.swing.JLabel lblLargoVigas;
    private javax.swing.JLabel lblSeleccion;
    private javax.swing.JLabel lblSeleccion1;
    private javax.swing.JLabel nombreEmpresa;
    // End of variables declaration//GEN-END:variables
}
