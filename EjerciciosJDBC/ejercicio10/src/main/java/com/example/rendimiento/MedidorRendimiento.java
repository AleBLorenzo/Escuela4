package com.example.rendimiento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.factory.DAOFactory;
import com.example.model.Producto;

public class MedidorRendimiento {

    private DAOFactory factory;

    public MedidorRendimiento(DAOFactory factory) {
        this.factory = factory;
    }

    public long medirInserciones(int cantidad) {
        // --- PASO 1: PREPARACIÓN (Fuera del tiempo de medición) ---
        // Insertamos una categoría base para que los productos tengan a dónde apuntar
        try {
            com.example.model.Categoria cat = new com.example.model.Categoria();
            cat.setNombre("Categoría de Prueba");
            cat.setDescripcion("Base para test de rendimiento");

            // El DAO se encarga de insertarla.
            // Si usas un ID fijo (1), asegúrate de que el DAO no falle si ya existe.
            factory.getCategoriaDAO().agregar(cat);
            // Importante: Obtenemos el ID que la base de datos le asignó realmente
            long idGenerado = cat.getId();

            // Preparar la lista de productos en memoria
            List<Producto> lote = new ArrayList<>();
            long ts = System.currentTimeMillis();
            for (int i = 0; i < cantidad; i++) {
                lote.add(new Producto(null, "C-" + ts + "-" + i, "Prod " + i,
                        idGenerado, new BigDecimal("10.0"), 100,
                        LocalDate.now(), true));
            }

            // --- PASO 2: MEDICIÓN REAL ---
            long inicio = System.nanoTime();
            factory.getProductoDAO().insertarLote(lote); // Método que usa Batch
            long fin = System.nanoTime();

            return fin - inicio;

        } catch (Exception e) {
            System.err.println("Error crítico en la medición: " + e.getMessage());
            return 0;
        }
    }

    public long medirConsultas() {
        long inicio = System.nanoTime();
        // Usamos la consulta compleja con JOINs y GROUP BY, no listarTodos simple
        factory.getProductoDAO().consultaCompleja();
        long fin = System.nanoTime();
        return fin - inicio;
    }

    public long medirActualizaciones(int cantidad) {
        // Primero obtenemos los IDs reales para no fallar
        List<Producto> productos = factory.getProductoDAO().listarTodos();
        int limite = Math.min(cantidad, productos.size());

        long inicio = System.nanoTime();

        // Aquí sí hacemos un bucle porque queremos probar la latencia de updates
        // individuales
        // (A menos que implementes updateBatch también)
        for (int i = 0; i < limite; i++) {
            Producto p = productos.get(i);
            p.setPrecio(p.getPrecio().add(BigDecimal.ONE));
            factory.getProductoDAO().actualizar(p);
        }

        long fin = System.nanoTime();
        return fin - inicio;
    }
}
