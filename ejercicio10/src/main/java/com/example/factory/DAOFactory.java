package com.example.factory;

import com.example.dao.CategoriaDAO;
import com.example.dao.CategoriaDAOImpl;
import com.example.dao.MovimientoStockDAO;
import com.example.dao.MovimientoStockDAOImpl;
import com.example.dao.ProductoDAO;
import com.example.dao.ProductoDAOImpl;
import com.example.util.ConexionDB;

public class DAOFactory {


    private static DAOFactory instance;
    private ConexionDB conexion;

    private DAOFactory() {
        // Usamos la conexión singleton que ya maneja autocommit
        this.conexion = ConexionDB.getInstance();
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

    // Devuelve DAO de Categoría
    public CategoriaDAO getCategoriaDAO() {
        // Podemos agregar lógica si queremos DAOs distintos por SGBD
        return new CategoriaDAOImpl();
    }

    // Devuelve DAO de Producto
    public ProductoDAO getProductoDAO() {
        return new ProductoDAOImpl();
    }

    // Devuelve DAO de MovimientoStock
    public MovimientoStockDAO getMovimientoStockDAO() {
        return new MovimientoStockDAOImpl();
    }

}
