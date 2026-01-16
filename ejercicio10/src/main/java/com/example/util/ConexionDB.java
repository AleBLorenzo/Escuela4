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

    private ConexionDB() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("/home/manana/Escritorio/Escuela4/ejercicio10/config.properties"));

            sgbd = props.getProperty("sgbd.actual").toLowerCase();

            String url;
            String user = "";
            String pass = "";
            String driver;

            switch (sgbd) {
                case "sqlite":
                    driver = "org.sqlite.JDBC";
                    url = "jdbc:sqlite:" + props.getProperty("sqlite.ruta");
                    break;

                case "postgresql":
                    driver = "org.postgresql.Driver";
                    url = "jdbc:postgresql://" +
                            props.getProperty("postgresql.host") + ":" +
                            props.getProperty("postgresql.port") + "/" +
                            props.getProperty("postgresql.database");
                    user = props.getProperty("postgresql.user");
                    pass = props.getProperty("postgresql.password");
                    break;

                case "mysql":
                    driver = "com.mysql.cj.jdbc.Driver";
                    url = "jdbc:mysql://" +
                            props.getProperty("mysql.host") + ":" +
                            props.getProperty("mysql.port") + "/" +
                            props.getProperty("mysql.database") +
                            "?useSSL=false&serverTimezone=UTC";
                    user = props.getProperty("mysql.user");
                    pass = props.getProperty("mysql.password");
                    break;

                default:
                    throw new RuntimeException("SGBD no soportado: " + sgbd);
            }

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            connection.setAutoCommit(true); //autocommit activado

            System.out.println("Conectado a " + sgbd + " correctamente (autocommit activado)");

        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
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
        return connection;
    }

    public void cerrar() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSGBD() {
        return sgbd;
    }
}