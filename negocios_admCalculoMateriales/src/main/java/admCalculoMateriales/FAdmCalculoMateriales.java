/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admCalculoMateriales;

import excepciones.AdmCalculoMaterialesException;
import java.util.List;
import negocio_dto.CalculoDTO;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_dto.ObraDTO;
import negocio_enums.TipoElementoNegocio;
import negocio_enums.TipoMaterialNegocio;

/**
 * Facade FAdmCalculoMateriales.
 *
 * Clase que implementa la interfaz IAdmCalculoMateriales y actúa como una
 * fachada del subsistema encargado de la gestión de cálculo de materiales. Su
 * propósito es simplificar el acceso a la lógica de negocio encapsulada en la
 * clase ControlAdmCalculoMateriales, ocultando detalles de implementación y
 * brindando una interfaz clara y uniforme al resto del sistema.
 *
 * @author Alejandra García Preciado - 252444
 */
public class FAdmCalculoMateriales implements IAdmCalculoMateriales {

    /**
     * Instancia del controlador que contiene la lógica de negocio para el
     * cálculo de materiales.
     */
    private final ControlAdmCalculoMateriales controlAdmCalculoMateriales;

    /**
     * Constructor por defecto de la clase.
     *
     * Inicializa la instancia del controlador que implementa las operaciones
     * necesarias para cumplir con el contrato definido por la interfaz.
     */
    public FAdmCalculoMateriales() {
        this.controlAdmCalculoMateriales = new ControlAdmCalculoMateriales();
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
    @Override
    public Double calcularVolumen(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.calcularVolumen(elemento);
    }

    /**
     * Genera un reporte de cálculo con materiales calculados para un elemento.
     *
     * @param elemento Elemento para el que se generará el reporte
     * @return Cálculo con la información del reporte
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * generación
     */
    @Override
    public CalculoDTO generarReporteCalculo(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.generarReporteCalculo(elemento);
    }

    /**
     * Calcula los materiales necesarios para un elemento constructivo
     * específico.
     *
     * @param elemento Elemento constructivo para el cual se calcularán los
     * materiales
     * @return Objeto CalculoDTO con los resultados del cálculo
     * @throws AdmCalculoMaterialesException Si ocurre un error durante el
     * cálculo
     */
    @Override
    public CalculoDTO calcularMateriales(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.calcularMateriales(elemento);
    }

    /**
     * Guarda un cálculo de materiales en la base de datos.
     *
     * @param calculo Cálculo a guardar
     * @return El cálculo guardado con su ID asignado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante el
     * guardado
     */
    @Override
    public CalculoDTO guardarCalculo(CalculoDTO calculo) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.guardarCalculo(calculo);
    }

    /**
     * Genera y descarga un reporte en formato PDF de un cálculo específico.
     *
     * @param idCalculo Identificador único del cálculo
     * @return Arreglo de bytes que representa el PDF generado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * generación del PDF
     */
    @Override
    public byte[] descargarPDF(String idCalculo) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.descargarPDF(idCalculo);
    }

    /**
     * Obtiene los materiales disponibles de un tipo específico.
     *
     * @param tipo Tipo de material a buscar
     * @return Lista de materiales del tipo especificado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * búsqueda
     */
    @Override
    public List<MaterialCalculoDTO> obtenerMateriales(TipoMaterialNegocio tipo) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.obtenerMateriales(tipo);
    }

    /**
     * Obtiene un elemento base de un tipo específico.
     *
     * @param tipo Tipo de elemento a buscar
     * @return Elemento del tipo especificado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * búsqueda
     */
    @Override
    public ElementoDTO obtenerElemento(TipoElementoNegocio tipo) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.obtenerElemento(tipo);
    }

    /**
     * Obtiene el historial de cálculos para la obra actualmente en sesión.
     *
     * @return Lista de cálculos realizados para la obra en sesión
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * consulta
     */
    @Override
    public List<CalculoDTO> obtenerHistorialCalculos() throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.obtenerHistorialCalculos();
    }

    /**
     * Obtiene la información de la obra actualmente en sesión.
     *
     * @return Información de la obra en sesión
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * consulta
     */
    @Override
    public ObraDTO obtenerObraActual() throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.obtenerObraActual();
    }

    /**
     * Valida si las dimensiones de un elemento son correctas para su tipo.
     *
     * @param elemento Elemento a validar
     * @return true si las dimensiones son válidas, false en caso contrario
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * validación
     */
    @Override
    public boolean validarDimensionesElemento(ElementoDTO elemento) throws AdmCalculoMaterialesException {
        return controlAdmCalculoMateriales.validarDimensionesElemento(elemento);
    }

}
