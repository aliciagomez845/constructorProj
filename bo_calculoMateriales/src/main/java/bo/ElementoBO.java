/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.ElementoDAO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IElementoDAO;
import java.util.List;
import mappers.ElementoMapper;
import negocio_dto.ElementoDTO;
import negocio_enums.TipoElementoNegocio;

/**
 * Clase ElementoBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * los elementos en el sistema. Esta clase implementa el patrón Singleton para
 * garantizar una única instancia en toda la aplicación.
 *
 * @author Alejandra García Preciado - 252444
 */
public class ElementoBO {

    /**
     * Instancia única de la clase (patrón Singleton). Garantiza que solo exista
     * una instancia en toda la aplicación.
     */
    public static ElementoBO instance;

    /**
     * DAO para elementos. Gestiona el acceso a los datos de elementos en la
     * persistencia.
     */
    private IElementoDAO elementoDAO;

    /**
     * Constructor privado (patrón Singleton). Previene la creación de múltiples
     * instancias desde fuera de la clase.
     */
    private ElementoBO() {
        this.elementoDAO = new ElementoDAO();
    }

    /**
     * Método para obtener la instancia única de ElementoBO (patrón Singleton).
     * Si no existe una instancia, la crea; de lo contrario, devuelve la
     * existente.
     *
     * @return La instancia única de ElementoBO
     */
    public static ElementoBO getInstance() {
        if (instance == null) {
            instance = new ElementoBO();
        }
        return instance;
    }

    /**
     * Busca elementos por su tipo en la base de datos.
     *
     * @param tipo Tipo de elemento a buscar
     * @return Lista de elementos del tipo especificado
     * @throws NegocioException Si ocurre un error durante la búsqueda
     */
    public List<ElementoDTO> buscarPorTipo(TipoElementoNegocio tipo) throws NegocioException {
        try {
            return ElementoMapper.toDTOList(elementoDAO.buscarPorTipo(ElementoMapper.convertirTipoElemento(tipo)));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar elementos por tipo: " + tipo, ex);
        }
    }

    /**
     * Obtiene todos los elementos registrados en la base de datos.
     *
     * @return Lista de todos los elementos registrados
     * @throws NegocioException Si ocurre un error durante la consulta
     */
    public List<ElementoDTO> buscarTodos() throws NegocioException {
        try {
            return ElementoMapper.toDTOList(elementoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar todos los elementos", ex);
        }
    }

    /**
     * Valida que las dimensiones de un elemento sean correctas según su tipo.
     *
     * @param elemento Elemento a validar
     * @return true si las dimensiones son válidas, false en caso contrario
     */
    public boolean validarDimensiones(ElementoDTO elemento) {
        if (elemento == null) {
            System.out.println("Elemento es nulo");
            return false;
        }
        
        System.out.println("Tipo: " + elemento.getTipo());
        System.out.println("Alto: " + elemento.getAlto());
        System.out.println("Ancho: " + elemento.getAncho());
        System.out.println("Largo: " + elemento.getLargo());
        System.out.println("Espesor: " + elemento.getEspesor());
        System.out.println("Profundidad: " + elemento.getProfundidad());

        // Validar que todas las dimensiones sean positivas
        if (!validarDimensionesPositivas(elemento)) {
            return false;
        }

        // Validar dimensiones específicas según el tipo de elemento
        switch (elemento.getTipo()) {
            case COLUMNA_CUADRADA:
                return elemento.getAlto() != null && elemento.getAncho() != null
                        && elemento.getAlto() > 0 && elemento.getAncho() > 0;

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                return elemento.getLargo() != null && elemento.getAncho() != null
                        && elemento.getEspesor() != null && elemento.getLargo() > 0
                        && elemento.getAncho() > 0 && elemento.getEspesor() > 0;

            case VIGA:
                return elemento.getLargo() != null && elemento.getAncho() != null
                        && elemento.getEspesor() != null && elemento.getLargo() > 0
                        && elemento.getAncho() > 0 && elemento.getEspesor() > 0;

            case MURO_LADRILLO:
                return elemento.getLargo() != null && elemento.getAlto() != null
                        && elemento.getEspesor() != null && elemento.getLargo() > 0
                        && elemento.getAlto() > 0 && elemento.getEspesor() > 0;

            case NIVELACION_MUROS_VERTICAL:
                return elemento.getLargo() != null && elemento.getAlto() != null
                        && elemento.getEspesor() != null && elemento.getLargo() > 0
                        && elemento.getAlto() > 0 && elemento.getEspesor() > 0;

            case NIVELACION_PISOS_HORIZONTAL:
                return elemento.getLargo() != null && elemento.getAncho() != null
                        && elemento.getEspesor() != null && elemento.getLargo() > 0
                        && elemento.getAncho() > 0 && elemento.getEspesor() > 0;

            default:
                return false;
        }
    }

    /**
     * Valida que todas las dimensiones de un elemento sean positivas.
     *
     * @param elemento Elemento a validar
     * @return true si todas las dimensiones son positivas, false en caso
     * contrario
     */
    private boolean validarDimensionesPositivas(ElementoDTO elemento) {
        TipoElementoNegocio tipo = elemento.getTipo();

        if (tipo == null) {
            return false;
        }

        switch (tipo) {
            case COLUMNA_CUADRADA:
                return esPositivo(elemento.getAlto())
                        && esPositivo(elemento.getAncho());

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                return esPositivo(elemento.getAncho())
                        && esPositivo(elemento.getLargo())
                        && esPositivo(elemento.getEspesor());

            case VIGA:
                return esPositivo(elemento.getAncho())
                        && esPositivo(elemento.getLargo())
                        && esCeroOPositivo(elemento.getEspesor());

            case NIVELACION_MUROS_VERTICAL:
                return esPositivo(elemento.getAlto())
                        && esPositivo(elemento.getLargo())
                        && esCeroOPositivo(elemento.getEspesor());

            case NIVELACION_PISOS_HORIZONTAL:
                return esPositivo(elemento.getAncho())
                        && esPositivo(elemento.getLargo())
                        && esCeroOPositivo(elemento.getEspesor());

            case MURO_LADRILLO:
                return esPositivo(elemento.getAlto())
                        && esPositivo(elemento.getLargo())
                        && esPositivo(elemento.getEspesor());

            default:
                return false;
        }
    }

    private boolean esPositivo(Double valor) {
        return valor != null && valor > 0;
    }

    private boolean esCeroOPositivo(Double valor) {
        return valor != null && valor >= 0;
    }
    
}
