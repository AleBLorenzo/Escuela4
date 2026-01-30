package com.example.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String nombre;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @Size(max = 20)
    private String telefono;

    @NotNull
    @Size(max = 100)
    private String especialidad;

    @Size(max = 200)
    private String titulacion;

    @NotNull
    private LocalDate fechaContratacion;

    @NotNull
    @Min(1)
    private Double salarioPorHora;

    @NotNull
    private Boolean activo = true;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private Set<Asignacion> asignaciones = new HashSet<>();

    public Profesor() {
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Double getSalarioPorHora() {
        return salarioPorHora;
    }

    public void setSalarioPorHora(Double salarioPorHora) {
        this.salarioPorHora = salarioPorHora;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setAsignaciones(Set<Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }
}