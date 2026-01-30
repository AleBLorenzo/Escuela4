package com.example.dao;

import java.util.List;

import com.example.model.Estudiante;

public interface EstudianteDAO {

    void guardar(Estudiante e);

    void actualizar(Estudiante e);

    void eliminar(Estudiante e);

    Estudiante buscarPorId(Long id);

    List<Estudiante> listarTodos();

    List<Estudiante> estudiantesMejorPromedio(int top);

    List<Estudiante> estudiantesSinCursosActivos();

}
