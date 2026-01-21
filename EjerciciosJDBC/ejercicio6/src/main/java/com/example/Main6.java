package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main6 {
    public static void main(String[] args) {
        String dirrecion = "jdbc:postgresql://100.87.253.157:5432/tienda";
        String user = "admin";
        String pass = "admin123";

        Conexion(dirrecion, user, pass);

    }

    @SuppressWarnings("CallToPrintStackTrace")

    private static void Conexion(String dirrecion, String user, String pass) {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("\nConectando a: " + dirrecion);

            try (Connection conexion = DriverManager.getConnection(dirrecion, user, pass)) {

                // ===============================================
                // Nota sobre ResultSet:
                // Usamos TYPE_SCROLL_INSENSITIVE y CONCUR_READ_ONLY para permitir navegación
                // completa:
                // - first(), last(), next(), previous(), absolute(), relative()
                // A diferencia de un ResultSet forward-only (por defecto),
                // que solo permite avanzar hacia adelante con next() y no permite ir atrás ni
                // saltar posiciones.
                // Esto es útil para interfaces que necesitan mostrar filas arbitrarias o
                // paginación.
                // ===============================================

                Statement sentecia = conexion.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, // Scrollable
                        ResultSet.CONCUR_READ_ONLY // Solo lectura
                );

                sentecia.executeUpdate("CREATE TABLE IF NOT EXISTS productos (\n" + //
                        "    id SERIAL PRIMARY KEY,\n" + //
                        "    nombre VARCHAR(100) NOT NULL,\n" + //
                        "    categoria VARCHAR(50),\n" + //
                        "    precio DECIMAL(10,2),\n" + //
                        "    stock INTEGER,\n" + //
                        "    descripcion TEXT,\n" + //
                        "     UNIQUE (nombre)\n" + //
                        ");");

                sentecia.executeUpdate("INSERT INTO productos (nombre, categoria, precio, stock, descripcion) VALUES\n"
                        + //
                        "('Ratón Gaming', 'Periféricos', 29.99, 50, 'Ratón con DPI ajustable y luces RGB'),\n" + //
                        "('Teclado Mecánico', 'Periféricos', 79.50, 30, 'Teclado con switches azules, retroiluminado'),\n"
                        + //
                        "('Monitor 24\"', 'Monitores', 149.99, 20, 'Monitor Full HD de 24 pulgadas'),\n" + //
                        "('Auriculares Bluetooth', 'Audio', 59.90, 40, 'Auriculares inalámbricos con cancelación de ruido'),\n"
                        + //
                        "('SSD 500GB', 'Almacenamiento', 89.99, 15, 'Unidad de estado sólido, velocidad 550MB/s'),\n" + //
                        "('Memoria RAM 16GB', NULL, 74.99, 25, 'DDR4, 3200 MHz'),\n" + //
                        "('Fuente de Alimentación 600W', 'Componentes', 65.50, 10, NULL),\n" + //
                        "('Placa Base ATX', 'Componentes', 120.00, 5, 'Compatible con procesadores Intel y AMD'),\n" + //
                        "('Procesador Intel i5', 'Procesadores', 199.99, 8, '10ª generación, 6 núcleos'),\n" + //
                        "('Caja PC Mid Tower', NULL, 49.99, 12, NULL)" + " ON CONFLICT (nombre) DO NOTHING;");

                ResultSet resultset = sentecia.executeQuery("SELECT * FROM productos");

                System.out.println("\n=== METADATOS DEL RESULTSET ===");

                ResultSetMetaData meta = resultset.getMetaData();

                int columnas = meta.getColumnCount();
                System.out.println("Número de columnas: " + columnas);

                for (int i = 1; i <= columnas; i++) {
                    System.out.println(
                            "Columna " + i + ": " + meta.getColumnName(i) + " (" + meta.getColumnTypeName(i) + ")");
                }

                if (resultset.first()) {
                    System.out.println(
                            "Fila actual: " + resultset.getRow() + " | Producto: " + resultset.getString("nombre"));
                }

                if (resultset.next()) {
                    System.out.println(
                            "Fila actual: " + resultset.getRow() + " | Producto: " + resultset.getString("nombre"));
                }

                if (resultset.last()) {
                    System.out.println(
                            "Fila actual: " + resultset.getRow() + " | Producto: " + resultset.getString("nombre"));
                }

                if (resultset.absolute(5)) {
                    System.out.println("Fila actual: " + resultset.getRow() + " | Producto absoluto 5: "
                            + resultset.getString("nombre"));
                }

                if (resultset.relative(2)) {
                    System.out.println("Fila actual: " + resultset.getRow() + " | Producto relativo +2: "
                            + resultset.getString("nombre"));
                }

                if (resultset.relative(-3)) {
                    System.out.println("Fila actual: " + resultset.getRow() + " | Producto relativo -3: "
                            + resultset.getString("nombre"));
                }

            } catch (SQLException ex) {

                if (ex.getMessage().contains("conexión")) {
                    System.out.println("No se pudo conectar al servidor PostgreSQL");
                    System.out.println("Causa: ");
                    ex.printStackTrace();

                } else if (ex.getMessage().contains("authentication")) {
                    System.out.println("Error de autenticación");
                    System.out.println("Verifica el usuario y contraseña");
                    System.out.println("Causa: ");
                    ex.printStackTrace();

                } else {

                    System.getLogger(Main6.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }

            System.out.println("Conexión cerrada correctamente");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}