package com.example.model;

import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "asignaciones")
public class Asignacion {

    @EmbeddedId
    private AsignacionId id;

    @ManyToOne
    @MapsId("profesorId")
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne
    @MapsId("cursoId")
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @NotNull
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolProfesor rol;

    @NotNull
    @Min(1)
    private Integer horasSemana;

    public Asignacion() {
    }

    // getters
    public Profesor getProfesor() {
        return profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public AsignacionId getId() {
        return id;
    }

    public void setId(AsignacionId id) {
        this.id = id;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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

    public RolProfesor getRol() {
        return rol;
    }

    public void setRol(RolProfesor rol) {
        this.rol = rol;
    }

    public Integer getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(Integer horasSemana) {
        this.horasSemana = horasSemana;
    }

    @Override
    public String toString() {
        return "Asignacion [id=" + id + ", profesor=" + profesor + ", curso=" + curso + ", fechaInicio=" + fechaInicio
                + ", fechaFin=" + fechaFin + ", rol=" + rol + ", horasSemana=" + horasSemana + "]";
    }
}