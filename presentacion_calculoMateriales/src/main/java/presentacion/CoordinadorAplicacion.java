/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import negocio_dto.ElementoDTO;

/**
 *
 * @author Alejandra García Preciado - 252444
 */
public class CoordinadorAplicacion {
    
    private static CoordinadorAplicacion coordinador;
    private InicioCalculosForm inicioCalculosForm;
    private SeleccionDatosForm seleccionDatosForm;
    private IngresoDimensionesConcretoForm ingresoDimensionesConcretoForm;
    private IngresoDimensionesNivelacionForm ingresoDimensionesNivelacionForm;
    private IngresoDimensionesMamposteriaForm ingresoDimensionesMamposteriaForm;
    private CalculoMaterialesConcretoForm calculoMaterialesConcretoForm;
    private CalculoMaterialesNivelacionForm calculoMaterialesNivelacionForm;
    private CalculoMaterialesMamposteriaForm calculoMaterialesMamposteriaForm;
    
    private CoordinadorAplicacion() {
    }
    
    public static CoordinadorAplicacion getInstancia() {
        if (coordinador == null) {
            coordinador = new CoordinadorAplicacion();
        }
        return coordinador;
    }
    
    public void mostrarInicioCalculos() {
        if (inicioCalculosForm == null) {
            inicioCalculosForm = new InicioCalculosForm();
        }
        inicioCalculosForm.setVisible(true);
    }
    
    public void mostrarSeleccionDatos() {
        if (seleccionDatosForm == null) {
            seleccionDatosForm = new SeleccionDatosForm();
        }
        seleccionDatosForm.setVisible(true);
    }
    
    public void mostrarIngresoDimensionesConcreto() {
        if (ingresoDimensionesConcretoForm == null) {
            ingresoDimensionesConcretoForm = new IngresoDimensionesConcretoForm();
        }
        ingresoDimensionesConcretoForm.setVisible(true);
    }
    
    public void mostrarIngresoDimensionesNivelacion() {
        if (ingresoDimensionesNivelacionForm == null) {
            ingresoDimensionesNivelacionForm = new IngresoDimensionesNivelacionForm();
        }
        ingresoDimensionesNivelacionForm.setVisible(true);
    }
    
    public void mostrarIngresoDimensionesMamposteria() {
        if (ingresoDimensionesMamposteriaForm == null) {
            ingresoDimensionesMamposteriaForm = new IngresoDimensionesMamposteriaForm();
        }
        ingresoDimensionesMamposteriaForm.setVisible(true);
    }
    
    public void mostrarCalculoMaterialesConcreto() {
        if (calculoMaterialesConcretoForm == null) {
            calculoMaterialesConcretoForm = new CalculoMaterialesConcretoForm();
        }
        calculoMaterialesConcretoForm.setVisible(true);
    }
    
    public void mostrarCalculoMaterialesNivelacion() {
        if (calculoMaterialesNivelacionForm == null) {
            calculoMaterialesNivelacionForm = new CalculoMaterialesNivelacionForm();
        }
        calculoMaterialesNivelacionForm.setVisible(true);
    }
    
    public void mostrarCalculoMaterialesMamposteria() {
        if (calculoMaterialesMamposteriaForm == null) {
            calculoMaterialesMamposteriaForm = new CalculoMaterialesMamposteriaForm();
        }
        calculoMaterialesMamposteriaForm.setVisible(true);
    }
    
    /**
     * Realiza un cálculo de materiales para el elemento actual.
     *
     * @param elemento Elemento para el que se calculará
     */
    public void calcularMateriales(ElementoDTO elemento) {
        // Almacenar el elemento en el CoordinadorNegocio
        CoordinadorNegocio.getInstancia().setElementoActual(elemento);

        // Abrir la pantalla correspondiente según el tipo de elemento
        switch (elemento.getTipo()) {
            case COLUMNA_CUADRADA:
            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
            case VIGA:
                mostrarCalculoMaterialesConcreto();
                break;

            case NIVELACION_MUROS_VERTICAL:
            case NIVELACION_PISOS_HORIZONTAL:
                mostrarCalculoMaterialesNivelacion();
                break;

            case MURO_LADRILLO:
                mostrarCalculoMaterialesMamposteria();
                break;
        }
    }
    
    public void reset() {
        if (inicioCalculosForm != null) {
            inicioCalculosForm = null;
        }
        if (seleccionDatosForm != null) {
            seleccionDatosForm = null;
        }
        if (ingresoDimensionesConcretoForm != null) {
            ingresoDimensionesConcretoForm = null;
        }
        if (ingresoDimensionesNivelacionForm != null) {
            ingresoDimensionesNivelacionForm = null;
        }
        if (ingresoDimensionesMamposteriaForm != null) {
            ingresoDimensionesMamposteriaForm = null;
        }
        if (calculoMaterialesConcretoForm != null) {
            calculoMaterialesConcretoForm = null;
        }
        if (calculoMaterialesNivelacionForm != null) {
            calculoMaterialesNivelacionForm = null;
        }
        if (calculoMaterialesMamposteriaForm != null) {
            calculoMaterialesMamposteriaForm = null;
        }
    }
    
}
