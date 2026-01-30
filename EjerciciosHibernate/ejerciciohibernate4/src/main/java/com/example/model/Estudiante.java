package com.example.model;

import java.time.LocalDate;
import java.time.Period;
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
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

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
    private LocalDate fechaNacimiento;

    @Size(max = 200)
    private String direccion;

    @NotNull
    private LocalDate fechaRegistro = LocalDate.now();

    @NotNull
    private Boolean activo = true;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private Set<Matricula> matriculas = new HashSet<>();

    public Estudiante() {
    }

    @AssertTrue(message = "El estudiante debe tener al menos 16 años")
    public boolean isEdadValida() {
        if (fechaNacimiento == null)
            return true;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 16;
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public Boolean getActivo() {
        return activo;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}