/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inicio;

import excepciones.PresentacionException;
import presentacion.CoordinadorAplicacion;

/**
 *
 * @author alega
 */
public class InicioCalculoMateriales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PresentacionException {
        CoordinadorAplicacion.getInstancia().mostrarObraSeleccionada();
    }
    
}
