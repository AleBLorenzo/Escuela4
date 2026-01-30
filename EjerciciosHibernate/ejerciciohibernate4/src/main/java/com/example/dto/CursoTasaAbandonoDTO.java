package com.example.dto;

import com.example.model.Curso;

public class CursoTasaAbandonoDTO {

    private Curso curso;
    private double tasa;

    public CursoTasaAbandonoDTO(Curso curso, double tasa) {
        this.curso = curso;
        this.tasa = tasa;
    }

    public Curso getCurso() {
        return curso;
    }

    public double getTasa() {
        return tasa;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

}
