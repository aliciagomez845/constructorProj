/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio_dto;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alega
 */
public class CalculoDTO {
    
    private ObraDTO obra;
    private LocalDate fecha;
    private Double volumenCalculado;
    private ElementoDTO elemento;
    private List<MaterialCalculoDTO> materialesCalculados;

    public CalculoDTO() {
    }

    public CalculoDTO(ObraDTO obra, LocalDate fecha, Double volumenCalculado, ElementoDTO elemento, List<MaterialCalculoDTO> materialesCalculados) {
        this.obra = obra;
        this.fecha = fecha;
        this.volumenCalculado = volumenCalculado;
        this.elemento = elemento;
        this.materialesCalculados = materialesCalculados;
    }

    public ObraDTO getObra() {
        return obra;
    }

    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getVolumenCalculado() {
        return volumenCalculado;
    }

    public void setVolumenCalculado(Double volumenCalculado) {
        this.volumenCalculado = volumenCalculado;
    }

    public ElementoDTO getElemento() {
        return elemento;
    }

    public void setElemento(ElementoDTO elemento) {
        this.elemento = elemento;
    }

    public List<MaterialCalculoDTO> getMaterialesCalculados() {
        return materialesCalculados;
    }

    public void setMaterialesCalculados(List<MaterialCalculoDTO> materialesCalculados) {
        this.materialesCalculados = materialesCalculados;
    }

    @Override
    public String toString() {
        return "CalculoDTO{" + "obra=" + obra + ", fecha=" + fecha + ", volumenCalculado=" + volumenCalculado + ", elemento=" + elemento + ", materialesCalculados=" + materialesCalculados + '}';
    }
    
}
