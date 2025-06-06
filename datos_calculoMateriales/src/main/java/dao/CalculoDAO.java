/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import conexion.ConexionMongo;
import dominio_entidades.Calculo;
import excepciones.PersistenciaException;
import interfaces.ICalculoDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;

/**
 * Clase CalculoDAO.
 *
 * Esta clase implementa la interfaz ICalculoDAO y proporciona métodos para
 * interactuar con la colección "calculos" de la base de datos MongoDB.
 *
 * @author Alejandra García Preciado - 252444
 */
public class CalculoDAO implements ICalculoDAO {

    private static final Logger LOGGER = Logger.getLogger(CalculoDAO.class.getName());

    /**
     * Referencia a la colección de cálculos en la base de datos MongoDB.
     */
    private final MongoCollection<Calculo> coleccion;

    /**
     * Constructor que inicializa la colección de cálculos a partir de la
     * conexión Mongo.
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
        try {
            LOGGER.info("Guardando cálculo en base de datos");

            // Validar que el cálculo tiene los datos mínimos necesarios
            if (calculo == null) {
                throw new PersistenciaException("El cálculo no puede ser nulo");
            }

            if (calculo.getIdObra() == null) {
                LOGGER.warning("El cálculo no tiene ID de obra asignado");
                throw new PersistenciaException("El cálculo debe tener una obra asignada");
            }

            if (calculo.getId() == null) {
                // Nuevo cálculo - establecer fecha actual, MongoDB generará el ID automáticamente
                calculo.setFecha(new Date());

                LOGGER.info("Insertando nuevo cálculo para obra: " + calculo.getIdObra().toHexString());

                // Insertar el documento - MongoDB asignará automáticamente el _id al campo idCalculo
                InsertOneResult result = coleccion.insertOne(calculo);

                if (!result.wasAcknowledged()) {
                    throw new PersistenciaException("La inserción no fue reconocida por la base de datos");
                }

                // Verificar que el ID se estableció correctamente después de la inserción
                if (calculo.getId() == null) {
                    throw new PersistenciaException("Error: el ID del cálculo no se estableció después de la inserción");
                }

                LOGGER.info("Cálculo insertado exitosamente con ID: " + calculo.getId().toHexString());
            } else {
                // Actualizar cálculo existente
                LOGGER.info("Actualizando cálculo existente: " + calculo.getId().toHexString());

                Calculo calculoActualizado = coleccion.findOneAndReplace(
                        Filters.eq("_id", calculo.getId()),
                        calculo
                );

                if (calculoActualizado == null) {
                    throw new PersistenciaException("No se encontró el cálculo a actualizar con ID: "
                            + calculo.getId().toHexString());
                }

                LOGGER.info("Cálculo actualizado correctamente");
            }

            return calculo;
        } catch (PersistenciaException e) {
            // Re-lanzar excepciones de persistencia
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al guardar cálculo", e);
            throw new PersistenciaException("Error inesperado al guardar cálculo: " + e.getMessage(), e);
        }
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
        try {
            LOGGER.info("Buscando cálculo por ID: " + id);

            if (id == null || id.trim().isEmpty()) {
                throw new PersistenciaException("El ID del cálculo no puede ser nulo o vacío");
            }

            ObjectId objectId = new ObjectId(id);
            Calculo resultado = coleccion.find(Filters.eq("_id", objectId)).first();

            LOGGER.info("Cálculo encontrado: " + (resultado != null ? "Sí" : "No"));
            return resultado;
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, "ID de cálculo inválido: " + id, ex);
            throw new PersistenciaException("ID de cálculo inválido: " + id, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar cálculo por ID: " + id, ex);
            throw new PersistenciaException("Error al buscar cálculo por ID: " + id, ex);
        }
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
        try {
            LOGGER.info("Buscando cálculos para obra: " + idObra);

            if (idObra == null || idObra.trim().isEmpty()) {
                throw new PersistenciaException("El ID de la obra no puede ser nulo o vacío");
            }

            // Verificar conexión
            long totalDocumentos = coleccion.countDocuments();
            LOGGER.info("Total de documentos en colección calculos: " + totalDocumentos);

            ObjectId objectIdObra = new ObjectId(idObra);
            LOGGER.info("ObjectId convertido: " + objectIdObra.toHexString());

            List<Calculo> resultados = coleccion.find(Filters.eq("idObra", objectIdObra))
                    .into(new ArrayList<>());

            LOGGER.info("Cálculos encontrados para obra " + idObra + ": " + resultados.size());

            // Log detallado de cada resultado
            for (int i = 0; i < resultados.size(); i++) {
                Calculo calculo = resultados.get(i);
                LOGGER.info("Cálculo " + i + ": " + calculo.toString());
            }

            return resultados;
        } catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, "ID de obra inválido: " + idObra, ex);
            throw new PersistenciaException("ID de obra inválido: " + idObra, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar cálculos por obra: " + idObra, ex);
            throw new PersistenciaException("Error al buscar cálculos por obra: " + idObra, ex);
        }
    }

}
