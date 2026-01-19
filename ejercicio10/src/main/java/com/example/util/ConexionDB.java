package com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static ConexionDB instance;
    private Connection connection;
    private String sgbd;
    private Properties props;

    private ConexionDB() {
        cargarConfiguracion();
        conectar(this.props.getProperty("sgbd.actual")); // Conectar por defecto
    }

    private void cargarConfiguracion() {
        try {
            props = new Properties();
            props.load(new FileInputStream("ejercicio10/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar config.properties", e);
        }
    }

    // Método para conectar a un SGBD específico (usado para cambiar en tiempo de
    // ejecución)
    public void conectar(String sgbdObjetivo) {
        this.sgbd = sgbdObjetivo.toLowerCase();

        // Cerrar conexión anterior si existe
        cerrar();

        try {
            String url = "";
            String user = "";
            String pass = "";
            String driver = "";

            switch (sgbd) {
                case "sqlite":
                    driver = "org.sqlite.JDBC";
                    url = "jdbc:sqlite:" + props.getProperty("sqlite.ruta");
                    break;
                case "postgresql":
                    driver = "org.postgresql.Driver";
                    url = "jdbc:postgresql://" + props.getProperty("postgresql.host") + ":" +
                            props.getProperty("postgresql.port") + "/" + props.getProperty("postgresql.database");
                    user = props.getProperty("postgresql.user");
                    pass = props.getProperty("postgresql.password");
                    break;
                case "mysql":
                    driver = "com.mysql.cj.jdbc.Driver";
                    url = "jdbc:mysql://" + props.getProperty("mysql.host") + ":" +
                            props.getProperty("mysql.port") + "/" + props.getProperty("mysql.database") +
                            "?useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
                    user = props.getProperty("mysql.user");
                    pass = props.getProperty("mysql.password");
                    break;
                default:
                    throw new RuntimeException("SGBD no soportado: " + sgbd);
            }

            Class.forName(driver);
            if (sgbd.equals("sqlite")) {
                connection = DriverManager.getConnection(url);
            } else {
                connection = DriverManager.getConnection(url, user, pass);
            }
            connection.setAutoCommit(true);
            System.out.println(">>> Conectado a: " + sgbd);

        } catch (Exception e) {
            throw new RuntimeException("Error conectando a " + sgbd, e);
        }
    }

    public static ConexionDB getInstance() {
        if (instance == null) {
            synchronized (ConexionDB.class) {
                if (instance == null) {
                    instance = new ConexionDB();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                conectar(this.sgbd); // Reconectar si se cayó
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void cerrar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            /* Ignorar */ }
    }

    public String getSGBD() {
        return sgbd;
    }
}