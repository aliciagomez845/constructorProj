/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.ConexionMongo;
import dominio_entidades.MaterialCalculo;
import dominio_enums.TipoMaterialDatos;
import dominio_enums.UnidadMaterialDatos;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import interfaces.IMaterialCalculoDAO;

/**
 * Implementación de la interfaz IMaterialCalculoDAO para acceder y manipular los
 * elementos en una base de datos MongoDB.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class MaterialCalculoDAO implements IMaterialCalculoDAO {
    
    /**
     * Referencia a la colección de materiales calculados en la base de datos MongoDB. Los
     * materiales calculados están incrustados dentro de cada documento de calculo.
     */
    private final MongoCollection<Document> coleccionCalculo;

    /**
     * Constructor que inicializa la colección de calculos a partir de la conexión
     * Mongo.
     */
    public MaterialCalculoDAO() {
        this.coleccionCalculo = ConexionMongo.getDatabase().getCollection("calculos");
    }

    /**
     * Busca materiales por su tipo.
     *
     * @param tipo tipo de material a buscar
     * @return lista de materiales del tipo especificado
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public List<MaterialCalculo> buscarPorTipo(TipoMaterialDatos tipo) throws PersistenciaException {
        try {
            List<MaterialCalculo> materiales = new ArrayList<>();

            // Buscar en la colección de cálculos, en el array de materiales calculados
            coleccionCalculo.find(Filters.eq("materialesCalculados.tipo", tipo.toString()))
                    .forEach(doc -> {
                        List<Document> materialesDoc = (List<Document>) doc.get("materialesCalculados", List.class);
                        if (materialesDoc != null) {
                            for (Document materialDoc : materialesDoc) {
                                if (tipo.toString().equals(materialDoc.getString("tipo"))) {
                                    MaterialCalculo material = documentToMaterialCalculo(materialDoc);
                                    materiales.add(material);
                                }
                            }
                        }
                    });

            return materiales;
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar materiales por tipo: " + tipo, ex);
        }
    }

    /**
     * Busca materiales por su unidad base.
     *
     * @param unidad unidad base de material a buscar
     * @return lista de materiales con la unidad base especificada
     * @throws PersistenciaException si ocurre un error durante la búsqueda
     */
    @Override
    public List<MaterialCalculo> buscarPorUnidadBase(UnidadMaterialDatos unidad) throws PersistenciaException {
        try {
            List<MaterialCalculo> materiales = new ArrayList<>();

            // Buscar en la colección de cálculos, en el array de materiales calculados
            coleccionCalculo.find(Filters.eq("materialesCalculados.unidad", unidad.toString()))
                    .forEach(doc -> {
                        List<Document> materialesDoc = (List<Document>) doc.get("materialesCalculados", List.class);
                        if (materialesDoc != null) {
                            for (Document materialDoc : materialesDoc) {
                                if (unidad.toString().equals(materialDoc.getString("unidad"))) {
                                    MaterialCalculo material = documentToMaterialCalculo(materialDoc);
                                    materiales.add(material);
                                }
                            }
                        }
                    });

            return materiales;
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar materiales por unidad base: " + unidad, ex);
        }
    }

    /**
     * Obtiene todos los materiales registrados en la base de datos.
     *
     * @return lista de todos los materiales
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    @Override
    public List<MaterialCalculo> buscarTodos() throws PersistenciaException {
        try {
            List<MaterialCalculo> materiales = new ArrayList<>();

            coleccionCalculo.find()
                    .forEach(doc -> {
                        List<Document> materialesDoc = (List<Document>) doc.get("materialesCalculados", List.class);
                        if (materialesDoc != null) {
                            for (Document materialDoc : materialesDoc) {
                                MaterialCalculo material = documentToMaterialCalculo(materialDoc);
                                materiales.add(material);
                            }
                        }
                    });

            return materiales;
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar todos los materiales", ex);
        }
    }
    
    /**
     * Método auxiliar para convertir un Document a un objeto MaterialCalculo.
     */
    private MaterialCalculo documentToMaterialCalculo(Document doc) {
        MaterialCalculo material = new MaterialCalculo();
        material.setTipo(TipoMaterialDatos.valueOf(doc.getString("tipo")));
        material.setUnidad(UnidadMaterialDatos.valueOf(doc.getString("unidad")));
        material.setCantidad(doc.getDouble("cantidad"));
        return material;
    }
    
}
