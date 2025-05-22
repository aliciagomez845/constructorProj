/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import admCalculoMateriales.FAdmCalculoMateriales;
import admCalculoMateriales.IAdmCalculoMateriales;
import admObraSeleccionada.FAdmObraSeleccionada;
import admObraSeleccionada.IAdmObraSeleccionada;
import excepciones.AdmCalculoMaterialesException;
import excepciones.AdmObraSeleccionadaException;
import excepciones.PresentacionException;
import java.util.List;
import negocio_dto.CalculoDTO;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_dto.ObraDTO;
import negocio_enums.TipoElementoNegocio;
import negocio_enums.TipoMaterialNegocio;

/**
 * Coordinador de la capa de negocio.
 *
 * Clase encargada de conectar la capa de presentación con los subsistemas de
 * negocio. Todas las comunicaciones hacia los subsistemas se hacen a través de
 * esta clase. Implementa el patrón Singleton para garantizar una única
 * instancia.
 *
 * @author Alejandra García Preciado - 252444
 */
public class CoordinadorNegocio {

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static CoordinadorNegocio coordinadorNegocio;

    /**
     * Subsistema para la gestión de cálculos de materiales.
     */
    private final IAdmCalculoMateriales admCalculoMateriales;

    /**
     * Subsistema para la gestión de la obra seleccionada.
     */
    private final IAdmObraSeleccionada admObraSeleccionada;
    
    /**
     * Variable para almacenar el elemento actual.
     */
    private ElementoDTO elementoActual;

    /**
     * Constructor privado que inicializa los subsistemas.
     */
    private CoordinadorNegocio() {
        this.admCalculoMateriales = new FAdmCalculoMateriales();
        this.admObraSeleccionada = new FAdmObraSeleccionada();
    }

    /**
     * Método para obtener la instancia única del CoordinadorNegocio.
     *
     * @return La instancia única del coordinador
     */
    public static CoordinadorNegocio getInstancia() {
        if (coordinadorNegocio == null) {
            coordinadorNegocio = new CoordinadorNegocio();
        }
        return coordinadorNegocio;
    }

    /**
     * Activa la sesión de una obra específica.
     *
     * @param numero Número identificador de la obra
     * @return true si se activó correctamente, false en caso contrario
     * @throws PresentacionException Si ocurre un error al activar la sesión
     */
    public boolean activarSesionObra(String numero) throws PresentacionException {
        try {
            return admObraSeleccionada.activarSesionObra(numero);
        } catch (AdmObraSeleccionadaException ex) {
            throw new PresentacionException("Error al activar sesión de obra: " + ex.getMessage(), ex);
        }
    }

    /**
     * Cierra la sesión de la obra actual.
     *
     * @throws PresentacionException Si ocurre un error al cerrar la sesión
     */
    public void cerrarSesionObra() throws PresentacionException {
        try {
            admObraSeleccionada.cerrarSesionObra();
        } catch (AdmObraSeleccionadaException ex) {
            throw new PresentacionException("Error al cerrar sesión de obra: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene la dirección de la obra actualmente en sesión.
     *
     * @return Dirección de la obra en sesión
     * @throws PresentacionException Si ocurre un error al obtener la dirección
     */
    public String obtenerDireccionObraActual() throws PresentacionException {
        try {
            return admObraSeleccionada.obtenerDireccionObra();
        } catch (AdmObraSeleccionadaException ex) {
            throw new PresentacionException("Error al obtener dirección de obra: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene la información completa de la obra actualmente en sesión.
     *
     * @return Información de la obra en sesión
     * @throws PresentacionException Si ocurre un error al obtener la
     * información
     */
    public ObraDTO obtenerObraActual() throws PresentacionException {
        try {
            return admCalculoMateriales.obtenerObraActual();
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al obtener obra actual: " + ex.getMessage(), ex);
        }
    }

    /**
     * Calcula los materiales necesarios para un elemento constructivo
     * específico.
     *
     * @param elemento Elemento constructivo para el cálculo
     * @return Cálculo con los materiales necesarios
     * @throws PresentacionException Si ocurre un error durante el cálculo
     */
    public CalculoDTO calcularMateriales(ElementoDTO elemento) throws PresentacionException {
        try {
            return admCalculoMateriales.calcularMateriales(elemento);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al calcular materiales: " + ex.getMessage(), ex);
        }
    }

    /**
     * Guarda un cálculo de materiales en la base de datos.
     *
     * @param calculo Cálculo a guardar
     * @return Cálculo guardado con su ID asignado
     * @throws PresentacionException Si ocurre un error durante el guardado
     */
    public CalculoDTO guardarCalculo(CalculoDTO calculo) throws PresentacionException {
        try {
            return admCalculoMateriales.guardarCalculo(calculo);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al guardar cálculo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Genera y descarga un reporte en PDF de un cálculo específico.
     *
     * @param idCalculo Identificador del cálculo
     * @return Arreglo de bytes que representa el PDF
     * @throws PresentacionException Si ocurre un error durante la generación
     */
    public byte[] descargarPDF(String idCalculo) throws PresentacionException {
        try {
            return admCalculoMateriales.descargarPDF(idCalculo);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al descargar PDF: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene los materiales disponibles de un tipo específico.
     *
     * @param tipo Tipo de material a buscar
     * @return Lista de materiales del tipo especificado
     * @throws PresentacionException Si ocurre un error durante la búsqueda
     */
    public List<MaterialCalculoDTO> obtenerMateriales(TipoMaterialNegocio tipo) throws PresentacionException {
        try {
            return admCalculoMateriales.obtenerMateriales(tipo);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al obtener materiales: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene un elemento base de un tipo específico.
     *
     * @param tipo Tipo de elemento a buscar
     * @return Elemento del tipo especificado
     * @throws PresentacionException Si ocurre un error durante la búsqueda
     */
    public ElementoDTO obtenerElemento(TipoElementoNegocio tipo) throws PresentacionException {
        try {
            return admCalculoMateriales.obtenerElemento(tipo);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al obtener elemento: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene el historial de cálculos para la obra actualmente en sesión.
     *
     * @return Lista de cálculos que conforman el historial
     * @throws PresentacionException Si ocurre un error durante la consulta
     */
    public List<CalculoDTO> obtenerHistorialCalculos() throws PresentacionException {
        try {
            return admCalculoMateriales.obtenerHistorialCalculos();
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al obtener historial de cálculos: " + ex.getMessage(), ex);
        }
    }

    /**
     * Valida si las dimensiones de un elemento son correctas para su tipo.
     *
     * @param elemento Elemento a validar
     * @return true si las dimensiones son válidas, false en caso contrario
     * @throws PresentacionException Si ocurre un error durante la validación
     */
    public boolean validarDimensionesElemento(ElementoDTO elemento) throws PresentacionException {
        try {
            return admCalculoMateriales.validarDimensionesElemento(elemento);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al validar dimensiones: " + ex.getMessage(), ex);
        }
    }

    /**
     * Calcula el volumen de un elemento constructivo basado en su tipo y
     * dimensiones.
     *
     * @param elemento Elemento para el que se calculará el volumen
     * @return Volumen calculado en metros cúbicos
     * @throws PresentacionException Si ocurre un error durante el cálculo
     */
    public Double calcularVolumen(ElementoDTO elemento) throws PresentacionException {
        try {
            return admCalculoMateriales.calcularVolumen(elemento);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al calcular volumen: " + ex.getMessage(), ex);
        }
    }

    /**
     * Genera un reporte de cálculo con materiales calculados para un elemento.
     *
     * @param elemento Elemento para el que se generará el reporte
     * @return Cálculo con la información del reporte
     * @throws PresentacionException Si ocurre un error durante la generación
     */
    public CalculoDTO generarReporteCalculo(ElementoDTO elemento) throws PresentacionException {
        try {
            return admCalculoMateriales.generarReporteCalculo(elemento);
        } catch (AdmCalculoMaterialesException ex) {
            throw new PresentacionException("Error al generar reporte: " + ex.getMessage(), ex);
        }
    }
    
    /**
     * Establece el elemento actual para realizar cálculos
     *
     * @param elemento Elemento a establecer
     */
    public void setElementoActual(ElementoDTO elemento) {
        this.elementoActual = elemento;
    }

    /**
     * Obtiene el elemento actual para realizar cálculos
     *
     * @return Elemento actual
     */
    public ElementoDTO getElementoActual() {
        return this.elementoActual;
    }
    
}
