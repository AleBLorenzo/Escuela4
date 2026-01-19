package com.example.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.dao.CategoriaDAO;
import com.example.dao.CategoriaDAOImpl;
import com.example.dao.MovimientoStockDAO;
import com.example.dao.MovimientoStockDAOImpl;
import com.example.dao.ProductoDAO;
import com.example.dao.ProductoDAOImpl;
import com.example.util.ConexionDB;

public class DAOFactory {
    private static DAOFactory instance;

    // ELIMINAMOS los campos 'conexion' y 'conn' de aquí.
    // No queremos cachear la conexión porque cambia dinámicamente.

    private DAOFactory() {
        // Constructor vacío
    }

    // Singleton
    public static DAOFactory getInstance() {
        if (instance == null) {
            synchronized (DAOFactory.class) {
                if (instance == null) {
                    instance = new DAOFactory();
                }
            }
        }
        return instance;
    }

    public CategoriaDAO getCategoriaDAO() {
        return new CategoriaDAOImpl();
    }

    public ProductoDAO getProductoDAO() {
        return new ProductoDAOImpl();
    }

    public MovimientoStockDAO getMovimientoStockDAO() {
        return new MovimientoStockDAOImpl();
    }

    // CORRECCIÓN CRÍTICA AQUÍ:
    public void limpiarTablas() {
        // Pedimos la conexión ACTUAL a la clase ConexionDB en el momento de ejecutar
        Connection conn = ConexionDB.getInstance().getConnection();

        try (Statement st = conn.createStatement()) {
            // El orden importa por las claves foráneas (FK)
            st.execute("DELETE FROM movimientos_stock");
            st.execute("DELETE FROM productos");
            st.execute("DELETE FROM categorias");
            // Reiniciar contadores autoincrementales (opcional, sintaxis varía por BD)
            // System.out.println("✓ Tablas limpiadas correctamente");
        } catch (SQLException e) {
            throw new RuntimeException("Error limpiando tablas", e);
        }
    }

}
