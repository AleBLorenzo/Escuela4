package com.example.dao;

import java.util.List;

import com.example.model.Matricula;
import com.example.model.MatriculaId;

public interface MatriculaDAO {

    void guardar(Matricula m);

    void actualizar(Matricula m);

    void eliminar(Matricula m);

    Matricula buscarPorId(MatriculaId id);

    List<Matricula> listarTodos();

    long contarMatriculasActivasPorCurso(Long cursoId);

    List<Object[]> estudiantesDeCursoConPromedios(String codigoCurso);

}
