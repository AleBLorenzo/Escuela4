package com.example.dao;

import java.util.List;

import com.example.model.Evaluacion;

public interface EvaluacionDAO {


    void guardar(Evaluacion evaluacion);

    void actualizar(Evaluacion evaluacion);

    void eliminar(Evaluacion evaluacion);

    Evaluacion buscarPorId(Long id);

    List<Evaluacion> listarTodos();

}
