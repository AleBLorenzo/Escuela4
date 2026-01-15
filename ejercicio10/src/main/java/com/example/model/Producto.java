package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Producto {

    private Long id;
    private String codigo;
    private String nombre;
    private Long categoria_id;
    private BigDecimal precio;
    private int stock;
    private LocalDate fecha_alta;
    private boolean activo;

    public Producto() {
    }

    public Producto(Long id, String codigo, String nombre, Long categoria_id, BigDecimal precio, int stock,
            LocalDate fecha_alta, boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria_id = categoria_id;
        this.precio = precio;
        this.stock = stock;
        this.fecha_alta = fecha_alta;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Long categoria_id) {
        this.categoria_id = categoria_id;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(LocalDate fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
