package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");

            String Direccion = "jdbc:sqlite:biblioteca.db";

            System.out.println("Intentando conectar a: " + Direccion);
            try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + Direccion)) {

                System.out.println("Conexión establecida correctamente");

            } catch (SQLException ex) {

                System.out.println("\nError al conectar con la base de datos");
                System.out.print("Detalle: [");
                System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                System.out.print("]");

            }

            System.out.println("\nConexión cerrada correctamente");

        } catch (ClassNotFoundException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}