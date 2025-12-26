package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main2 {
    public static void main(String[] args) {

        String dirrecion = "jdbc:postgresql://100.87.253.157:5432/instituto";
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

                Monstrarinfo(user, conexion);

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

                    System.getLogger(Main2.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }

            System.out.println("Conexión cerrada correctamente");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void Monstrarinfo(String user, final Connection conexion) throws SQLException {

        try {

            System.out.println("Usuario: " + user);
            System.out.println("Conexión establecida exitosamente");
            System.out.println("Versión de PostgreSQL: " + conexion.getMetaData().getDatabaseProductName() + " "
                    + conexion.getMetaData().getDatabaseProductVersion());
            System.out.println("Base de datos: " + conexion.getCatalog());

        } catch (SQLException e) {

            e.printStackTrace();
        }
        
    }
}