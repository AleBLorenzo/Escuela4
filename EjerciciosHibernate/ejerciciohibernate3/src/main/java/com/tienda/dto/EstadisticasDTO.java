package com.tienda.dto;

public class EstadisticasDTO {

      private String nombre;
    private Long cantidad;
    private Double total;

    @Override
    public String toString() {
        return "EstadisticasDTO [nombre=" + nombre + ", cantidad=" + cantidad + ", total=" + total + "]";
    }

    public EstadisticasDTO() {
    }

    public EstadisticasDTO(String nombre, Long cantidad, Double total) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public boolean getIngresoTotal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
