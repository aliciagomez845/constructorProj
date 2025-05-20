/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio_dto;

/**
 *
 * @author alega
 */
public class ObraDTO {
    
    private Long id;
    private String direccion;

    public ObraDTO() {
    }

    public ObraDTO(Long id, String direccion) {
        this.id = id;
        this.direccion = direccion;
    }    

    public ObraDTO(String direccion) {
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "ObraDTO{" + "id=" + id + ", direccion=" + direccion + '}';
    }
    
}
