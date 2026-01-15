package com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionDB1 {

    private static ConexionDB1 instance; // Singleton
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(ConexionDB1.class.getName());
    private String url;
    private String usuario;
    private String password;
    private String driver;

        // Private constructor: carga propiedades y establece conexión
        private ConexionDB1() {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream("/home/manana/Escritorio/Escuela4/ejercicio9/db.properties"));

                driver = props.getProperty("db.driver");
                url = props.getProperty("db.url");
                usuario = props.getProperty("db.user");
                password = props.getProperty("db.password");

                Class.forName(driver);
                connection = DriverManager.getConnection(url, usuario, password);
                connection.setAutoCommit(false); // control de transacciones manual
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error al conectar a la base de datos", ex);
                throw new RuntimeException(ex);
            }
        }

    // Método para obtener la instancia Singleton
    public static ConexionDB1 getInstance() {
        if (instance == null) {
            synchronized (ConexionDB1.class) {
                if (instance == null) {
                    instance = new ConexionDB1();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Métodos de commit y rollback
    public void commit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error en commit", ex);
        }
    }

    public void rollback() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error en rollback", ex);
        }
    }

    // Cierre de la conexión
    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al cerrar conexión", ex);
        }
    }

    // Ejecutar consulta SELECT
    public ResultSet ejecutarConsulta(String consulta) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(consulta);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al ejecutar consulta", ex);
            return null;
        }
    }

    // Ejecutar INSERT, UPDATE, DELETE
    public int ejecutarInstruccion(String instruccion, boolean commit) {
        int filas = 0;
        try (Statement stmt = connection.createStatement()) {
            filas = stmt.executeUpdate(instruccion);
            if (commit) {
                commit();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al ejecutar instrucción", ex);
        }
        return filas;
    }

    // Método para comprobar existencia de valor
    public boolean existeValor(String valor, String columna, String tabla) {
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE UPPER(%s) = UPPER(?)", tabla, columna);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, valor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) >= 1;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error en existeValor", ex);
        }
        return false;
    }
}