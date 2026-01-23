package com.example.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "salario", nullable = false)
    @Min(value = 0, message = "El salario debe ser mayor o igual a 0")
    private Double salario;

    @Column(name = "fecha_contratacion", nullable = false, updatable = false)
    private LocalDate fechaContratacion;

    @Column(name = "activo", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean activo;

    // Relación ManyToOne (lado propietario - tiene la FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", nullable = true)
    private Departamento departamento;

    @Override
    public String toString() {
        return "Empleado [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
                + ", salario=" + salario + ", fechaContratacion=" + fechaContratacion + ", activo=" + activo
                + ", departamento=" + departamento + "]";
    }

    public Empleado() {
    }

    public Empleado(Boolean activo, String apellidos, Departamento departamento, String email, String nombre,
            Double salario) {
        this.activo = activo;
        this.apellidos = apellidos;
        this.departamento = departamento;
        this.email = email;
        this.nombre = nombre;
        this.salario = salario;
    }

    @PrePersist
    protected void onCreate() {
        if (fechaContratacion == null) {
            fechaContratacion = LocalDate.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

}
