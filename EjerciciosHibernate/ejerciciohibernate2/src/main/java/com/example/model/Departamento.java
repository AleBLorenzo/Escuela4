package com.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "departamentos")
public class Departamento {

      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

     @Column(name = "nombre",nullable = false, length = 100)
    private String nombre ;

     @Column(name = "codigo", unique = true, nullable = false, length = 10)
    private String codigo ;

     @Column(name = "ubicacion", length = 100)
    private String ubicacion ;

     @Column(name = "presupuesto")
      @Min(value = 0, message = "El presupuesto debe ser mayor o igual a 0")
    private Double  presupuesto ;

     @Column(name = "fecha_creacion", nullable = false, updatable = false )
    private LocalDate  fechaCreacion ;

     // Relación OneToMany (lado inverso)
    @OneToMany(
        mappedBy = "departamento",           // Atributo en Empleado
        cascade = CascadeType.ALL,           // Propagar operaciones
        orphanRemoval = true,                // Eliminar huérfanos
        fetch = FetchType.LAZY               // Carga perezosa
    )
    private List<Empleado> empleados = new ArrayList<>();
    
    // Métodos helper para mantener sincronía bidireccional
    public void addEmpleado(Empleado empleado) {
        empleados.add(empleado);
        empleado.setDepartamento(this);
    }
    
    public void removeEmpleado(Empleado empleado) {
        empleados.remove(empleado);
        empleado.setDepartamento(null);
    }

    @Override
    public String toString() {
        return "Departamento [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + ", ubicacion=" + ubicacion
                + ", presupuesto=" + presupuesto + ", fechaCreacion=" + fechaCreacion + ", empleados=" + empleados
                + "]";
    }

    public Departamento() {
    }

    public Departamento(String codigo, String nombre, Double presupuesto, String ubicacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.ubicacion = ubicacion;
    }


     @PrePersist
    protected void onCreate() {

        if (fechaCreacion == null) {
            fechaCreacion = LocalDate.now();
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

}
