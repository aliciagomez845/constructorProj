/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio_entidades;

import dominio_enums.TipoMaterialDatos;
import dominio_enums.UnidadMaterialDatos;

/**
 *
 * @author alega
 */
public class MaterialCalculo {
    
    private Long idMaterial;
    private UnidadMaterialDatos unidad;
    private Double cantidad;
    private TipoMaterialDatos tipo;

    public MaterialCalculo() {
    }

    public MaterialCalculo(UnidadMaterialDatos unidad, Double cantidad, TipoMaterialDatos tipo) {
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public UnidadMaterialDatos getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadMaterialDatos unidad) {
        this.unidad = unidad;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public TipoMaterialDatos getTipo() {
        return tipo;
    }

    public void setTipo(TipoMaterialDatos tipo) {
        this.tipo = tipo;
    }
    
}
