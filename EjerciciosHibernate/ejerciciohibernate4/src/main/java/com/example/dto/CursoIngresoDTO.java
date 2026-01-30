package com.example.dto;

import com.example.model.Curso;

public class CursoIngresoDTO {

    private Curso curso;
    private double ingresos;

    public CursoIngresoDTO(Curso curso, double ingresos) {
        this.curso = curso;
        this.ingresos = ingresos;
    }

    public Curso getCurso() {
        return curso;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

}
