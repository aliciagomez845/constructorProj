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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(CoordinadorNegocio.class.getName());

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
        LOGGER.info("Inicializando CoordinadorNegocio");
        this.admCalculoMateriales = new FAdmCalculoMateriales();
        this.admObraSeleccionada = new FAdmObraSeleccionada();
        LOGGER.info("CoordinadorNegocio inicializado correctamente");
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
            LOGGER.info("Activando sesión para obra: " + numero);
            boolean resultado = admObraSeleccionada.activarSesionObra(numero);
            LOGGER.info("Sesión activada: " + resultado);
            return resultado;
        } catch (AdmObraSeleccionadaException ex) {
            LOGGER.log(Level.SEVERE, "Error al activar sesión de obra", ex);
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
            LOGGER.info("Cerrando sesión de obra");
            admObraSeleccionada.cerrarSesionObra();
            LOGGER.info("Sesión cerrada correctamente");
        } catch (AdmObraSeleccionadaException ex) {
            LOGGER.log(Level.SEVERE, "Error al cerrar sesión de obra", ex);
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
            LOGGER.info("Obteniendo dirección de obra actual");
            String direccion = admObraSeleccionada.obtenerDireccionObra();
            LOGGER.info("Dirección obtenida: " + direccion);
            return direccion;
        } catch (AdmObraSeleccionadaException ex) {
            LOGGER.log(Level.SEVERE, "Error al obtener dirección de obra", ex);
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
            LOGGER.info("Obteniendo obra actual");
            ObraDTO obra = admCalculoMateriales.obtenerObraActual();
            LOGGER.info("Obra obtenida: " + (obra != null ? obra.getDireccion() : "null"));
            return obra;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al obtener obra actual", ex);
            throw new PresentacionException("Error al obtener obra actual: " + ex.getMessage(), ex);
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
            LOGGER.info("Obteniendo historial de cálculos");

            // Verificar que hay una sesión activa
            String idObra = admObraSeleccionada.obtenerSesion();
            LOGGER.info("ID de obra en sesión: " + idObra);

            if (idObra == null || idObra.isEmpty()) {
                LOGGER.warning("No hay una obra en sesión activa");
                throw new PresentacionException("No hay una obra seleccionada actualmente");
            }

            List<CalculoDTO> historial = admCalculoMateriales.obtenerHistorialCalculos();

            if (historial == null) {
                LOGGER.warning("El historial devuelto es null, creando lista vacía");
                historial = new java.util.ArrayList<>();
            }

            LOGGER.info("Historial obtenido con " + historial.size() + " elementos");

            // Log detallado de cada cálculo por unos problemitas
            for (int i = 0; i < historial.size(); i++) {
                CalculoDTO calculo = historial.get(i);
                LOGGER.info("Cálculo " + i + ": "
                        + (calculo != null
                                ? "Obra=" + (calculo.getObra() != null ? calculo.getObra().getDireccion() : "null")
                                + ", Fecha=" + (calculo.getFecha() != null ? calculo.getFecha().toString() : "null")
                                + ", Elemento=" + (calculo.getElemento() != null ? calculo.getElemento().getTipo() : "null")
                                : "null"));
            }

            return historial;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al obtener historial de cálculos", ex);
            throw new PresentacionException("Error al obtener historial de cálculos: " + ex.getMessage(), ex);
        } catch (AdmObraSeleccionadaException ex) {
            LOGGER.log(Level.SEVERE, "Error al verificar sesión de obra", ex);
            throw new PresentacionException("Error al verificar sesión de obra: " + ex.getMessage(), ex);
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
            LOGGER.info("Calculando materiales para elemento: " + (elemento != null ? elemento.getTipo() : "null"));
            CalculoDTO resultado = admCalculoMateriales.calcularMateriales(elemento);
            LOGGER.info("Cálculo completado exitosamente");
            return resultado;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al calcular materiales", ex);
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
            LOGGER.info("Guardando cálculo");
            CalculoDTO resultado = admCalculoMateriales.guardarCalculo(calculo);
            LOGGER.info("Cálculo guardado exitosamente");
            return resultado;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al guardar cálculo", ex);
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
            LOGGER.info("Descargando PDF para cálculo: " + idCalculo);
            byte[] resultado = admCalculoMateriales.descargarPDF(idCalculo);
            LOGGER.info("PDF generado exitosamente");
            return resultado;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al descargar PDF", ex);
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
            LOGGER.info("Obteniendo materiales de tipo: " + tipo);
            List<MaterialCalculoDTO> materiales = admCalculoMateriales.obtenerMateriales(tipo);
            LOGGER.info("Materiales obtenidos: " + (materiales != null ? materiales.size() : 0));
            return materiales;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al obtener materiales", ex);
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
            LOGGER.info("Obteniendo elemento de tipo: " + tipo);
            ElementoDTO elemento = admCalculoMateriales.obtenerElemento(tipo);
            LOGGER.info("Elemento obtenido: " + (elemento != null ? elemento.getTipo() : "null"));
            return elemento;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al obtener elemento", ex);
            throw new PresentacionException("Error al obtener elemento: " + ex.getMessage(), ex);
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
            LOGGER.info("Validando dimensiones de elemento");
            boolean valido = admCalculoMateriales.validarDimensionesElemento(elemento);
            LOGGER.info("Dimensiones válidas: " + valido);
            return valido;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al validar dimensiones", ex);
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
            LOGGER.info("Calculando volumen de elemento");
            Double volumen = admCalculoMateriales.calcularVolumen(elemento);
            LOGGER.info("Volumen calculado: " + volumen);
            return volumen;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al calcular volumen", ex);
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
            LOGGER.info("Generando reporte de cálculo");
            CalculoDTO reporte = admCalculoMateriales.generarReporteCalculo(elemento);
            LOGGER.info("Reporte generado exitosamente");
            return reporte;
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.SEVERE, "Error al generar reporte", ex);
            throw new PresentacionException("Error al generar reporte: " + ex.getMessage(), ex);
        }
    }

    /**
     * Valida que la dirección ingresada coincida con la obra en sesión.
     *
     * @param direccionIngresada Dirección ingresada por el usuario
     * @throws PresentacionException Si la dirección no coincide o hay error
     */
    public void validarDireccionObra(String direccionIngresada) throws PresentacionException {
        try {
            LOGGER.info("Validando dirección de obra: " + direccionIngresada);
            admCalculoMateriales.validarDireccionObra(direccionIngresada);
            LOGGER.info("Dirección validada exitosamente");
        } catch (AdmCalculoMaterialesException ex) {
            LOGGER.log(Level.WARNING, "Error al validar dirección", ex);
            throw new PresentacionException("Error al validar dirección: " + ex.getMessage(), ex);
        }
    }

    /**
     * Establece el elemento actual para realizar cálculos.
     *
     * @param elemento Elemento a establecer
     */
    public void setElementoActual(ElementoDTO elemento) {
        LOGGER.info("Estableciendo elemento actual: " + (elemento != null ? elemento.getTipo() : "null"));
        this.elementoActual = elemento;
    }

    /**
     * Obtiene el elemento actual para realizar cálculos.
     *
     * @return Elemento actual
     */
    public ElementoDTO getElementoActual() {
        LOGGER.info("Obteniendo elemento actual: " + (elementoActual != null ? elementoActual.getTipo() : "null"));
        return this.elementoActual;
    }

    /**
     * Limpia el elemento actual. Útil cuando se inicia un nuevo proceso de
     * cálculo.
     */
    public void limpiarElementoActual() {
        LOGGER.info("Limpiando elemento actual");
        this.elementoActual = null;
    }

    /**
     * Inicia una nueva sesión de cálculo limpiando el elemento actual. Este
     * método debe llamarse cuando el usuario inicia un nuevo cálculo para
     * asegurar que no hay datos residuales de cálculos anteriores.
     */
    public void iniciarNuevaSesionCalculo() {
        LOGGER.info("Iniciando nueva sesión de cálculo");
        limpiarElementoActual();
    }

}
