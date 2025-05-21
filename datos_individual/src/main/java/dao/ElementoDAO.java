/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import conexion.ConexionMongo;
import dominio_entidades.Elemento;
import dominio_enums.TipoElementoDatos;
import excepciones.PersistenciaException;
import interfaces.IElementoDAO;
import java.util.List;
import org.bson.Document;

/**
 * Implementación de la interfaz IElementoDAO para acceder y manipular los elementos
 * en una base de datos MongoDB.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class ElementoDAO implements IElementoDAO {
    
    /**
     * Referencia a la colección de elementos en la base de datos MongoDB. Los
     * elementos están incrustados dentro de cada documento de calculo.
     */
    private final MongoCollection<Document> coleccionCalculo;

    /**
     * Constructor que inicializa la colección de calculos a partir de la conexión
     * Mongo.
     */
    public ElementoDAO() {
        this.coleccionCalculo = ConexionMongo.getDatabase().getCollection("calculos");
    }

    /**
     * Busca elementos por su tipo.
     *
     * @param tipo tipo de elemento a buscar
     * @return lista de elementos del tipo especificado
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public List<Elemento> buscarPorTipo(TipoElementoDatos tipo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene todos los elementos registrados en la base de datos.
     *
     * @return lista de todos los elementos
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    @Override
    public List<Elemento> buscarTodos() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
