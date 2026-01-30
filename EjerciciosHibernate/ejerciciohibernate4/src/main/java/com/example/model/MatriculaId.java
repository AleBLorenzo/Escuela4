package com.example.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MatriculaId implements Serializable {

    @Column(name = "estudiante_id")
    private Long estudianteId;

    @Column(name = "curso_id")
    private Long cursoId;

    public MatriculaId() {
    }

    public MatriculaId(Long estudianteId, Long cursoId) {
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MatriculaId))
            return false;
        MatriculaId that = (MatriculaId) o;
        return Objects.equals(estudianteId, that.estudianteId) &&
                Objects.equals(cursoId, that.cursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudianteId, cursoId);
    }
}