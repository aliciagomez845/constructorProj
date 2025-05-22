/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import conexion.ConexionMongo;
import dominio_entidades.Obra;
import excepciones.PersistenciaException;
import interfaces.IObraDAO;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Clase ObraDAO.
 *
 * Esta clase implementa la interfaz IObraDAO y proporciona métodos para
 * interactuar con la colección "obras" de la base de datos MongoDB.
 *
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
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
     * Verifica si una obra con el número especificado existe en la base de
     * datos.
     *
     * @param numero Número identificador de la obra.
     * @return true si la obra existe, false en caso contrario.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public boolean obraExiste(String numero) throws PersistenciaException {
        try {
            // Filtro para obtener por numero
            Bson filtro = Filters.eq("numero", numero);

            // Regresar si lo obtenido es diferente o no a nulo
            return coleccion.find(filtro).first() != null;
        } catch (Exception e) {
            throw new PersistenciaException("Error al validar si la obra existe:" + e.getMessage(), e);
        }
    }

    /**
     * Obtiene el ID de una obra dado su número.
     *
     * @param numero Número identificador de la obra.
     * @return ID de la obra como cadena.
     * @throws DAOException Si ocurre un error durante la consulta.
     */
    @Override
    public String obtenerIdPorNumero(String numero) throws PersistenciaException {
        try {
            // Filtro para obtener por numero
            Bson filtro = Filters.eq("numero", numero);
            // Solo incluir el id
            Bson projection = Projections.include("_id");

            // Ejecutar la búsqueda y regresar el id como string
            return coleccion.find(filtro).projection(projection).first().getObjectString();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener el id de la obra por numero:" + e.getMessage(), e);
        }
    }

    /**
     * Obtiene la dirección de una obra dado su ID.
     *
     * @param id ID de la obra como cadena.
     * @return Dirección de la obra.
     * @throws DAOException Si ocurre un error durante la consulta o el ID no es
     * válido.
     */
    @Override
    public String obtenerDireccionObra(String id) throws PersistenciaException {
        try {
            // Convertir el id de string a ObjectId
            ObjectId objectId = new ObjectId(id);
            // Filtro para obtener por id
            Bson filtro = Filters.eq("_id", objectId);

            // Ejecutar la búsqueda y regresar la dirección
            return coleccion.find(filtro).first().getDireccion();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener la dirección de la obra" + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una obra completa dado su ID.
     *
     * @param id ID de la obra como cadena.
     * @return Objeto Obra correspondiente al ID.
     * @throws DAOException Si ocurre un error durante la consulta.
     */
    @Override
    public Obra obtenerObra(String id) throws PersistenciaException {

        try {
            // Convertir el id de string a ObjectId
            ObjectId objectId = new ObjectId(id);
            // Filtro para obtener por id
            Bson filtro = Filters.eq("_id", objectId);

            // Ejecutar la búsqueda y regresar la obra
            return coleccion.find(filtro).first();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener la obra" + e.getMessage(), e);
        }
    }
}
