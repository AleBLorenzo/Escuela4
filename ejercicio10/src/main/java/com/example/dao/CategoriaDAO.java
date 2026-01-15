package com.example.dao;

import java.util.List;

import com.example.model.Categoria;

public interface CategoriaDAO {

    void agregar(Categoria c);

    void actualizar(Categoria c);

    void eliminar(int id);

    Categoria obtenerPorId(int id);

    List<Categoria> listarTodos();

}
