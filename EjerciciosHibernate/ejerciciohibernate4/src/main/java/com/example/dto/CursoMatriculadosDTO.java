package com.example.dto;

import com.example.model.Curso;

public class CursoMatriculadosDTO {

    private Curso curso;
    private long totalMatriculados;

    public CursoMatriculadosDTO(Curso curso, long totalMatriculados) {
        this.curso = curso;
        this.totalMatriculados = totalMatriculados;
    }

    public Curso getCurso() {
        return curso;
    }

    public long getTotalMatriculados() {
        return totalMatriculados;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setTotalMatriculados(long totalMatriculados) {
        this.totalMatriculados = totalMatriculados;
    }
}
