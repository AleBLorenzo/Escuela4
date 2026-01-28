package com.tienda.dao;

import java.util.List;

import com.tienda.model.Pedido;

public interface PedidoDAO {

   void guardar(Pedido pedido);
    void actualizar(Pedido pedido);
    void eliminar(Long id);
    Pedido buscarPorId(Long id);
    List<Pedido> listarTodos();
    List<Pedido> listarPorCliente(Long clienteId);

}
