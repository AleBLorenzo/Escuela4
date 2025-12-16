package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main3 {
    public static void main(String[] args) {

        String Direccion = "biblioteca.db";

        Conexion(Direccion);
    }

    private static void Conexion( String Direccion) {
        try {
            Class.forName("org.sqlite.JDBC");

            

            System.out.println("Intentando conectar a: " + Direccion);
            try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + Direccion)) {

                System.out.println("Conexión establecida correctamente");

            } catch (SQLException ex) {

                System.out.println("\nError al conectar con la base de datos");
                System.out.print("Detalle: [");
                System.getLogger(Main3.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                System.out.print("]");

            }

            System.out.println("\nConexión cerrada correctamente");

        } catch (ClassNotFoundException ex) {
            System.getLogger(Main3.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}