/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admCalculoMateriales;

import admObraSeleccionada.FAdmObraSeleccionada;
import admObraSeleccionada.IAdmObraSeleccionada;
import bo.CalculoBO;
import bo.ElementoBO;
import bo.MaterialCalculoBO;
import excepciones.AdmCalculoMaterialesException;
import excepciones.AdmObraSeleccionadaException;
import excepciones.NegocioException;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import negocio_dto.CalculoDTO;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_dto.ObraDTO;
import negocio_enums.TipoElementoNegocio;
import negocio_enums.TipoMaterialNegocio;

/**
 * Controlador del subsistema admCalculoMateriales.
 *
 * Esta clase gestiona la lógica de control relacionada con los cálculos de
 * materiales para elementos constructivos. Sirve como intermediario entre la
 * capa de presentación y las clases de negocio (BO), específicamente para
 * calcular materiales, guardar resultados y generar reportes.
 *
 * @author Alejandra García Preciado - 252444
 */
public class ControlAdmCalculoMateriales {

    /**
     * Interfaz para acceder al subsistema de obra seleccionada.
     */
    private final IAdmObraSeleccionada admObraSeleccionada;

    /**
     * Objeto de negocio encargado de la lógica relacionada con los cálculos.
     */
    private final CalculoBO calculoBO;

    /**
     * Objeto de negocio encargado de la lógica relacionada con los elementos.
     */
    private final ElementoBO elementoBO;

    /**
     * Objeto de negocio encargado de la lógica relacionada con los materiales.
     */
    private final MaterialCalculoBO materialCalculoBO;

    /**
     * Constructor que inicializa las instancias de los BOs e interfaces
     * requeridas.
     */
    public ControlAdmCalculoMateriales() {
        this.admObraSeleccionada = new FAdmObraSeleccionada();

        this.calculoBO = CalculoBO.getInstance();
        this.elementoBO = ElementoBO.getInstance();
        this.materialCalculoBO = MaterialCalculoBO.getInstance();
    }
    
    /**
     * Valida que la dirección ingresada coincida con la obra en sesión.
     *
     * @param direccionIngresada Dirección ingresada por el usuario
     * @throws AdmCalculoMaterialesException Si la dirección no coincide o hay
     * error
     */
    public void validarDireccionObra(String direccionIngresada) throws AdmCalculoMaterialesException {
        try {
            // Obtener la obra actual en sesión
            ObraDTO obraActual = obtenerObraActual();
            if (obraActual == null) {
                throw new AdmCalculoMaterialesException("No hay una obra seleccionada actualmente");
            }

            // Validar que la dirección coincida (ignorando mayúsculas/minúsculas y espacios)
            String direccionObra = obraActual.getDireccion().trim().toLowerCase();
            String direccionInput = direccionIngresada.trim().toLowerCase();

            if (!direccionObra.equals(direccionInput)) {
                throw new AdmCalculoMaterialesException(
                        "La dirección ingresada '" + direccionIngresada
                        + "' no coincide con la dirección de la obra en sesión '" + obraActual.getDireccion() + "'"
                );
            }
        } catch (AdmCalculoMaterialesException ex) {
            throw new AdmCalculoMaterialesException("Error al validar dirección de obra: " + ex.getMessage(), ex);
        }
    }

    /**
     * Calcula los materiales necesarios para un elemento constructivo
     * específico en la obra actualmente seleccionada.
     *
     * @param elemento Elemento constructivo para el cual se calcularán los
     * materiales
     * @return Objeto CalculoDTO con los resultados del cálculo
     * @throws AdmCalculoMaterialesException Si ocurre un error durante el
     * cálculo
     */
    public CalculoDTO calcularMateriales(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        try {
            // Validar que haya una obra seleccionada
            ObraDTO obraActual = obtenerObraActual();
            if (obraActual == null) {
                throw new AdmCalculoMaterialesException("No hay una obra seleccionada actualmente");
            }

            // Validar dimensiones del elemento
            if (!validarDimensionesElemento(elemento)) {
                throw new AdmCalculoMaterialesException("Las dimensiones del elemento no son válidas");
            }

            // Crear un nuevo cálculo usando la lógica del BO
            return calculoBO.crearCalculo(obraActual, elemento);
        } catch (NegocioException ex) {
            throw new AdmCalculoMaterialesException("Error al calcular materiales: " + ex.getMessage(), ex);
        }
    }

    /**
     * Guarda un cálculo de materiales en la base de datos.
     *
     * @param calculo Cálculo a guardar
     * @return El cálculo guardado con su ID asignado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante el
     * guardado
     */
    public CalculoDTO guardarCalculo(CalculoDTO calculo) throws AdmCalculoMaterialesException {
        try {
            // Validar que el cálculo tenga una obra asociada
            if (calculo.getObra() == null) {
                // Asignar la obra actual de la sesión
                ObraDTO obraActual = obtenerObraActual();
                if (obraActual == null) {
                    throw new AdmCalculoMaterialesException("No hay una obra seleccionada para asociar al cálculo");
                }
                calculo.setObra(obraActual);
            }

            // Asignar la fecha actual si no tiene
            if (calculo.getFecha() == null) {
                calculo.setFecha(LocalDate.now());
            }

            // Guardar el cálculo usando la lógica del BO
            return calculoBO.guardarCalculo(calculo);
        } catch (NegocioException ex) {
            throw new AdmCalculoMaterialesException("Error al guardar el cálculo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Genera y descarga un reporte en formato PDF de un cálculo específico.
     *
     * @param calculo Identificador único del cálculo
     * @return Arreglo de bytes que representa el PDF generado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * generación del PDF
     */
    public byte[] generarPDFDirecto(CalculoDTO calculo) throws AdmCalculoMaterialesException {
        try {
            // Validar que el cálculo tenga la información necesaria
            if (calculo == null) {
                throw new AdmCalculoMaterialesException("El cálculo no puede ser nulo");
            }

            if (calculo.getObra() == null || calculo.getObra().getDireccion() == null) {
                throw new AdmCalculoMaterialesException("El cálculo debe tener una obra asociada con dirección");
            }

            if (calculo.getElemento() == null) {
                throw new AdmCalculoMaterialesException("El cálculo debe tener un elemento asociado");
            }

            if (calculo.getMaterialesCalculados() == null || calculo.getMaterialesCalculados().isEmpty()) {
                throw new AdmCalculoMaterialesException("El cálculo debe tener materiales calculados");
            }

            // Generar el PDF usando PDFGenerator
            return PDFGenerator.generarReportePDF(calculo);

        } catch (Exception ex) {
            throw new AdmCalculoMaterialesException("Error al generar PDF directo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene los materiales disponibles de un tipo específico.
     *
     * @param tipo Tipo de material a buscar
     * @return Lista de materiales del tipo especificado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * búsqueda
     */
    public List<MaterialCalculoDTO> obtenerMateriales(TipoMaterialNegocio tipo) throws AdmCalculoMaterialesException {
        try {
            return materialCalculoBO.buscarPorTipo(tipo);
        } catch (NegocioException ex) {
            throw new AdmCalculoMaterialesException("Error al obtener materiales por tipo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene un elemento base de un tipo específico.
     *
     * @param tipo Tipo de elemento a buscar
     * @return Elemento del tipo especificado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * búsqueda
     */
    public ElementoDTO obtenerElemento(TipoElementoNegocio tipo) throws AdmCalculoMaterialesException {
        try {
            List<ElementoDTO> elementos = elementoBO.buscarPorTipo(tipo);
            if (elementos != null && !elementos.isEmpty()) {
                return elementos.get(0);
            } else {
                // Si no hay elementos guardados, crear uno nuevo con valores por defecto
                return crearElementoPorDefecto(tipo);
            }
        } catch (NegocioException ex) {
            throw new AdmCalculoMaterialesException("Error al obtener elemento por tipo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene el historial de cálculos para la obra actualmente en sesión.
     *
     * @return Lista de cálculos realizados para la obra en sesión
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * consulta
     */
    public List<CalculoDTO> obtenerHistorialCalculos() throws AdmCalculoMaterialesException {
        try {
            // Obtener el ID de la obra en sesión
            String idObra = admObraSeleccionada.obtenerSesion();
            if (idObra == null || idObra.isEmpty()) {
                throw new AdmCalculoMaterialesException("No hay una obra seleccionada actualmente");
            }

            // Obtener el historial de cálculos para la obra
            return calculoBO.obtenerHistorial(idObra);
        } catch (NegocioException | AdmObraSeleccionadaException ex) {
            throw new AdmCalculoMaterialesException("Error al obtener historial de cálculos: " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtiene la información de la obra actualmente en sesión.
     *
     * @return Información de la obra en sesión
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * consulta
     */
    public ObraDTO obtenerObraActual() throws AdmCalculoMaterialesException {
        try {
            return admObraSeleccionada.obtenerObra();
        } catch (AdmObraSeleccionadaException ex) {
            throw new AdmCalculoMaterialesException("Error al obtener la obra actual: " + ex.getMessage(), ex);
        }
    }

    /**
     * Valida si las dimensiones de un elemento son correctas para su tipo.
     *
     * @param elemento Elemento a validar
     * @return true si las dimensiones son válidas, false en caso contrario
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * validación
     */
    public boolean validarDimensionesElemento(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        if (elemento == null) {
            throw new AdmCalculoMaterialesException("El elemento no puede ser nulo");
        }

        return elementoBO.validarDimensiones(elemento);
    }

    /**
     * Método auxiliar para generar un PDF a partir de un cálculo.
     *
     * @param calculo Cálculo del que se generará el PDF
     * @return Arreglo de bytes que representa el PDF generado
     */
    private byte[] generarPDF(CalculoDTO calculo) throws Exception {
        try {
            // Validar que el cálculo tenga la información necesaria
            if (calculo == null) {
                throw new Exception("El cálculo no puede ser nulo");
            }

            if (calculo.getObra() == null || calculo.getObra().getDireccion() == null) {
                throw new Exception("El cálculo debe tener una obra asociada con dirección");
            }

            if (calculo.getElemento() == null) {
                throw new Exception("El cálculo debe tener un elemento asociado");
            }

            if (calculo.getMaterialesCalculados() == null || calculo.getMaterialesCalculados().isEmpty()) {
                throw new Exception("El cálculo debe tener materiales calculados");
            }

            // Generar el PDF usando la clase PDFGenerator
            return PDFGenerator.generarReportePDF(calculo);

        } catch (Exception ex) {
            // Log del error para debugging
            System.err.println("Error al generar PDF: " + ex.getMessage());
            ex.printStackTrace();
            throw new Exception("Error al generar el reporte PDF: " + ex.getMessage());
        }
    }

    /**
     * Método auxiliar para crear un elemento con valores por defecto según su
     * tipo.
     *
     * @param tipo Tipo de elemento a crear
     * @return Elemento creado con valores por defecto
     */
    private ElementoDTO crearElementoPorDefecto(TipoElementoNegocio tipo) {
        ElementoDTO elemento = new ElementoDTO();
        elemento.setTipo(tipo);

        // Establecer dimensiones por defecto según el tipo
        switch (tipo) {
            case COLUMNA_CUADRADA:
                elemento.setAlto(3.0);
                elemento.setAncho(0.3);
                elemento.setProfundidad(0.0);
                elemento.setLargo(0.0);
                elemento.setEspesor(0.0);
                break;

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                elemento.setLargo(5.0);
                elemento.setAncho(4.0);
                elemento.setEspesor(0.1);
                elemento.setAlto(0.0);
                elemento.setProfundidad(0.0);
                break;

            case VIGA:
                elemento.setLargo(4.0);
                elemento.setAncho(0.25);
                elemento.setAlto(0.4);
                elemento.setEspesor(0.0);
                elemento.setProfundidad(0.0);
                break;

            case MURO_LADRILLO:
                elemento.setLargo(3.0);
                elemento.setAlto(2.5);
                elemento.setEspesor(0.15);
                elemento.setAncho(0.0);
                elemento.setProfundidad(0.0);
                break;

            case NIVELACION_MUROS_VERTICAL:
                elemento.setLargo(3.0);
                elemento.setAlto(2.5);
                elemento.setEspesor(0.02);
                elemento.setAncho(0.0);
                elemento.setProfundidad(0.0);
                break;

            case NIVELACION_PISOS_HORIZONTAL:
                elemento.setLargo(5.0);
                elemento.setAncho(4.0);
                elemento.setEspesor(0.03);
                elemento.setAlto(0.0);
                elemento.setProfundidad(0.0);
                break;

            default:
                // Valores genéricos para otros tipos
                elemento.setLargo(1.0);
                elemento.setAncho(1.0);
                elemento.setAlto(1.0);
                elemento.setEspesor(0.1);
                elemento.setProfundidad(0.0);
        }

        return elemento;
    }

    /**
     * Calcula el volumen de un elemento constructivo basado en su tipo y
     * dimensiones.
     *
     * @param elemento Elemento para el que se calculará el volumen
     * @return Volumen calculado en metros cúbicos
     * @throws AdmCalculoMaterialesException Si ocurre un error durante el
     * cálculo
     */
    public Double calcularVolumen(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        if (elemento == null) {
            throw new AdmCalculoMaterialesException("El elemento no puede ser nulo");
        }

        Double volumen = 0.0;

        switch (elemento.getTipo()) {
            case COLUMNA_CUADRADA:
                // Para columnas cuadradas se usa alto * ancho * ancho (área de la sección * altura)
                volumen = elemento.getAlto() * elemento.getAncho() * elemento.getAncho();
                break;

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                // Para losas: largo * ancho * espesor
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            case VIGA:
                // Para vigas: largo * ancho * espesor 
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getAlto();
                break;

            case MURO_LADRILLO:
                // Para muros: largo * alto * espesor
                volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                break;

            case NIVELACION_MUROS_VERTICAL:
                // Para nivelación vertical: largo * alto * espesor
                volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                break;

            case NIVELACION_PISOS_HORIZONTAL:
                // Para nivelación horizontal: largo * ancho * espesor
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            default:
                throw new AdmCalculoMaterialesException("Tipo de elemento no reconocido: " + elemento.getTipo());
        }

        return volumen;
    }

    /**
     * Genera un reporte de cálculo con materiales calculados para un elemento.
     *
     * @param elemento Elemento para el que se generará el reporte
     * @return Cálculo con la información del reporte
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * generación
     */
    public CalculoDTO generarReporteCalculo(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        try {
            // Validar dimensiones del elemento
            if (!validarDimensionesElemento(elemento)) {
                throw new AdmCalculoMaterialesException("Las dimensiones del elemento no son válidas");
            }

            // Obtener la obra actual
            ObraDTO obraActual = obtenerObraActual();
            if (obraActual == null) {
                throw new AdmCalculoMaterialesException("No hay una obra seleccionada actualmente");
            }

            // Calcular el volumen
            Double volumen = calcularVolumen(elemento);

            // Calcular los materiales necesarios
            List<MaterialCalculoDTO> materiales = materialCalculoBO.calcularMateriales(elemento);

            // Crear el objeto cálculo (sin guardarlo)
            CalculoDTO calculo = new CalculoDTO(
                    obraActual,
                    LocalDate.now(),
                    volumen,
                    elemento,
                    materiales
            );

            return calculo;
        } catch (NegocioException ex) {
            throw new AdmCalculoMaterialesException("Error al generar reporte de cálculo: " + ex.getMessage(), ex);
        }
    }

}
