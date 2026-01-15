package com.example.app;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.example.dao.AutorDAOImpl;
import com.example.dao.LibroDAOImpl;
import com.example.dao.PrestamoDAOImpl;
import com.example.model.Autor;
import com.example.model.Libro;
import com.example.model.Prestamo;
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

                                    List<Autor> autores = autorimple.listarTodos();

                                    if (autores.isEmpty()) {
                                        System.out.println("No hay autores registrados.");
                                    } else {
                                        autores.forEach(System.out::println);
                                    }
                                    break;

                                case 4:

                                    System.out.print("Id del autor a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Autor autorActualizar = autorimple.buscarPorId(idActualizar);

                                    if (autorActualizar == null) {
                                        System.out.println("Autor no encontrado.");
                                        break;
                                    }

                                    System.out.print("Nuevo nombre: ");
                                    autorActualizar.setNombre(sc.nextLine());

                                    System.out.print("Nuevos apellidos: ");
                                    autorActualizar.setApellidos(sc.nextLine());

                                    System.out.print("Nueva nacionalidad: ");
                                    autorActualizar.setNacionalidad(sc.nextLine());

                                    System.out.print("Nueva fecha nacimiento (YYYY-MM-DD): ");
                                    autorActualizar.setFecha_nacimiento(LocalDate.parse(sc.nextLine()));

                                    autorimple.actualizar(autorActualizar);
                                    System.out.println("Autor actualizado correctamente.");
                                    break;

                                case 5:

                                    System.out.print("Id del autor a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    autorimple.eliminar(idEliminar);
                                    System.out.println("Autor eliminado.");

                                    break;

                                case 6:

                                    System.out.print("Nacionalidad: ");
                                    String nac = sc.nextLine();

                                    List<Autor> autoresNac = autorimple.buscarPorNacionalidad(nac);

                                    if (autoresNac.isEmpty()) {
                                        System.out.println("No se encontraron autores.");
                                    } else {
                                        autoresNac.forEach(System.out::println);
                                    }

                                    break;

                                case 7:

                                    System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
                                    LocalDate fechaBuscar = LocalDate.parse(sc.nextLine());

                                    List<Autor> autoresFecha = autorimple.buscarFechaNacimiento(fechaBuscar);

                                    if (autoresFecha.isEmpty()) {
                                        System.out.println("No se encontraron autores.");
                                    } else {
                                        autoresFecha.forEach(System.out::println);
                                    }
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
                        try {
                            LibroDAOImpl libroDAO = new LibroDAOImpl(conn);

                            System.out.println("Menú Libros:\n" +
                                    "  1. Insertar libro\n" +
                                    "  2. Buscar libro por ISBN\n" +
                                    "  3. Listar todos los libros\n" +
                                    "  4. Actualizar libro\n" +
                                    "  5. Eliminar libro\n" +
                                    "  6. Buscar libros por autor\n" +
                                    "  7. Listar libros disponibles");

                            int opcionLibros = sc.nextInt();
                            sc.nextLine();

                            switch (opcionLibros) {

                                case 1: // INSERTAR
                                    Libro libro = new Libro();

                                    System.out.print("Título: ");
                                    libro.setTitulo(sc.nextLine());

                                    System.out.print("ISBN: ");
                                    libro.setIsbn(sc.nextLine());

                                    System.out.print("ID Autor: ");
                                    libro.setAutorId(sc.nextInt());

                                    sc.nextLine();
                                    System.out.print("Género: ");
                                    libro.setGenero(sc.nextLine());

                                    System.out.print("Año publicación: ");
                                    libro.setAnioPublicacion(sc.nextInt());

                                    libro.setDisponible(true);

                                    libroDAO.insertar(libro);
                                    System.out.println("Libro insertado correctamente.");
                                    break;

                                case 2: // BUSCAR ISBN
                                    System.out.print("ISBN: ");
                                    String isbn = sc.nextLine();

                                    Libro l = libroDAO.buscarPorISBN(isbn);
                                    System.out.println(l != null ? l : "Libro no encontrado.");
                                    break;

                                case 3: // LISTAR TODOS
                                    libroDAO.listarTodos().forEach(System.out::println);
                                    break;

                                case 4: // ACTUALIZAR
                                    System.out.print("ISBN del libro a actualizar: ");
                                    String isbnActualizar = sc.nextLine();

                                    Libro libroActualizar = libroDAO.buscarPorISBN(isbnActualizar);
                                    if (libroActualizar == null) {
                                        System.out.println("Libro no encontrado.");
                                        break;
                                    }

                                    System.out.print("Nuevo título: ");
                                    libroActualizar.setTitulo(sc.nextLine());

                                    System.out.print("Nuevo ID Autor: ");
                                    libroActualizar.setAutorId(sc.nextInt());
                                    sc.nextLine();

                                    System.out.print("Nuevo género: ");
                                    libroActualizar.setGenero(sc.nextLine());

                                    System.out.print("Nuevo año publicación: ");
                                    libroActualizar.setAnioPublicacion(sc.nextInt());

                                    System.out.print("¿Disponible? (true/false): ");
                                    libroActualizar.setDisponible(sc.nextBoolean());

                                    libroDAO.actualizar(libroActualizar);
                                    System.out.println("Libro actualizado.");
                                    break;

                                case 5: // ELIMINAR
                                    System.out.print("ID del libro: ");
                                    libroDAO.eliminar(sc.nextInt());
                                    System.out.println("Libro eliminado.");
                                    break;

                                case 6: // BUSCAR POR AUTOR
                                    System.out.print("ID Autor: ");
                                    libroDAO.buscarPorAutor(sc.nextInt()).forEach(System.out::println);
                                    break;

                                case 7: // DISPONIBLES
                                    libroDAO.buscarDisponibles().forEach(System.out::println);
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            PrestamoDAOImpl prestamoDAO = new PrestamoDAOImpl(conn);

                            System.out.println("Menú Préstamos:\n" +
                                    "  1. Registrar préstamo\n" +
                                    "  2. Registrar devolución\n" +
                                    "  3. Listar préstamos activos\n" +
                                    "  4. Listar historial de préstamos\n" +
                                    "  5. Buscar préstamos por usuario");

                            int opcionPrestamo = sc.nextInt();
                            sc.nextLine();

                            switch (opcionPrestamo) {

                                case 1: // REGISTRAR PRÉSTAMO
                                    Prestamo p = new Prestamo();

                                    System.out.print("ID Libro: ");
                                    p.setLibro_id(sc.nextInt());
                                    sc.nextLine();

                                    System.out.print("Nombre usuario: ");
                                    p.setNombre_usuario(sc.nextLine());

                                    prestamoDAO.registrarPrestamo(p);
                                    break;

                                case 2: // DEVOLUCIÓN
                                    System.out.print("ID del préstamo: ");
                                    prestamoDAO.registrarDevolucion(sc.nextInt());
                                    System.out.println("Devolución registrada.");
                                    break;

                                case 3: // ACTIVOS
                                    prestamoDAO.listarPrestamosActivos().forEach(System.out::println);
                                    break;

                                case 4: // HISTORIAL
                                    prestamoDAO.listarHistorialPrestamos().forEach(System.out::println);
                                    break;

                                case 5: // POR USUARIO
                                    System.out.print("Nombre usuario: ");
                                    prestamoDAO.buscarPrestamosPorUsuario(sc.nextLine())
                                            .forEach(System.out::println);
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            LibroDAOImpl libroDAO = new LibroDAOImpl(conn);
                            PrestamoDAOImpl prestamoDAO = new PrestamoDAOImpl(conn);

                            System.out.println("Consultas y Reportes:\n" +
                                    "  1. Libros más prestados\n" +
                                    "  2. Libros por autor\n" +
                                    "  3. Préstamos pendientes de devolución\n" +
                                    "  4. Estadísticas generales");

                            int opcionReporte = sc.nextInt();
                            sc.nextLine();

                            switch (opcionReporte) {

                                case 1: // 📚 LIBROS MÁS PRESTADOS (simple)
                                    prestamoDAO.listarTodos()
                                            .forEach(System.out::println);
                                    break;

                                case 2: // ✍️ LIBROS POR AUTOR
                                    System.out.print("ID Autor: ");
                                    int idAutor = sc.nextInt();

                                    libroDAO.buscarPorAutor(idAutor)
                                            .forEach(System.out::println);
                                    break;

                                case 3: // 🔁 PRÉSTAMOS PENDIENTES
                                    prestamoDAO.listarPrestamosActivos()
                                            .forEach(System.out::println);
                                    break;

                                case 4: // 📊 ESTADÍSTICAS GENERALES (básico)
                                    System.out.println("Total libros: " + libroDAO.listarTodos().size());
                                    System.out.println("Total préstamos: " + prestamoDAO.listarTodos().size());
                                    System.out.println(
                                            "Préstamos activos: " + prestamoDAO.listarPrestamosActivos().size());
                                    break;

                                default:
                                    System.out.println("Opción no válida.");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case 5:
                        System.out.println("Saliendo del sistema...");
                        sc.close();
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