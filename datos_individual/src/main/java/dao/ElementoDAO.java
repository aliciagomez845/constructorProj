/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.ConexionMongo;
import dominio_entidades.Elemento;
import dominio_enums.TipoElementoDatos;
import excepciones.PersistenciaException;
import interfaces.IElementoDAO;
import java.util.ArrayList;
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
        try {
            // Los elementos están embebidos en los documentos de cálculos
            // Agregación para extraer solo los documentos que tienen elementos del tipo especificado
            List<Elemento> elementos = new ArrayList<>();

            coleccionCalculo.find(Filters.eq("elemento.tipo", tipo.toString()))
                    .forEach(doc -> {
                        Document elementoDoc = doc.get("elemento", Document.class);
                        if (elementoDoc != null) {
                            Elemento elemento = new Elemento();
                            elemento.setTipo(TipoElementoDatos.valueOf(elementoDoc.getString("tipo")));
                            elemento.setProfundidad(elementoDoc.getDouble("profundidad"));
                            elemento.setLargo(elementoDoc.getDouble("largo"));
                            elemento.setAlto(elementoDoc.getDouble("alto"));
                            elemento.setAncho(elementoDoc.getDouble("ancho"));
                            elemento.setEspesor(elementoDoc.getDouble("espesor"));
                            elementos.add(elemento);
                        }
                    });

            return elementos;
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar elementos por tipo: " + tipo, ex);
        }
    }

    /**
     * Obtiene todos los elementos registrados en la base de datos.
     *
     * @return lista de todos los elementos
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    @Override
    public List<Elemento> buscarTodos() throws PersistenciaException {
        try {
            List<Elemento> elementos = new ArrayList<>();

            coleccionCalculo.find()
                    .forEach(doc -> {
                        Document elementoDoc = doc.get("elemento", Document.class);
                        if (elementoDoc != null) {
                            Elemento elemento = new Elemento();
                            elemento.setTipo(TipoElementoDatos.valueOf(elementoDoc.getString("tipo")));
                            elemento.setProfundidad(elementoDoc.getDouble("profundidad"));
                            elemento.setLargo(elementoDoc.getDouble("largo"));
                            elemento.setAlto(elementoDoc.getDouble("alto"));
                            elemento.setAncho(elementoDoc.getDouble("ancho"));
                            elemento.setEspesor(elementoDoc.getDouble("espesor"));
                            elementos.add(elemento);
                        }
                    });

            return elementos;
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar todos los elementos", ex);
        }
    }
    
}
