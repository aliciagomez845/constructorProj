/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.MaterialCalculoDAO;
import interfaces.IMaterialCalculoDAO;

/**
 * Clase MaterialCalculoBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * los materiales calculados en los cálculos del sistema. Esta clase implementa el patrón Singleton para
 * garantizar una única instancia en toda la aplicación.
 * 
 * @author Alejandra García Preciado - 252444
 */
public class MaterialCalculoBO {
    
    /**
     * Instancia única de la clase (patrón Singleton). Garantiza que solo exista
     * una instancia en toda la aplicación.
     */
    public static MaterialCalculoBO instance;

    /**
     * DAO para materiales calculados. Gestiona el acceso a los datos de los materiales calculados en la
     * persistencia.
     */
    private IMaterialCalculoDAO materialCalculoDAO;

    /**
     * Constructor privado (patrón Singleton). Previene la creación de múltiples
     * instancias desde fuera de la clase.
     */
    private MaterialCalculoBO() {
        this.materialCalculoDAO = new MaterialCalculoDAO();
    }

    /**
     * Método para obtener la instancia única de MaterialCalculoBO (patrón Singleton).
     * Si no existe una instancia, la crea; de lo contrario, devuelve la
     * existente.
     *
     * @return La instancia única de MaterialCalculoBO
     */
    public static MaterialCalculoBO getInstance() {
        if (instance == null) {
            instance = new MaterialCalculoBO();
        }
        return instance;
    }
    
}
