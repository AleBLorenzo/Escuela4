package com.example.dto;

import com.example.model.Profesor;

public class ProfesorCargaDTO {

    private Profesor profesor;
    private int cargaHoraria;

    public ProfesorCargaDTO(Profesor profesor, int cargaHoraria) {
        this.profesor = profesor;
        this.cargaHoraria = cargaHoraria;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

}
