/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package negocio_enums;

/**
 *
 * @author alega
 */
public enum TipoMaterialNegocio {
    
    CEMENTO(UnidadMaterialNegocio.KILOGRAMO),
    ARENA(UnidadMaterialNegocio.METRO_CUBICO),
    GRAVA(UnidadMaterialNegocio.METRO_CUBICO),
    LADRILLO(UnidadMaterialNegocio.PIEZA),
    CLAVO(UnidadMaterialNegocio.PIEZA),
    PINTURA(UnidadMaterialNegocio.LITRO),
    MADERA(UnidadMaterialNegocio.METRO),
    RESINA(UnidadMaterialNegocio.LITRO);
    
    private UnidadMaterialNegocio unidad;
    
    private TipoMaterialNegocio(UnidadMaterialNegocio unidad) {
        this.unidad = unidad;
    }
    
    public UnidadMaterialNegocio getUnidad() {
        return unidad;
    }
    
}
