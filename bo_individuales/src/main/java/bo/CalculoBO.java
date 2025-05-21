/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.CalculoDAO;
import interfaces.ICalculoDAO;

/**
 * Clase CalculoBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * los cálculos de material en el sistema. Esta clase implementa el
 * patrón Singleton para garantizar una única instancia en toda la aplicación.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class CalculoBO {
    
    /**
     * Instancia única de la clase (patrón Singleton). Garantiza que solo exista
     * una instancia en toda la aplicación.
     */
    public static CalculoBO instance;

    /**
     * DAO para calculos. Gestiona el acceso a los datos de calculos en la
     * persistencia.
     */
    private ICalculoDAO obraDAO;

    /**
     * Constructor privado (patrón Singleton). Previene la creación de múltiples
     * instancias desde fuera de la clase.
     */
    private CalculoBO() {
        this.obraDAO = new CalculoDAO();
    }

    /**
     * Método para obtener la instancia única de CalculoBO (patrón Singleton). Si
     * no existe una instancia, la crea; de lo contrario, devuelve la existente.
     *
     * @return La instancia única de CalculoBO
     */
    public static CalculoBO getInstance() {
        if (instance == null) {
            instance = new CalculoBO();
        }
        return instance;
    }
    
}
