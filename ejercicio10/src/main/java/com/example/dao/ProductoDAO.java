package com.example.dao;

import java.util.List;

import com.example.model.Producto;

public interface ProductoDAO {

    void agregar(Producto p);

    void actualizar(Producto p);

    void eliminar(int id);

    Producto obtenerPorId(int id);

    List<Producto> listarTodos();

}
