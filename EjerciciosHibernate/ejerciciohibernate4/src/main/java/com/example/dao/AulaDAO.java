package com.example.dao;

import java.util.List;

import com.example.model.Aula;

public interface AulaDAO {

    void guardar(Aula a);

    void actualizar(Aula a);

    void eliminar(Aula a);

    Aula buscarPorId(Long id);

    List<Aula> listarTodos();

    List<Aula> listarAulasDisponibles();

}
