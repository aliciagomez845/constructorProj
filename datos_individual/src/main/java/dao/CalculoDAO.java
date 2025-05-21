/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import conexion.ConexionMongo;
import dominio_entidades.Calculo;
import excepciones.PersistenciaException;
import interfaces.ICalculoDAO;
import java.util.List;

/**
 * Clase CalculoDAO.
 *
 * Esta clase implementa la interfaz ICalculoDAO y proporciona métodos para
 * interactuar con la colección "calculos" de la base de datos MongoDB.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class CalculoDAO implements ICalculoDAO {
    
    /**
     * Referencia a la colección de obras en la base de datos MongoDB.
     */
    private final MongoCollection<Calculo> coleccion;

    /**
     * Constructor que inicializa la colección de obras a partir de la conexión
     * Mongo.
     */
    public CalculoDAO() {
        this.coleccion = ConexionMongo.getDatabase().getCollection("calculos", Calculo.class);
    }

    /**
     * Guarda un nuevo cálculo en la base de datos.
     *
     * @param calculo el cálculo a guardar
     * @return el cálculo guardado con su ID asignado
     * @throws PersistenciaException si ocurre un error durante el guardado
     */
    @Override
    public Calculo guardarCalculo(Calculo calculo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Busca un cálculo por su identificador único.
     *
     * @param id identificador único del cálculo a buscar
     * @return el cálculo encontrado o null si no existe
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public Calculo buscarPorId(String id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Busca cálculos por la obra a la que pertenecen.
     *
     * @param idObra identificador único de la obra
     * @return lista de cálculos asociados a la obra especificada
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public List<Calculo> buscarPorObra(String idObra) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
