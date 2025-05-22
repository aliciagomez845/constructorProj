/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Clase que proporciona la conexión a la base de datos MongoDB.
 *
 * Esta clase implementa el patrón Singleton para garantizar una única instancia
 * de la conexión a la base de datos en toda la aplicación.
 * 
 * @author Alejandra García 252444
 * @author Isabel Valenzuela 253301
 * @author Ximena Rosales 253088
 * @author Dario Cortez 252267
 * @author Jesús Osuna 240549
 */
public class ConexionMongo {

    private static MongoClient mongoClient = null;

    // Si se conectan por Atlas, esta es la URL que les proporciona la página
    private static final String URL = "mongodb+srv://user:itson@cluster0.4x3w8il.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

    // Nombre de la base de datos que se utilizará en el proyecto
    private static final String DATABASE_NAME = "buildControl";

    /**
     * Constructor privado para evitar instanciación directa.
     */
    private ConexionMongo() {

    }

    /**
     * Obtiene la instancia de la base de datos MongoDB.
     *
     * @return la instancia de la base de datos
     */
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {

            // 1. Configuramos el codec para manejar POJOs
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );

            // 2. Configuramos los ajustes del cliente MongoDB, incluyendo la cadena de conexión
            // (URL) y el registro de codecs anterior
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(URL))
                    .codecRegistry(pojoCodecRegistry)
                    .build();

            // 3. Asignamos los ajustes al MongoClient estático de la clase
            mongoClient = MongoClients.create(clientSettings);

            // 4. Regresamos la base de datos con la configuración de codecs
            return mongoClient.getDatabase(DATABASE_NAME).withCodecRegistry(pojoCodecRegistry);
        }

        // Si ya existe el cliente, simplemente regresamos la instancia de la base de datos
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}
