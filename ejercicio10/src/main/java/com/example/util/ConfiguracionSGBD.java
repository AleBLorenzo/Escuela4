package com.example.util;

import java.sql.Connection;
import java.sql.Statement;

public class ConfiguracionSGBD {
    public static void crearTablas() {
        String sgbd = ConexionDB.getInstance().getSGBD();
        Connection conn = ConexionDB.getInstance().getConnection();

        try (Statement st = conn.createStatement()) {

            switch (sgbd) {
                case "sqlite" -> crearSQLite(st);
                case "postgresql" -> crearPostgreSQL(st);
                case "mysql" -> crearMySQL(st);
                default -> throw new RuntimeException("SGBD no soportado");
            }

            System.out.println("✓ Tablas creadas para " + sgbd);

        } catch (Exception e) {
            throw new RuntimeException("Error creando tablas", e);
        }
    }

    private static void crearSQLite(Statement st) throws Exception {
        st.execute("""
                    CREATE TABLE IF NOT EXISTS categorias (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT NOT NULL,
                        descripcion TEXT
                    );
                """);

        st.execute("""
                    CREATE TABLE IF NOT EXISTS productos (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        codigo TEXT UNIQUE NOT NULL,
                        nombre TEXT NOT NULL,
                        categoria_id INTEGER,
                        precio REAL NOT NULL,
                        stock INTEGER DEFAULT 0,
                        fecha_alta TEXT,
                        activo INTEGER DEFAULT 1,
                        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
                    );
                """);

        st.execute("""
                    CREATE TABLE IF NOT EXISTS movimientos_stock (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        producto_id INTEGER,
                        tipo TEXT,
                        cantidad INTEGER,
                        motivo TEXT,
                        fecha TEXT,
                        FOREIGN KEY (producto_id) REFERENCES productos(id)
                    );
                """);
    }

    private static void crearPostgreSQL(Statement st) throws Exception {
        st.execute("""
                    CREATE TABLE IF NOT EXISTS categorias (
                        id SERIAL PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion TEXT
                    );
                """);

        st.execute("""
                    CREATE TABLE IF NOT EXISTS productos (
                        id SERIAL PRIMARY KEY,
                        codigo VARCHAR(50) UNIQUE NOT NULL,
                        nombre VARCHAR(200) NOT NULL,
                        categoria_id INTEGER REFERENCES categorias(id),
                        precio DECIMAL(10,2) NOT NULL,
                        stock INTEGER DEFAULT 0,
                        fecha_alta DATE,
                        activo BOOLEAN DEFAULT TRUE
                    );
                """);

        st.execute("""
                    CREATE TABLE IF NOT EXISTS movimientos_stock (
                        id SERIAL PRIMARY KEY,
                        producto_id INTEGER REFERENCES productos(id),
                        tipo VARCHAR(20),
                        cantidad INTEGER,
                        motivo VARCHAR(200),
                        fecha TIMESTAMP
                    );
                """);
    }

    private static void crearMySQL(Statement st) throws Exception {
        st.execute("""
                    CREATE TABLE IF NOT EXISTS categorias (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion TEXT
                    );
                """);

        st.execute("""
                    CREATE TABLE IF NOT EXISTS productos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        codigo VARCHAR(50) UNIQUE NOT NULL,
                        nombre VARCHAR(200) NOT NULL,
                        categoria_id INT,
                        precio DECIMAL(10,2) NOT NULL,
                        stock INT DEFAULT 0,
                        fecha_alta DATE,
                        activo BOOLEAN DEFAULT TRUE,
                        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
                    );
                """);

        st.execute("""
                    CREATE TABLE IF NOT EXISTS movimientos_stock (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        producto_id INT,
                        tipo VARCHAR(20),
                        cantidad INT,
                        motivo VARCHAR(200),
                        fecha DATETIME,
                        FOREIGN KEY (producto_id) REFERENCES productos(id)
                    );
                """);
    }
}
