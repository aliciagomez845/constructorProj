package bo;

import dao.ObraDAO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IObraDAO;
import mappers.ObraMapper;

/**
 * Clase ObraBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * las obras o proyectos de construcción en el sistema. Esta clase implementa el
 * patrón Singleton para garantizar una única instancia en toda la aplicación.
 *
 * @author Alejandra Garcia 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public class ObraBO {

    /**
     * Instancia única de la clase (patrón Singleton). Garantiza que solo exista
     * una instancia en toda la aplicación.
     */
    public static ObraBO instance;

    /**
     * DAO para obras. Gestiona el acceso a los datos de obras en la
     * persistencia.
     */
    private IObraDAO obraDAO;

    /**
     * Constructor privado (patrón Singleton). Previene la creación de múltiples
     * instancias desde fuera de la clase.
     */
    private ObraBO() {
        this.obraDAO = new ObraDAO();
    }

    /**
     * Método para obtener la instancia única de ObraBO (patrón Singleton). Si
     * no existe una instancia, la crea; de lo contrario, devuelve la existente.
     *
     * @return La instancia única de ObraBO
     */
    public static ObraBO getInstance() {
        if (instance == null) {
            instance = new ObraBO();
        }
        return instance;
    }

//    /**
//     * Verifica si una obra existe en el sistema según su número identificador.
//     *
//     * En la implementación actual, siempre retorna true para permitir pruebas,
//     * pero en una versión completa debería consultar la base de datos.
//     *
//     * @param numero Número identificador de la obra a verificar
//     * @return true si la obra existe, false en caso contrario
//     * @throws BOException Si ocurre un error durante la verificación
//     */
//    public boolean validarObraExiste(String numero) throws BOException {
//        try {
//            return obraDAO.obraExiste(numero);
//        } catch (DAOException e) {
//            throw new BOException("Error al validar si la obra existe.", e);
//        }
//    }
//
//    /**
//     * Obtiene el identificador interno (ID) de una obra a partir de su número
//     * externo.
//     *
//     * En la implementación actual, simplemente retorna el mismo número como ID,
//     * pero en una versión completa debería realizar la consulta a la base de
//     * datos.
//     *
//     * @param numero Número externo o de referencia de la obra
//     * @return El ID interno de la obra en el sistema
//     * @throws BOException Si ocurre un error durante la consulta
//     */
//    public String obtenerIdPorNumero(String numero) throws BOException, Exception {
//        try {
//            return obraDAO.obtenerIdPorNumero(numero);
//        } catch (DAOException e) {
//            throw new BOException("Error al obtener el ID de la obra.", e);
//        }
//    }
//
//    /**
//     * Obtiene la dirección física de una obra a partir de su ID.
//     *
//     * En la implementación actual, retorna una dirección fija para pruebas,
//     * pero en una versión completa debería consultar la base de datos.
//     *
//     * @param id Identificador interno de la obra
//     * @return La dirección física de la obra
//     * @throws BOException Si ocurre un error durante la consulta
//     */
//    public String obtenerDireccionObra(String id) throws BOException, Exception {
//        try {
//            return obraDAO.obtenerDireccionObra(id);
//        } catch (DAOException e) {
//            throw new BOException("Error al obtener la dirección de la obra.", e);
//        }
//    }

    public negocio_dto.ObraDTO obtenerObra(String id) throws NegocioException {
        try {
            return ObraMapper.toDTO(obraDAO.buscarPorId(id));
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener la obra.", e);
        }
    }
}
