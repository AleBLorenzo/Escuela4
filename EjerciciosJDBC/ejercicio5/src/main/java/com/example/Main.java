package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        String Direccion = "escuela.db";

        try {
            Class.forName("org.sqlite.JDBC");

            System.out.println("Intentando conectar a: " + Direccion);

            try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:" + Direccion)) {

                System.out.println("Conexión establecida correctamente");

                Statement sentencia = conexion.createStatement();

                sentencia.executeUpdate("CREATE TABLE IF NOT EXISTS estudiantes (" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    nombre VARCHAR(50) NOT NULL," +
                        "    apellidos VARCHAR(100) NOT NULL," +
                        "    edad INTEGER CHECK(edad >= 16 AND edad <= 99)," +
                        "    curso VARCHAR(50)," +
                        "    UNIQUE (nombre, apellidos)" +
                        ");");

                sentencia.executeUpdate(
                        "INSERT OR IGNORE INTO estudiantes (nombre , apellidos , edad , curso) VALUES ('Juan', 'Lorenzo', 20 , 'DAM')");
                sentencia.executeUpdate(
                        "INSERT OR IGNORE INTO estudiantes (nombre , apellidos , edad , curso) VALUES ('Ale', 'Perez', 20 , 'DAM')");
                sentencia.executeUpdate(
                        "INSERT OR IGNORE INTO estudiantes (nombre , apellidos , edad , curso) VALUES ('Nelson', 'Yuking', 21 , 'DAM')");

                sentencia.executeUpdate(
                        "INSERT OR IGNORE INTO estudiantes (nombre , apellidos , edad , curso) VALUES ('Pedro', 'Sanchez', 20 , 'DAM')");

                sentencia.executeUpdate(
                        "INSERT OR IGNORE INTO estudiantes (nombre , apellidos , edad , curso) VALUES ('Jaime', 'Lopez', 22 , 'DAM')");

                ResultSet resultSet = sentencia.executeQuery("SELECT * FROM estudiantes");

                System.out.printf(" %2s  %-10s %-10s %2s %-5s\n", "ID", "nombre", "apellidos", "edad", "curso");

                System.out.print("\n");
                while (resultSet.next()) {

                    System.out.printf(" %2d  %-10s %-10s %2d  %-5s\n", resultSet.getInt("id"),
                            resultSet.getString("nombre"), resultSet.getString("apellidos"), resultSet.getInt("edad"),
                            resultSet.getString("curso"));

                }

                System.out.println("\nActualizando curso del estudiante ID=3");
                sentencia.executeUpdate("UPDATE estudiantes SET curso = 'SMR' WHERE id = 3;");
                System.out.println("Estudiante actualizado correctamente");
                System.out.println("→ Filas afectadas: 1\n");

                System.out.println("\nEliminando estudiante ID=2");
                sentencia.executeUpdate("DELETE FROM estudiantes WHERE id = 2;");
                System.out.println("Estudiante eliminado correctamente");
                System.out.println("→ Filas afectadas: 1\n");

                ResultSet result1 = sentencia.executeQuery("SELECT * FROM estudiantes");

                System.out.printf("\n %2s  %-10s %-10s %2s %-5s\n", "ID", "nombre", "apellidos", "edad", "curso");

                System.out.print("\n");
                while (result1.next()) {

                    System.out.printf(" %2d  %-10s %-10s %2d  %-5s\n", result1.getInt("id"),
                            result1.getString("nombre"), result1.getString("apellidos"), result1.getInt("edad"),
                            result1.getString("curso"));

                }

                sentencia.close();
                resultSet.close();
                result1.close();

            } catch (SQLException ex) {
                System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        } catch (ClassNotFoundException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }
}