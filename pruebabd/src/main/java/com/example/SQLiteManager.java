package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum SQLiteManager {

    INSTANCE; // Java garantiza que solo existe UNA instancia

    private Connection connection;

    SQLiteManager() {
        // Constructor se llama EXACTAMENTE una vez
    }

    public synchronized Connection getConnection() {

        try {

            if (connection == null) {

                connection = DriverManager.getConnection("jdbc:sqlite:app.db");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;

    }

    @SuppressWarnings("CallToPrintStackTrace")

    public synchronized void close() {

        try {

            if (connection != null) {

                connection.close();

                System.out.println("Conexión cerrada");

            }

        } catch (SQLException ex) {

        }

        connection = null;

    }

    public synchronized boolean isConnected() throws SQLException {

        if (connection != null || connection.isClosed()) {

            System.out.println("Existe la conexión");

            return true;
        } else {
            System.out.println("No existe la conexión");

            return false;
        }

    }


    public synchronized void ensureConnection() throws SQLException {

        if (connection != null) {

            System.out.println("Está conectado");

        } else if (connection == null || connection.isClosed()) {

            connection = DriverManager.getConnection("jdbc:sqlite:app.db");

            System.out.println("Se creó una nueva conexión");
        }

    }
}
