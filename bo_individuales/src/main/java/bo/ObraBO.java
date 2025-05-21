/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.ObraDAO;
import interfaces.IObraDAO;

/**
 * Clase ObraBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * las obras o proyectos de construcción en el sistema. Esta clase implementa el
 * patrón Singleton para garantizar una única instancia en toda la aplicación.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class ObraBO {
    
    /**
     * Instancia única de la clase (patrón Singleton). Garantiza que solo exista
     * una instancia en toda la aplicación.
     */
    public static ObraBO instance;

    /**
     * DAO para obras. Gestiona el acceso a los datos de obras en la
     * persistencia.
     */
    private IObraDAO obraDAO;

    /**
     * Constructor privado (patrón Singleton). Previene la creación de múltiples
     * instancias desde fuera de la clase.
     */
    private ObraBO() {
        this.obraDAO = new ObraDAO();
    }

    /**
     * Método para obtener la instancia única de ObraBO (patrón Singleton). Si
     * no existe una instancia, la crea; de lo contrario, devuelve la existente.
     *
     * @return La instancia única de ObraBO
     */
    public static ObraBO getInstance() {
        if (instance == null) {
            instance = new ObraBO();
        }
        return instance;
    }
    
}
