package com.example.dao;

import java.util.List;

import com.example.model.Curso;

public interface CursoDAO {

    void guardar(Curso curso);

    void actualizar(Curso curso);

    void eliminar(Curso curso);

    Curso buscarPorId(Long id);

    Curso buscarPorCodigo(String codigo);

    List<Curso> listarTodos();

    List<Curso> cursosConMasMatriculados(int top);

    List<Curso> cursosPorCompletarEsteMes();

}
