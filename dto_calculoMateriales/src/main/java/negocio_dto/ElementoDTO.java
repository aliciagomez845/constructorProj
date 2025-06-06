/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio_dto;

import negocio_enums.TipoElementoNegocio;

/**
 *
 * @author alega
 */
public class ElementoDTO {
    
    private TipoElementoNegocio tipo;
    private Double profundidad;
    private Double largo;
    private Double alto;
    private Double ancho;
    private Double espesor;

    public ElementoDTO() {
    }

    public ElementoDTO(TipoElementoNegocio tipo, Double profundidad, Double largo, Double alto, Double ancho, Double espesor) {
        this.tipo = tipo;
        this.profundidad = profundidad;
        this.largo = largo;
        this.alto = alto;
        this.ancho = ancho;
        this.espesor = espesor;
    }

    public TipoElementoNegocio getTipo() {
        return tipo;
    }

    public void setTipo(TipoElementoNegocio tipo) {
        this.tipo = tipo;
    }

    public Double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getEspesor() {
        return espesor;
    }

    public void setEspesor(Double espesor) {
        this.espesor = espesor;
    }

    @Override
    public String toString() {
        return "ElementoDTO{" + "tipo=" + tipo + ", profundidad=" + profundidad + ", largo=" + largo + ", alto=" + alto + ", ancho=" + ancho + ", espesor=" + espesor + '}';
    }
    
}
