package com.example.rendimiento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.ProductoDAO;
import com.example.factory.DAOFactory;
import com.example.model.Producto;

public class MedidorRendimiento {

    private DAOFactory factory;

    public MedidorRendimiento(DAOFactory factory) {
        this.factory = factory;
    }

    // Mide tiempo de inserciones masivas
    public long medirInserciones(int cantidad) {
        ProductoDAO productoDAO = factory.getProductoDAO();

        List<Producto> productos = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            productos.add(new Producto(
                null,
                "COD" + i,
                "Producto " + i,
                1L,
                new BigDecimal("10.0"),
                100,
                LocalDate.now(),
                true
            ));
        }

        long inicio = System.nanoTime();

        for (Producto p : productos) {
            productoDAO.agregar(p);
        }

        long fin = System.nanoTime();
        return fin - inicio; // tiempo en nanosegundos
    }

    // Mide tiempo de consultas complejas
    public long medirConsultas() {
        ProductoDAO productoDAO = factory.getProductoDAO();
        long inicio = System.nanoTime();

        // Ejemplo de consulta: listar todos los productos
        productoDAO.listarTodos();

        long fin = System.nanoTime();
        return fin - inicio;
    }

    // Mide tiempo de actualizaciones masivas
    public long medirActualizaciones(int cantidad) {
        ProductoDAO productoDAO = factory.getProductoDAO();

        long inicio = System.nanoTime();

        for (int i = 1; i <= cantidad; i++) {
            Producto p = productoDAO.obtenerPorId(i);
            if (p != null) {
                p.setPrecio(p.getPrecio().add(new BigDecimal("1.0")));
                productoDAO.actualizar(p);
            }
        }

        long fin = System.nanoTime();
        return fin - inicio;
    }

}
