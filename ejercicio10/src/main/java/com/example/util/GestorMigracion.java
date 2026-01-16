package com.example.util;

import java.util.List;

import com.example.dao.CategoriaDAO;
import com.example.dao.MovimientoStockDAO;
import com.example.dao.ProductoDAO;
import com.example.factory.DAOFactory;
import com.example.model.Categoria;
import com.example.model.MovimientoStock;
import com.example.model.Producto;

public class GestorMigracion {

    private DAOFactory origenFactory;
    private DAOFactory destinoFactory;

    public GestorMigracion(DAOFactory origen, DAOFactory destino) {
        this.origenFactory = origen;
        this.destinoFactory = destino;
    }

    public void migrar() {

        // Migrar Categorías
        List<Categoria> categorias = origenFactory.getCategoriaDAO().listarTodos();
        CategoriaDAO daoCatDestino = destinoFactory.getCategoriaDAO();
        for (Categoria c : categorias) {
            daoCatDestino.agregar(c);
        }

        // Migrar Productos
        List<Producto> productos = origenFactory.getProductoDAO().listarTodos();
        ProductoDAO daoProdDestino = destinoFactory.getProductoDAO();
        for (Producto p : productos) {
            daoProdDestino.agregar(p);
        }

        // Migrar Movimientos de Stock
        List<MovimientoStock> movimientos = origenFactory.getMovimientoStockDAO().listarTodos();
        MovimientoStockDAO daoMovDestino = destinoFactory.getMovimientoStockDAO();
        for (MovimientoStock m : movimientos) {
            daoMovDestino.agregar(m);
        }

        System.out.println("Migración completada!");
    }

}
