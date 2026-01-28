package com.tienda.dao;

import java.util.List;

import com.tienda.model.Producto;

public interface ProductoDAO {

    void guardar(Producto producto);

    void actualizar(Producto producto);

    void eliminar(Long id);

    Producto buscarPorId(Long id);

    List<Producto> listarTodos();

    List<Producto> listarPorCategoria(Long idCategoria);

    List<Producto> buscarPorNombre(String nombre);

    List<Producto> productosConStockBajo(int stockMinimo);
}
