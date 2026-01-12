package com.example.model;

import java.time.LocalDate;

public class Prestamo {

     private int id;
     private int libro_id;
    private String nombre_usuario;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion_esperada;
    private LocalDate fecha_devolucion_real;
    private boolean devuelto;

    public Prestamo(boolean devuelto, LocalDate fecha_devolucion_esperada, LocalDate fecha_devolucion_real, LocalDate fecha_prestamo, int id, int libro_id, String nombre_usuario) {
        this.devuelto = devuelto;
        this.fecha_devolucion_esperada = fecha_devolucion_esperada;
        this.fecha_devolucion_real = fecha_devolucion_real;
        this.fecha_prestamo = fecha_prestamo;
        this.id = id;
        this.libro_id = libro_id;
        this.nombre_usuario = nombre_usuario;
    }

    public Prestamo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLibro_id() {
        return libro_id;
    }

    public void setLibro_id(int libro_id) {
        this.libro_id = libro_id;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public LocalDate getFecha_devolucion_esperada() {
        return fecha_devolucion_esperada;
    }

    public void setFecha_devolucion_esperada(LocalDate fecha_devolucion_esperada) {
        this.fecha_devolucion_esperada = fecha_devolucion_esperada;
    }

    public LocalDate getFecha_devolucion_real() {
        return fecha_devolucion_real;
    }

    public void setFecha_devolucion_real(LocalDate fecha_devolucion_real) {
        this.fecha_devolucion_real = fecha_devolucion_real;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prestamo{");
        sb.append("id=").append(id);
        sb.append(", libro_id=").append(libro_id);
        sb.append(", nombre_usuario=").append(nombre_usuario);
        sb.append(", fecha_prestamo=").append(fecha_prestamo);
        sb.append(", fecha_devolucion_esperada=").append(fecha_devolucion_esperada);
        sb.append(", fecha_devolucion_real=").append(fecha_devolucion_real);
        sb.append(", devuelto=").append(devuelto);
        sb.append('}');
        return sb.toString();
    }

}
