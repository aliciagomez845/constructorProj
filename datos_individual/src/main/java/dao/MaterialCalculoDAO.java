/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import conexion.ConexionMongo;
import dominio_entidades.MaterialCalculo;
import dominio_enums.TipoMaterialDatos;
import dominio_enums.UnidadMaterialDatos;
import excepciones.PersistenciaException;
import interfaces.IMaterialCalculo;
import java.util.List;
import org.bson.Document;

/**
 * Implementación de la interfaz IMaterialCalculoDAO para acceder y manipular los
 * elementos en una base de datos MongoDB.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class MaterialCalculoDAO implements IMaterialCalculo {
    
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene todos los materiales registrados en la base de datos.
     *
     * @return lista de todos los materiales
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    @Override
    public List<MaterialCalculo> buscarTodos() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
