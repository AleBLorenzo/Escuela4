package com.tienda.dao;

import java.util.List;

import com.tienda.model.Cliente;

public interface ClienteDAO {

    void guardar(Cliente cliente);

    void actualizar(Cliente cliente);

    void eliminar(Long id);

    Cliente buscarPorId(Long id);

    Cliente buscarPorEmail(String email);

    List<Cliente> listarTodos();

    List<Cliente> listarClientesConPedidos();
}
