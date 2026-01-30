package com.example.dao;

import java.util.List;

import com.example.model.Profesor;

public interface ProfesorDAO {

    void guardar(Profesor p);

    void actualizar(Profesor p);

    void eliminar(Profesor p);

    Profesor buscarPorId(Long id);

    List<Profesor> listarTodos();

    List<Profesor> profesoresMayorCargaHoraria(int top);

}
