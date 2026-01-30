package com.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "matriculas")
public class Matricula {

    @EmbeddedId
    private MatriculaId id;

    @ManyToOne
    @MapsId("estudianteId")
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("cursoId")
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @NotNull
    private LocalDate fechaMatricula = LocalDate.now();

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double calificacionFinal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoMatricula estado = EstadoMatricula.ACTIVA;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double porcentajeAsistencia = 0.0;

    @Size(max = 500)
    private String observaciones;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    public Matricula() {
    }

    public Matricula(Estudiante estudiante, Curso curso) {
        this();
        this.estudiante = estudiante;
        this.curso = curso;
        this.id = new MatriculaId(estudiante.getId(), curso.getId());
    }

    public void calcularCalificacionFinal() {
        if (evaluaciones.isEmpty()) {
            calificacionFinal = null;
        } else {
            double suma = evaluaciones.stream().mapToDouble(Evaluacion::getNota).sum();
            calificacionFinal = suma / evaluaciones.size();
        }
    }

    // getters
    public MatriculaId getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setId(MatriculaId id) {
        this.id = id;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Double getCalificacionFinal() {
        return calificacionFinal;
    }

    public void setCalificacionFinal(Double calificacionFinal) {
        this.calificacionFinal = calificacionFinal;
    }

    public EstadoMatricula getEstado() {
        return estado;
    }

    public void setEstado(EstadoMatricula estado) {
        this.estado = estado;
    }

    public Double getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(Double porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}