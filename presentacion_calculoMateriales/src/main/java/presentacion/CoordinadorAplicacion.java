/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import excepciones.PresentacionException;
import negocio_dto.ElementoDTO;

/**
 * Coordinador de la capa de presentación.
 *
 * Esta clase se encarga de la navegación entre formularios y coordina toda la
 * comunicación entre las diferentes pantallas del sistema. Implementa el patrón
 * Singleton y asegura que los formularios siempre se inicialicen limpios cuando
 * se navega entre ellos.
 *
 * @author Alejandra García Preciado - 252444
 */
public class CoordinadorAplicacion {

    private static CoordinadorAplicacion coordinador;
    private ObraSeleccionadaForm obraSeleccionadaForm;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private CoordinadorAplicacion() {
    }

    /**
     * Obtiene la instancia única del coordinador (patrón Singleton).
     *
     * @return La instancia única del coordinador
     */
    public static CoordinadorAplicacion getInstancia() {
        if (coordinador == null) {
            coordinador = new CoordinadorAplicacion();
        }
        return coordinador;
    }

    /**
     * Muestra el formulario de obra seleccionada.
     *
     * @throws PresentacionException Si ocurre un error al mostrar el formulario
     */
    public void mostrarObraSeleccionada() throws PresentacionException {
        if (obraSeleccionadaForm == null) {
            obraSeleccionadaForm = new ObraSeleccionadaForm();
        }
        obraSeleccionadaForm.setVisible(true);
    }

    /**
     * Muestra el formulario de inicio de cálculos. Siempre crea una nueva
     * instancia para asegurar que esté limpio.
     */
    public void mostrarInicioCalculos() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        InicioCalculosForm inicioCalculosForm = new InicioCalculosForm();
        inicioCalculosForm.setVisible(true);
    }

    /**
     * Muestra el formulario de selección de datos. Siempre crea una nueva
     * instancia para asegurar que esté limpio.
     */
    public void mostrarSeleccionDatos() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        SeleccionDatosForm seleccionDatosForm = new SeleccionDatosForm();
        seleccionDatosForm.setVisible(true);
    }

    /**
     * Muestra el formulario de ingreso de dimensiones para concreto. Siempre
     * crea una nueva instancia para asegurar que esté limpio.
     */
    public void mostrarIngresoDimensionesConcreto() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        IngresoDimensionesConcretoForm ingresoDimensionesConcretoForm = new IngresoDimensionesConcretoForm();
        ingresoDimensionesConcretoForm.setVisible(true);
    }

    /**
     * Muestra el formulario de ingreso de dimensiones para nivelación. Siempre
     * crea una nueva instancia para asegurar que esté limpio.
     */
    public void mostrarIngresoDimensionesNivelacion() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        IngresoDimensionesNivelacionForm ingresoDimensionesNivelacionForm = new IngresoDimensionesNivelacionForm();
        ingresoDimensionesNivelacionForm.setVisible(true);
    }

    /**
     * Muestra el formulario de ingreso de dimensiones para mampostería. Siempre
     * crea una nueva instancia para asegurar que esté limpio.
     */
    public void mostrarIngresoDimensionesMamposteria() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        IngresoDimensionesMamposteriaForm ingresoDimensionesMamposteriaForm = new IngresoDimensionesMamposteriaForm();
        ingresoDimensionesMamposteriaForm.setVisible(true);
    }

    /**
     * Muestra el formulario de cálculo de materiales para concreto. Siempre
     * crea una nueva instancia para asegurar que esté limpio.
     */
    public void mostrarCalculoMaterialesConcreto() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        CalculoMaterialesConcretoForm calculoMaterialesConcretoForm = new CalculoMaterialesConcretoForm();
        calculoMaterialesConcretoForm.setVisible(true);
    }

    /**
     * Muestra el formulario de cálculo de materiales para nivelación. Siempre
     * crea una nueva instancia para asegurar que esté limpio.
     */
    public void mostrarCalculoMaterialesNivelacion() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        CalculoMaterialesNivelacionForm calculoMaterialesNivelacionForm = new CalculoMaterialesNivelacionForm();
        calculoMaterialesNivelacionForm.setVisible(true);
    }

    /**
     * Muestra el formulario de cálculo de materiales para mampostería. Siempre
     * crea una nueva instancia para asegurar que esté limpio.
     */
    public void mostrarCalculoMaterialesMamposteria() {
        // Siempre crear una nueva instancia para asegurar que esté limpia
        CalculoMaterialesMamposteriaForm calculoMaterialesMamposteriaForm = new CalculoMaterialesMamposteriaForm();
        calculoMaterialesMamposteriaForm.setVisible(true);
    }

    /**
     * Realiza un cálculo de materiales para el elemento actual.
     *
     * Este método determina qué tipo de pantalla mostrar basándose en el tipo
     * de elemento y delega la responsabilidad al método correspondiente.
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

}
