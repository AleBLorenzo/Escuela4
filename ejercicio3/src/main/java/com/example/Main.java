package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
Diferecia con el postgresql es su importacion cambia la clase al hacerlo 
a la hora de conectarlo es igual con el tema de el usuario y la contraseña
encuanto a la optencion de los datos es igual  diferecia en postgresql tengo q especificar
a la base de datos que me vot a conetar mientras q en mysql no tiene q ser aswi igual se coneta
como puedes ver en urlnativo donde se coneta igual sin la base de datos especificada
*/
public class Main {

    public static void main(String[] args) {

        String user = "usuario";
        String password = "usuario123";
        String urlnativo = "jdbc:mysql://100.87.253.15:3306/";
        String urlNoTimezone = "jdbc:mysql://100.87.253.157:3306/academia";
        String url = urlNoTimezone+"?serverTimezone=UTC";

        System.out.println("=== CONEXIÓN A MYSQL ===");
        System.out.println("URL: " + url);
        System.out.println("Usuario: " + user);

        establecerconexion(urlnativo, user, password);

    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void establecerconexion(String url, String user, String password) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(url, user, password)) {

                System.out.println("Conexión establecida exitosamente");

                Mostrarinfo(conexion);

            } catch (SQLException e) {

                if (e.getMessage().contains("Access denied")) {

                    System.out.println("Error de autenticación en MySQL");
                    System.out.println("Access denied for user ");
                    e.printStackTrace();

                } else {

                    System.out.println("No se pudo conectar al servidor MySQL");
                    System.out.println("Causa: ");

                    System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);

                }

            }

            System.out.println("Conexión cerrada correctamente");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    private static void Mostrarinfo(final Connection conexion) throws SQLException {

        try {

            System.out.println("Versión MySQL: "+ conexion.getMetaData().getDatabaseProductName() + " " + conexion.getMetaData().getDatabaseProductVersion());
            System.out.println("Base de datos actual: " + conexion.getCatalog());
            System.out.println("Usuario conectado: " + conexion.getMetaData().getUserName());

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }
}