package com.example.dto;

import com.example.model.Estudiante;

public class EstudiantePromedioDTO {

    private Estudiante estudiante;
    private double promedio;

    public EstudiantePromedioDTO(Estudiante estudiante, double promedio) {
        this.estudiante = estudiante;
        this.promedio = promedio;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

}
