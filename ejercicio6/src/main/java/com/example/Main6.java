package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
                        "('Caja PC Mid Tower', NULL, 49.99, 12, NULL)"+" ON CONFLICT (nombre) DO NOTHING;");

                ResultSet resultset = sentecia.executeQuery("SELECT * FROM productos");




                try {
                    if (resultset.first()) {

                        int id = resultset.getInt("id");
                       String nombre =  resultset.getString("nombre");
                       double precio =  resultset.getDouble("precio");

                       System.out.println("[FIRST] Primer producto:");
                       System.out.printf("%-2d %-10s %.2f\n", id , nombre, precio);

                    }
                } catch (Exception e) {
                    
                    e.printStackTrace();
                }

                try {
                    if (resultset.previous()) {
                           int id = resultset.getInt("id");
                       String nombre =  resultset.getString("nombre");
                       double precio =  resultset.getDouble("precio");

                       System.out.println("[FIRST] Primer producto:");
                       System.out.printf("%-2d %-10s %.2f\n", id , nombre, precio);

                    }
                } catch (Exception e) {
                    // TODO Auto-gene mintrated catch block
                    e.printStackTrace();
                }
                try {
                    if (resultset.last()) {
                           int id = resultset.getInt("id");
                       String nombre =  resultset.getString("nombre");
                       double precio =  resultset.getDouble("precio");

                       System.out.println("[FIRST] Primer producto:");
                       System.out.printf("%-2d %-10s %.2f\n", id , nombre, precio);

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    if (resultset.absolute(5)) {
                           int id = resultset.getInt("id");
                       String nombre =  resultset.getString("nombre");
                       double precio =  resultset.getDouble("precio");

                       System.out.println("[FIRST] Primer producto:");
                       System.out.printf("%-2d %-10s %.2f\n", id , nombre, precio);

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    if (resultset.relative(2)) {
                           int id = resultset.getInt("id");
                       String nombre =  resultset.getString("nombre");
                       double precio =  resultset.getDouble("precio");

                       System.out.println("[FIRST] Primer producto:");
                       System.out.printf("%-2d %-10s %.2f\n", id , nombre, precio);

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    if (resultset.relative(-3)) {
                           int id = resultset.getInt("id");
                       String nombre =  resultset.getString("nombre");
                       double precio =  resultset.getDouble("precio");

                       System.out.println("[FIRST] Primer producto:");
                       System.out.printf("%-2d %-10s %.2f\n", id , nombre, precio);

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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