package com.example.app;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

import com.example.dao.AutorDAOImpl;
import com.example.model.Autor;
import com.example.util.ConexionDB;

public class AplicacionBiblioteca {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = ConexionDB.getInstance().getConnection();

            while (true) {

                System.out.println("╔════════════════════════════════════════╗\n" + //
                        "║   SISTEMA DE GESTIÓN DE BIBLIOTECA     ║\n" + //
                        "╚════════════════════════════════════════╝");

                System.out.println("Menú Principal:\n" + //
                        "  1. Gestión de Autores\n" + //
                        "  2. Gestión de Libros\n" + //
                        "  3. Gestión de Préstamos\n" + //
                        "  4. Consultas y Reportes\n" + //
                        "  5. Salir");

                System.out.println("Seleccione una opción:");
                int opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:

                        try {
                            AutorDAOImpl autorimple = new AutorDAOImpl(conn);

                            System.out.println("Menú Autores:\n" + //
                                    "  1. Insertar autores\n" + //
                                    "  2. Buscar autor por ID\n" + //
                                    "  3. Listar todos los autores\n" + //
                                    "  4. Actualizar autor\n" + //
                                    "  5. Eliminar autor\n" + //
                                    "  6. Buscar autor por nacionalidad\n" + //
                                    "  7. Buscar autor por fecha de nacimiento");

                            int opcionautores = sc.nextInt();
                            sc.nextLine();

                            switch (opcionautores) {
                                case 1:

                                    System.out.println("Introduzca los datos:");

                                    System.out.print("nombre: ");
                                    String nombre = sc.nextLine();

                                    System.out.print("apellidos: ");
                                    String apellidos = sc.nextLine();

                                    System.out.print("nacionalidad: ");
                                    String nacionalidad = sc.nextLine();

                                    System.out.print("fecha_nacimiento: ");
                                    String fecha_nacimiento = sc.nextLine();

                                    LocalDate fecha = LocalDate.parse(fecha_nacimiento);

                                    Autor autor = new Autor(apellidos, fecha, opcionautores, nacionalidad, nombre);

                                    autorimple.insertar(autor);

                                    break;

                                case 2:
                                    System.out.println("Introduzca los datos:");

                                    System.out.print("Id: ");
                                    int id = sc.nextInt();

                                    Autor autordevuelto = autorimple.buscarPorId(id);

                                    System.out.println(autordevuelto.toString());
                                    break;

                                case 3:

                                    break;

                                case 4:

                                    break;

                                case 5:

                                    break;

                                case 6:

                                    break;

                                case 7:

                                    break;
                                default:
                                    throw new AssertionError();
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        break;
                    case 2:

                        break;
                    case 3:

                        break;

                    case 4:

                        break;

                    case 5:

                        return;

                    default:
                        throw new AssertionError();
                }
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}