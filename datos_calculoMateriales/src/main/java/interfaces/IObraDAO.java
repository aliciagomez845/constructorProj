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
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public interface IObraDAO {

    /**
     * Verifica si una obra con el número especificado existe en la base de
     * datos.
     *
     * @param numero Número identificador de la obra.
     * @return true si la obra existe, false en caso contrario.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
     */
    public boolean obraExiste(String numero) throws PersistenciaException;

    /**
     * Obtiene el ID de una obra dado su número.
     *
     * @param numero Número identificador de la obra.
     * @return ID de la obra como cadena.
     * @throws DAOException Si ocurre un error durante la consulta.
     */
    public String obtenerIdPorNumero(String numero) throws PersistenciaException;

    /**
     * Obtiene la dirección de una obra dado su ID.
     *
     * @param id ID de la obra como cadena.
     * @return Dirección de la obra.
     * @throws DAOException Si ocurre un error durante la consulta o el ID no es
     * válido.
     */
    public String obtenerDireccionObra(String id) throws PersistenciaException;

    /**
     * Obtiene una obra completa dado su ID.
     *
     * @param id ID de la obra como cadena.
     * @return Objeto Obra correspondiente al ID.
     * @throws DAOException Si ocurre un error durante la consulta.
     */
    public Obra obtenerObra(String id) throws PersistenciaException;
}
