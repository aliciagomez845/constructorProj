/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
 * Interface IAdmCalculoMateriales.
 *
 * Interfaz que define el contrato para la administración del proceso de
 * realizar el cálculo de materiales dentro del sistema. En este caso, se
 * implementará mediante la Fachada del subsistema.
 *
 * @author Alejandra García Preciado - 252444
 */
public interface IAdmCalculoMateriales {

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
    CalculoDTO calcularMateriales(ElementoDTO elemento) throws AdmCalculoMaterialesException;

    /**
     * Guarda un cálculo de materiales en la base de datos.
     *
     * @param calculo Cálculo a guardar
     * @return El cálculo guardado con su ID asignado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante el
     * guardado
     */
    CalculoDTO guardarCalculo(CalculoDTO calculo) throws AdmCalculoMaterialesException;

    /**
     * Genera un PDF directamente a partir de un objeto CalculoDTO.Agregar este método a IAdmCalculoMateriales:
     *
     * @param calculo
     * @return 
     * @throws AdmCalculoMaterialesException
     */
    byte[] generarPDFDirecto(CalculoDTO calculo) throws AdmCalculoMaterialesException;

    /**
     * Obtiene los materiales disponibles de un tipo específico.
     *
     * @param tipo Tipo de material a buscar
     * @return Lista de materiales del tipo especificado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * búsqueda
     */
    List<MaterialCalculoDTO> obtenerMateriales(TipoMaterialNegocio tipo) throws AdmCalculoMaterialesException;

    /**
     * Obtiene un elemento base de un tipo específico.
     *
     * @param tipo Tipo de elemento a buscar
     * @return Elemento del tipo especificado
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * búsqueda
     */
    ElementoDTO obtenerElemento(TipoElementoNegocio tipo) throws AdmCalculoMaterialesException;

    /**
     * Obtiene el historial de cálculos para la obra actualmente en sesión.
     *
     * @return Lista de cálculos realizados para la obra en sesión
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * consulta
     */
    List<CalculoDTO> obtenerHistorialCalculos() throws AdmCalculoMaterialesException;

    /**
     * Obtiene la información de la obra actualmente en sesión.
     *
     * @return Información de la obra en sesión
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * consulta
     */
    ObraDTO obtenerObraActual() throws AdmCalculoMaterialesException;

    /**
     * Valida si las dimensiones de un elemento son correctas para su tipo.
     *
     * @param elemento Elemento a validar
     * @return true si las dimensiones son válidas, false en caso contrario
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * validación
     */
    boolean validarDimensionesElemento(ElementoDTO elemento) throws AdmCalculoMaterialesException;

    /**
     * Calcula el volumen de un elemento constructivo basado en su tipo y
     * dimensiones.
     *
     * @param elemento Elemento para el que se calculará el volumen
     * @return Volumen calculado en metros cúbicos
     * @throws AdmCalculoMaterialesException Si ocurre un error durante el
     * cálculo
     */
    Double calcularVolumen(ElementoDTO elemento) throws AdmCalculoMaterialesException;

    /**
     * Genera un reporte de cálculo con materiales calculados para un elemento.
     *
     * @param elemento Elemento para el que se generará el reporte
     * @return Cálculo con la información del reporte
     * @throws AdmCalculoMaterialesException Si ocurre un error durante la
     * generación
     */
    CalculoDTO generarReporteCalculo(ElementoDTO elemento) throws AdmCalculoMaterialesException;
    
    /**
     * Valida que la dirección ingresada coincida con la obra en sesión.
     *
     * @param direccionIngresada Dirección ingresada por el usuario
     * @throws AdmCalculoMaterialesException Si la dirección no coincide o hay
     * error
     */
    void validarDireccionObra(String direccionIngresada) throws AdmCalculoMaterialesException;

}
