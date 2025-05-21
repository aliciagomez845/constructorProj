/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominio_entidades.Calculo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz ICalculoDAO
 *
 * Esta interfaz define las operaciones de acceso a datos relacionadas con la
 * entidad Calculo.
 * 
 * @author Alejandra García Preciado - 252444
 */
public interface ICalculoDAO {
    
    /**
     * Guarda un nuevo cálculo en la base de datos.
     *
     * @param calculo el cálculo a guardar
     * @return el cálculo guardado con su ID asignado
     * @throws PersistenciaException si ocurre un error durante el guardado
     */
    public Calculo guardarCalculo(Calculo calculo) throws PersistenciaException;
    
    /**
     * Busca un cálculo por su identificador único.
     *
     * @param id identificador único del cálculo a buscar
     * @return el cálculo encontrado o null si no existe
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public Calculo buscarPorId(String id) throws PersistenciaException;
    
    /**
     * Busca cálculos por la obra a la que pertenecen.
     *
     * @param idObra identificador único de la obra
     * @return lista de cálculos asociados a la obra especificada
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    public List<Calculo> buscarPorObra(String idObra) throws PersistenciaException;
    
}
