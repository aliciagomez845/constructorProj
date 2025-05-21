/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.ObraDAO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IObraDAO;
import mappers.ObraMapper;
import negocio_dto.ObraDTO;

/**
 * Clase ObraBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * las obras o proyectos de construcción en el sistema. Esta clase implementa el
 * patrón Singleton para garantizar una única instancia en toda la aplicación.
 *
 * @author Alejandra García Preciado - 252444
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

    /**
     * Busca una obra por su identificador único.
     *
     * @param id Identificador único de la obra
     * @return Obra encontrada o null si no existe
     * @throws NegocioException Si ocurre un error durante la búsqueda
     */
    public ObraDTO buscarPorId(String id) throws NegocioException {
        try {
            return ObraMapper.toDTO(obraDAO.buscarPorId(id));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar obra por ID: " + id, ex);
        }
    }

    /**
     * Busca una obra por su dirección.
     *
     * @param direccion Dirección de la obra
     * @return Obra encontrada o null si no existe
     * @throws NegocioException Si ocurre un error durante la búsqueda
     */
    public ObraDTO buscarPorDireccion(String direccion) throws NegocioException {
        try {
            return ObraMapper.toDTO(obraDAO.buscarPorDireccion(direccion));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar obra por dirección: " + direccion, ex);
        }
    }

    /**
     * Valida que una obra sea correcta.
     *
     * @param obraDTO Obra a validar
     * @return true si la obra es válida, false en caso contrario
     */
    public boolean validarObra(ObraDTO obraDTO) {
        if (obraDTO == null) {
            return false;
        }

        // Validar que la dirección no sea nula o vacía
        if (obraDTO.getDireccion() == null || obraDTO.getDireccion().trim().isEmpty()) {
            return false;
        }

        return true;
    }

}
