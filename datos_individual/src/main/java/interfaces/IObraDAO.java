/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio_entidades.Obra;
import excepciones.PersistenciaException;

/**
 * Interfaz IObraDAO
 *
 * Esta interfaz define las operaciones de acceso a datos relacionadas con la
 * entidad Obra.
 * 
 * @author Alejandra García Preciado - 252444
 */
public interface IObraDAO {
    
    /**
     * Busca una obra por su identificador único.
     *
     * @param id identificador único de la obra a buscar
     * @return la obra encontrada o null si no existe
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public Obra buscarPorId(String id) throws PersistenciaException;
    
    /**
     * Busca una obra por su dirección.
     *
     * @param direccion dirección de la obra a buscar
     * @return la obra encontrada o null si no existe
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public Obra buscarPorDireccion(String direccion) throws PersistenciaException;
    
}
