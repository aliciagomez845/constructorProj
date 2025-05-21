/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import conexion.ConexionMongo;
import dominio_entidades.Obra;
import excepciones.PersistenciaException;
import interfaces.IObraDAO;

/**
 * Clase ObraDAO.
 *
 * Esta clase implementa la interfaz IObraDAO y proporciona métodos para
 * interactuar con la colección "obras" de la base de datos MongoDB.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class ObraDAO implements IObraDAO {
    
    /**
     * Referencia a la colección de obras en la base de datos MongoDB.
     */
    private final MongoCollection<Obra> coleccion;

    /**
     * Constructor que inicializa la colección de obras a partir de la conexión
     * Mongo.
     */
    public ObraDAO() {
        this.coleccion = ConexionMongo.getDatabase().getCollection("obras", Obra.class);
    }

    /**
     * Busca una obra por su identificador único.
     *
     * @param id identificador único de la obra a buscar
     * @return la obra encontrada o null si no existe
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public Obra buscarPorId(String id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Busca una obra por su dirección.
     *
     * @param direccion dirección de la obra a buscar
     * @return la obra encontrada o null si no existe
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public Obra buscarPorDireccion(String direccion) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
