/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import negocio_dto.CalculoDTO;
import utilities.Utilities;

/**
 * Formulario de la capa de presentación que muestra una tabla con el historial
 * de cálculos y un buscador por dirección, permite al usuario iniciar el
 * proceso de cálculo de materiales por elemento constructivo.
 *
 * @author Alejandra García Preciado - 252444
 */
public class InicioCalculosForm extends javax.swing.JFrame {

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
     * Historial de cálculos.
     */
    private List<CalculoDTO> listaCalculos;

    /**
     * Creates new form InicioCalculosForm
     */
    public InicioCalculosForm() {
        initComponents();
        this.coordinador = CoordinadorAplicacion.getInstancia();
        this.coordinadorNegocio = CoordinadorNegocio.getInstancia();

        // Centrar la ventana
        this.setLocationRelativeTo(null);

        // Limpiar formulario al inicializar
        limpiarFormulario();

        // Configurar la tabla con el botón "Consultar"
        configurarTablaHistorial();

        // Cargar el historial de cálculos
        cargarHistorialCalculos();
    }

    /**
     * Limpia el formulario al estado inicial.
     */
    private void limpiarFormulario() {
        // Limpiar campo de búsqueda
        Utilities.limpiarCampos(campoDireccion);

        // Limpiar cualquier elemento actual almacenado
        coordinadorNegocio.limpiarElementoActual();
    }

    /**
     * Configura la tabla de historial con el botón "Consultar".
     */
    private void configurarTablaHistorial() {
        // Modelo de tabla personalizado
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Dirección", "Fecha", "Acciones"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Solo la columna de acciones es editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 2 ? JButton.class : Object.class;
            }
        };

        tblHistorial.setModel(modelo);

        // Configurar renderer para botones
        tblHistorial.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JButton button = new JButton("Consultar");
                button.setBackground(new java.awt.Color(95, 168, 211));
                button.setForeground(new java.awt.Color(255, 255, 255));
                button.setFont(new java.awt.Font("Segoe UI", 1, 14));
                return button;
            }
        });

        // Configurar editor para botones
        tblHistorial.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(tblHistorial));

        // Ajustar ancho de columnas
        TableColumn column = tblHistorial.getColumnModel().getColumn(2);
        column.setPreferredWidth(100);
        column.setMaxWidth(100);
        column.setMinWidth(100);
    }

    /**
     * Carga el historial de cálculos.
     */
    private void cargarHistorialCalculos() {
        try {
            // Obtener historial de cálculos del coordinador de negocio
            listaCalculos = coordinadorNegocio.obtenerHistorialCalculos();

            // Limpiar la tabla
            DefaultTableModel modelo = (DefaultTableModel) tblHistorial.getModel();
            modelo.setRowCount(0);

            // Agregar los cálculos a la tabla
            if (listaCalculos != null && !listaCalculos.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                for (CalculoDTO calculo : listaCalculos) {
                    String direccion = calculo.getObra().getDireccion();
                    String fecha = calculo.getFecha().format(formatter);

                    modelo.addRow(new Object[]{direccion, fecha, "Consultar"});
                }
            }
        } catch (Exception ex) {
            Utilities.mostrarMensajeError("Error al cargar el historial de cálculos: " + ex.getMessage());
        }
    }

    /**
     * Busca cálculos por dirección.
     */
    private void buscarPorDireccion() {
        String direccionBusqueda = campoDireccion.getText().trim().toLowerCase();

        if (direccionBusqueda.isEmpty() || listaCalculos == null) {
            cargarHistorialCalculos();
            return;
        }

        // Filtrar los cálculos por dirección
        DefaultTableModel modelo = (DefaultTableModel) tblHistorial.getModel();
        modelo.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (CalculoDTO calculo : listaCalculos) {
            String direccion = calculo.getObra().getDireccion().toLowerCase();

            if (direccion.contains(direccionBusqueda)) {
                String fecha = calculo.getFecha().format(formatter);
                modelo.addRow(new Object[]{calculo.getObra().getDireccion(), fecha, "Consultar"});
            }
        }
    }

    /**
     * Abre la pantalla correspondiente para mostrar los detalles del cálculo
     *
     * @param indice Índice del cálculo seleccionado
     */
    private void consultarCalculo(int indice) {
        if (indice < 0 || indice >= listaCalculos.size()) {
            return;
        }

        CalculoDTO calculo = listaCalculos.get(indice);

        // Establecer el elemento del cálculo consultado
        coordinadorNegocio.setElementoActual(calculo.getElemento());

        // Determinar qué pantalla abrir según el tipo de elemento
        switch (calculo.getElemento().getTipo()) {
            case COLUMNA_CUADRADA:
            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
            case VIGA:
                // Abrir pantalla de concreto
                this.dispose();
                coordinador.mostrarCalculoMaterialesConcreto();
                break;

            case NIVELACION_MUROS_VERTICAL:
            case NIVELACION_PISOS_HORIZONTAL:
                // Abrir pantalla de nivelación
                this.dispose();
                coordinador.mostrarCalculoMaterialesNivelacion();
                break;

            case MURO_LADRILLO:
                // Abrir pantalla de mampostería
                this.dispose();
                coordinador.mostrarCalculoMaterialesMamposteria();
                break;
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

        nombreEmpresa = new javax.swing.JLabel();
        btnNuevoCalculo = new javax.swing.JButton();
        lblRecientes = new javax.swing.JLabel();
        campoDireccion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        nombreEmpresa.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        nombreEmpresa.setForeground(new java.awt.Color(0, 0, 0));
        nombreEmpresa.setText("BuildControl");

        btnNuevoCalculo.setBackground(new java.awt.Color(95, 168, 211));
        btnNuevoCalculo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnNuevoCalculo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCalculo.setText("Nuevo Cálculo");
        btnNuevoCalculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCalculoActionPerformed(evt);
            }
        });

        lblRecientes.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblRecientes.setForeground(new java.awt.Color(0, 0, 0));
        lblRecientes.setText("Recientes:");

        campoDireccion.setBackground(new java.awt.Color(255, 255, 255));
        campoDireccion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        campoDireccion.setForeground(new java.awt.Color(0, 0, 0));
        campoDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(6, 108, 169), 3));
        campoDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoDireccionKeyReleased(evt);
            }
        });

        tblHistorial.setBackground(new java.awt.Color(255, 255, 255));
        tblHistorial.setForeground(new java.awt.Color(0, 0, 0));
        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Dirección", "Fecha", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHistorial);

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
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVolver)
                    .addComponent(btnNuevoCalculo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRecientes, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(nombreEmpresa)
                .addGap(28, 28, 28)
                .addComponent(btnNuevoCalculo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblRecientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoCalculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCalculoActionPerformed
        // Limpiar cualquier elemento previo antes de iniciar nuevo cálculo
        coordinadorNegocio.iniciarNuevaSesionCalculo();
        this.dispose();
        coordinador.mostrarSeleccionDatos();
    }//GEN-LAST:event_btnNuevoCalculoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        if (Utilities.mostrarConfirmacion("¿Desea salir de la aplicación?")) {
            // Limpiar datos antes de salir
            coordinadorNegocio.limpiarElementoActual();
            this.dispose();
            System.exit(0);
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void campoDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoDireccionKeyReleased
        buscarPorDireccion();
    }//GEN-LAST:event_campoDireccionKeyReleased

    /**
     * Editor personalizado para la columna de botones en la tabla de historial.
     */
    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private boolean isPushed;
        private int fila;
        private JTable tabla;
        private InicioCalculosForm parent;

        public ButtonEditor(JTable tabla) {
            super(new JCheckBox());
            this.tabla = tabla;
            this.parent = (InicioCalculosForm) tabla.getTopLevelAncestor();

            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new java.awt.Color(95, 168, 211));
            button.setForeground(new java.awt.Color(255, 255, 255));
            button.setFont(new java.awt.Font("Segoe UI", 1, 14));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "Consultar" : value.toString();
            button.setText(label);
            fila = row;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Cuando se hace clic en el botón, se llama al método consultarCalculo
                parent.consultarCalculo(fila);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevoCalculo;
    private javax.swing.JButton btnVolver;
    private javax.swing.JTextField campoDireccion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecientes;
    private javax.swing.JLabel nombreEmpresa;
    private javax.swing.JTable tblHistorial;
    // End of variables declaration//GEN-END:variables
}
