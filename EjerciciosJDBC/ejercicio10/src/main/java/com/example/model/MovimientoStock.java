package com.example.model;

import java.time.LocalDate;

public class MovimientoStock {

    private Long id;
    private Long producto_id;
    private String tipo;
    private int cantidad;
    private String motivo;
    private LocalDate fecha;

    public MovimientoStock() {
    }

    public MovimientoStock(int cantidad, LocalDate fecha, Long id, String motivo, Long producto_id, String tipo) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.id = id;
        this.motivo = motivo;
        this.producto_id = producto_id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Long producto_id) {
        this.producto_id = producto_id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
