package com.example.dao;

import java.util.List;

import com.example.model.MovimientoStock;



public interface MovimientoStockDAO {

    void agregar(MovimientoStock m);

    void actualizar(MovimientoStock m);

    void eliminar(int id);

    MovimientoStock obtenerPorId(int id);

    List<MovimientoStock> listarTodos();

}
