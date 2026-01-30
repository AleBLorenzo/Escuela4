package com.example.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "CUR-\\d{3}")
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotNull
    @Size(max = 150)
    private String nombre;

    @Size(max = 1000)
    private String descripcion;

    @NotNull
    @Min(1)
    private Integer duracionHoras;

    @NotNull
    @Min(0)
    private Double precio;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NivelCurso nivel;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @NotNull
    @Min(1)
    private Integer maxEstudiantes;

    @NotNull
    private Boolean activo = true;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<Matricula> matriculas = new HashSet<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private Set<Asignacion> asignaciones = new HashSet<>();

    public Curso() {
    }

    // getters y setters
    public Long getId() {
        return id;
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

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public Set<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public NivelCurso getNivel() {
        return nivel;
    }

    public void setNivel(NivelCurso nivel) {
        this.nivel = nivel;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getMaxEstudiantes() {
        return maxEstudiantes;
    }

    public void setMaxEstudiantes(Integer maxEstudiantes) {
        this.maxEstudiantes = maxEstudiantes;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}