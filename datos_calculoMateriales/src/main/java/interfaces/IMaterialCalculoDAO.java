/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio_entidades.MaterialCalculo;
import dominio_enums.TipoMaterialDatos;
import dominio_enums.UnidadMaterialDatos;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz IMaterialCalculoDAO
 *
 * Esta interfaz define las operaciones de acceso a datos relacionadas con la
 * entidad MaterialCalculo.
 * 
 * @author Alejandra García Preciado - 252444
 */
public interface IMaterialCalculoDAO {
    
    /**
     * Busca materiales por su tipo.
     *
     * @param tipo tipo de material a buscar
     * @return lista de materiales del tipo especificado
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public List<MaterialCalculo> buscarPorTipo(TipoMaterialDatos tipo) throws PersistenciaException;
    
    /**
     * Busca materiales por su unidad base.
     *
     * @param unidad unidad base de material a buscar
     * @return lista de materiales con la unidad base especificada
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public List<MaterialCalculo> buscarPorUnidadBase(UnidadMaterialDatos unidad) throws PersistenciaException;
    
    /**
     * Obtiene todos los materiales registrados en la base de datos.
     *
     * @return lista de todos los materiales
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    public List<MaterialCalculo> buscarTodos() throws PersistenciaException;
    
}
