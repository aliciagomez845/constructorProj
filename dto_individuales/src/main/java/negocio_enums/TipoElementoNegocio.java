/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package negocio_enums;

import java.util.List;

/**
 *
 * @author alega
 */
public enum TipoElementoNegocio {
    
    COLUMNA_CUADRADA(
            "Columna Cuadrada",
            List.of("alto", "ancho", "espesor")
    ),
    LOSA_CONTRAPISO(
            "Losa de Contrapiso",
            List.of("largo", "ancho", "espesor")
    ),
    LOSA_ENTREPISO,
    VIGA,
    NIVELACION_MUROS_VERTICAL,
    NIVELACION_PISOS_HORIZONTAL,
    MURO_LADRILLO;
    
    private final String nombreAmigable;
    private final List<String> dimensionesRequeridas;

    TipoElementoNegocio(String nombreTipo, List<String> dimensionesRequeridas) {
        this.nombreAmigable = nombreTipo;
        this.dimensionesRequeridas = dimensionesRequeridas;
    }

    public String getNombreAmigable() {
        return nombreAmigable;
    }

    public List<String> getDimensionesRequeridas() {
        return dimensionesRequeridas;
    }
    
}
