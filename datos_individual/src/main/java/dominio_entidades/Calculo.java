/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio_entidades;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alega
 */
public class Calculo {
    
    private Long idCalculo;
    private LocalDate fecha;
    private Elemento elemento;
    private Obra obra;
    private List<MaterialCalculo> materialesCalculados;

    public Calculo() {
    }

    public Calculo(LocalDate fecha, Elemento elemento, Obra obra, List<MaterialCalculo> materialesCalculados) {
        this.fecha = fecha;
        this.elemento = elemento;
        this.obra = obra;
        this.materialesCalculados = materialesCalculados;
    }

    public Long getIdCalculo() {
        return idCalculo;
    }

    public void setIdCalculo(Long idCalculo) {
        this.idCalculo = idCalculo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public List<MaterialCalculo> getMaterialesCalculados() {
        return materialesCalculados;
    }

    public void setMaterialesCalculados(List<MaterialCalculo> materialesCalculados) {
        this.materialesCalculados = materialesCalculados;
    }
    
}
