package com.tienda.dao;

import java.util.List;

import com.tienda.model.Categoria;

public interface CategoriaDAO {

       void guardar(Categoria categoria);
    void actualizar(Categoria categoria);
    void eliminar(Long id);
    Categoria buscarPorId(Long id);
    List<Categoria> listarTodos();

}
