/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio_dto;

import negocio_enums.TipoMaterialNegocio;
import negocio_enums.UnidadMaterialNegocio;

/**
 *
 * @author alega
 */
public class MaterialCalculoDTO {
    
    private TipoMaterialNegocio tipo;
    private UnidadMaterialNegocio unidad;
    private Double cantidadCalculada;

    public MaterialCalculoDTO() {
    }

    public MaterialCalculoDTO(TipoMaterialNegocio tipo, UnidadMaterialNegocio unidad, Double cantidadCalculada) {
        this.tipo = tipo;
        this.unidad = unidad;
        this.cantidadCalculada = cantidadCalculada;
    }

    public TipoMaterialNegocio getTipo() {
        return tipo;
    }

    public void setTipo(TipoMaterialNegocio tipo) {
        this.tipo = tipo;
    }

    public UnidadMaterialNegocio getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadMaterialNegocio unidad) {
        this.unidad = unidad;
    }

    public Double getCantidadCalculada() {
        return cantidadCalculada;
    }

    public void setCantidadCalculada(Double cantidadCalculada) {
        this.cantidadCalculada = cantidadCalculada;
    }

    @Override
    public String toString() {
        return "MaterialCalculoDTO{" + "tipo=" + tipo + ", unidad=" + unidad + ", cantidadCalculada=" + cantidadCalculada + '}';
    }
    
}
