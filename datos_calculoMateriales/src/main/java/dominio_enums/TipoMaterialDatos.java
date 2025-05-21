/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package dominio_enums;

/**
 * Enumeración TipoMaterialDatos.
 *
 * Representa los distintos tipos de materiales registrados en el modelo de
 * datos, cada uno asociado a su unidad base correspondiente.
 *
 * Esta enumeración se utiliza en la capa de datos para clasificar los
 * materiales y definir su unidad de medida estándar, facilitando así
 * operaciones como almacenamiento, consultas y generación de reportes.
 *
 * @author Isabel Valenzuela Rocha - 253301
 * @author Alejandra García Preciado - 252444
 */
public enum TipoMaterialDatos {
    
    CEMENTO(UnidadMaterialDatos.KILOGRAMO),
    ARENA(UnidadMaterialDatos.METRO_CUBICO),
    GRAVA(UnidadMaterialDatos.METRO_CUBICO),
    LADRILLO(UnidadMaterialDatos.PIEZA),
    CLAVO(UnidadMaterialDatos.PIEZA),
    PINTURA(UnidadMaterialDatos.LITRO),
    MADERA(UnidadMaterialDatos.METRO),
    RESINA(UnidadMaterialDatos.LITRO);
    
    /**
     * Unidad base con la que se mide este tipo de material.
     *
     * Por ejemplo, el cemento se mide en kilogramos, la arena en metros
     * cúbicos, etc. Esta unidad se define al momento de declarar la constante
     * enum correspondiente.
     */
    private UnidadMaterialDatos unidad;

    /**
     * Constructor privado del enum TipoMaterialDatos.
     *
     * Asocia una unidad base al tipo de material para garantizar que cada tipo
     * tenga una unidad de medida predeterminada, facilitando la consistencia en
     * los procesos de almacenamiento y manipulación de datos.
     *
     * @param unidad la unidad base asociada a este tipo de material
     */
    private TipoMaterialDatos(UnidadMaterialDatos unidad) {
        this.unidad = unidad;
    }

    /**
     * Devuelve la unidad base asociada al tipo de material.
     *
     * @return unidad base del material
     */
    public UnidadMaterialDatos getUnidad() {
        return unidad;
    }
    
}
