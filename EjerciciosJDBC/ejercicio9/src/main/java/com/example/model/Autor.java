package com.example.model;

import java.time.LocalDate;

public class Autor {

    private int id;
    private String nombre ;
    private String apellidos;
    private String nacionalidad;
    private LocalDate fecha_nacimiento;

    public Autor() {
    }

    public Autor(String apellidos, LocalDate fecha_nacimiento, int id, String nacionalidad, String nombre) {
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.id = id;
        this.nacionalidad = nacionalidad;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Autor{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", apellidos=").append(apellidos);
        sb.append(", nacionalidad=").append(nacionalidad);
        sb.append(", fecha_nacimiento=").append(fecha_nacimiento);
        sb.append('}');
        return sb.toString();
    }

}
