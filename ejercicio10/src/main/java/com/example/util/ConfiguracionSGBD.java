package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfiguracionSGBD {
    public static void main(String[] args) {
        
        // La base de datos será un archivo "inventario.db" en la raíz del proyecto
        String url = "jdbc:sqlite:inventario.db";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            // Crear tablas
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS categorias (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nombre TEXT NOT NULL,
                            descripcion TEXT
                        );
                    """);

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS productos (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            codigo TEXT UNIQUE NOT NULL,
                            nombre TEXT NOT NULL,
                            categoria_id INTEGER REFERENCES categorias(id),
                            precio REAL NOT NULL,
                            stock INTEGER NOT NULL DEFAULT 0,
                            fecha_alta TEXT,
                            activo INTEGER DEFAULT 1
                        );
                    """);

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS movimientos_stock (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            producto_id INTEGER REFERENCES productos(id),
                            tipo TEXT,
                            cantidad INTEGER NOT NULL,
                            motivo TEXT,
                            fecha TEXT
                        );
                    """);

            System.out.println("Base de datos SQLite creada con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }
}
