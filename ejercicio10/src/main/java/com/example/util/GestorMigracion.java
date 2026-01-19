package com.example.util;

import java.sql.Connection;
import java.util.List;

import com.example.factory.DAOFactory;
import com.example.model.Categoria;
import com.example.model.MovimientoStock;
import com.example.model.Producto;

public class GestorMigracion {

   private DAOFactory origen;
    private DAOFactory destino;

    public GestorMigracion(DAOFactory origen, DAOFactory destino) {
        this.origen = origen;
        this.destino = destino;
    }

    public void migrar() {
        Connection conn = ConexionDB.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            System.out.println("Migrando categorías...");
            List<Categoria> categorias = origen.getCategoriaDAO().listarTodos();
            for (Categoria c : categorias) {
                destino.getCategoriaDAO().agregar(c);
            }

            System.out.println("Migrando productos...");
            List<Producto> productos = origen.getProductoDAO().listarTodos();
            for (Producto p : productos) {
                destino.getProductoDAO().agregar(p);
            }

            System.out.println("Migrando movimientos...");
            List<MovimientoStock> movs = origen.getMovimientoStockDAO().listarTodos();
            for (MovimientoStock m : movs) {
                destino.getMovimientoStockDAO().agregar(m);
            }

            conn.commit();
            System.out.println("✓ Migración completada");

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            throw new RuntimeException("Error en migración", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) {}
        }
    }

}
