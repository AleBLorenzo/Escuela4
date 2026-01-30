package com.example.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AsignacionId implements Serializable {

    @Column(name = "profesor_id")
    private Long profesorId;

    @Column(name = "curso_id")
    private Long cursoId;

    public AsignacionId() {
    }

    public AsignacionId(Long profesorId, Long cursoId) {
        this.profesorId = profesorId;
        this.cursoId = cursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AsignacionId))
            return false;
        AsignacionId that = (AsignacionId) o;
        return Objects.equals(profesorId, that.profesorId) &&
                Objects.equals(cursoId, that.cursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profesorId, cursoId);
    }

    public Long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Long profesorId) {
        this.profesorId = profesorId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}
