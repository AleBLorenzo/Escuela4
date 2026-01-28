package com.tienda.dao;

import java.util.List;

import com.tienda.model.LineaPedido;

public interface LineaPedidoDAO {

    void guardar(LineaPedido linea);

    void actualizar(LineaPedido linea);

    void eliminar(Long id);

    LineaPedido buscarPorId(Long id);

    List<LineaPedido> listarPorPedido(Long pedidoId);

}
