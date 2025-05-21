/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio_entidades.Elemento;
import dominio_enums.TipoElementoDatos;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz IElementoDAO
 *
 * Esta interfaz define las operaciones de acceso a datos relacionadas con la
 * entidad Elemento.
 * 
 * @author Alejandra García Preciado - 252444
 */
public interface IElementoDAO {
    
    /**
     * Busca elementos por su tipo.
     *
     * @param tipo tipo de elemento a buscar
     * @return lista de elementos del tipo especificado
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public List<Elemento> buscarPorTipo(TipoElementoDatos tipo) throws PersistenciaException;
    
    /**
     * Obtiene todos los elementos registrados en la base de datos.
     *
     * @return lista de todos los elementos
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    public List<Elemento> buscarTodos() throws PersistenciaException;
    
}
