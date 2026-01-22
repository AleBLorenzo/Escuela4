package com.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.example.model.Libro;
import com.example.service.BibliotecaService;

public class Main {
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    BibliotecaService servicio = new BibliotecaService();

    while (true) {

      System.out.println("=== GESTIÓN DE BIBLIOTECA ===\n" + //
          "1. Añadir nuevo libro\n" + //
          "2. Buscar libro por ISBN\n" + //
          "3. Buscar libro por ID\n" + //
          "4. Listar todos los libros\n" + //
          "5. Listar libros disponibles\n" + //
          "6. Buscar libros por autor\n" + //
          "7. Eliminar libro\n" + //
          "8. Actualizar libro\n" + //
          "9. Estadísticas\n" + //
          "0. Salir\n" + //
          "\n" + //
          "Seleccione una opción:");

      int opción = sc.nextInt();
      sc.nextLine();

      switch (opción) {
        case 1:

          System.out.println("=== Añadir nuevo libro ===");

          System.out.print("ISBN: ");
          String isbn = sc.nextLine();

          System.out.print("Título: ");
          String titulo = sc.nextLine();

          System.out.print("Autor: ");
          String autor = sc.nextLine();

          System.out.print("Editorial: ");
          String editorial = sc.nextLine();

          System.out.print("Año de publicación: ");
          int anio = sc.nextInt();
          sc.nextLine();

          System.out.print("Precio: ");
          double precio = sc.nextDouble();
          sc.nextLine();

          System.out.print("¿Disponible? (S/N): ");
          String dispInput = sc.nextLine();

          boolean disponible = false;

          if (dispInput.equalsIgnoreCase("S")) {
            disponible = true;
          }

          Libro libro = new Libro(anio, autor, disponible, editorial, LocalDate.now(), isbn, precio, titulo);

          try {
            servicio.guardar(libro);

          } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
          }
          break;

        case 2:
          System.out.println("=== Buscar libro por ISBN ===");

          System.out.print("ISBN: ");
          String isbnb = sc.nextLine();

          Libro libroibs = servicio.buscarPorIsbn(isbnb);

          System.out.println(libroibs.toString());

          break;
        case 3:

          System.out.println("=== Buscar libro por ID ===");

          System.out.print("ID: ");
          long id = sc.nextLong();

          Libro libroid = servicio.buscarPorId(id);

          System.out.println(libroid.toString());

          break;
        case 4:

          System.out.println("=== Listar todos los libros ===");

          List<Libro> listat = servicio.listarTodos();

          if (listat != null && !listat.isEmpty()) {
            for (Libro librol : listat) {
              System.out.println(librol.toString());
            }
          } else {
            System.out.println("No hay libros registrados o ocurrió un error.");
          }
          break;

        case 5:

          System.out.println("=== Listar libros disponibles ===");

          List<Libro> listad = servicio.listarDisponibles();

          for (Libro librod : listad) {

            System.out.println(librod.toString());
          }

          break;
        case 6:
          System.out.println("=== Buscar libros por autor ===");

          System.out.print("Autor: ");
          String autorl = sc.nextLine();

          List<Libro> listaa = servicio.buscarPorAutor(autorl);

          for (Libro libroa : listaa) {

            System.out.println(libroa.toString());
          }

          break;
        case 7:

          System.out.println("=== Eliminar libro ===");

          System.out.print("Introduzca el ISBN del libro a eliminar: ");
          String isbnDel = sc.nextLine();

          Libro libroElim = servicio.buscarPorIsbn(isbnDel);

          if (libroElim == null) {
            System.out.println("No se ha encontrado el libro con ese ISBN.");
          } else {
            System.out.println("Datos del libro a eliminar:");
            System.out.println(libroElim.toString());

            System.out.print("\n¿Está seguro de que desea eliminar este libro? (S/N): ");
            String confirmaElim = sc.nextLine();

            if (confirmaElim.equalsIgnoreCase("S")) {
              try {
                servicio.eliminar(libroElim.getId());
                System.out.println("Libro eliminado con éxito.");
              } catch (Exception e) {
                System.out.println("Error al eliminar el libro: " + e.getMessage());
              }
            } else {
              System.out.println("Operación cancelada.");
            }
          }

          break;
        case 8:
          System.out.println("=== Actualizar libro ===");

          System.out.print("ISBN: ");
          String isbnba = sc.nextLine();

          Libro libroibsa = servicio.buscarPorIsbn(isbnba);

          if (libroibsa == null) {
            System.out.println("No se ha encontrado el libro.");
          } else {
            System.out.println("Datos Actuales");
            System.out.println(libroibsa.toString());

            System.out.println("Introduzca los nuevos datos (ENTER para saltar): ");

            System.out.print("ISBN: ");
            String isbnn = sc.nextLine();
            if (!isbnn.trim().isEmpty()) {
              libroibsa.setIsbn(isbnn);
            }

            System.out.print("Título: ");
            String titulon = sc.nextLine();
            if (!titulon.trim().isEmpty()) {
              libroibsa.setTitulo(titulon);
            }

            System.out.print("Autor: ");
            String autorn = sc.nextLine();
            if (!autorn.trim().isEmpty()) {
              libroibsa.setAutor(autorn);
            }

            System.out.print("Editorial: ");
            String editorialn = sc.nextLine();
            if (!editorialn.trim().isEmpty()) {
              libroibsa.setEditorial(editorialn);
            }

            System.out.print("Año de publicación: ");
            String entradaAnio = sc.nextLine();

            if (!entradaAnio.trim().isEmpty()) {

              try {
                int anioLeido = Integer.parseInt(entradaAnio);
                libroibsa.setAnio(anioLeido);

              } catch (NumberFormatException e) {
                System.out.println("Año no válido, se mantendrá el anterior.");
              }
            }

            System.out.print("Precio: ");
            String entradaPrecio = sc.nextLine();
            if (!entradaPrecio.trim().isEmpty()) {
              double precioLeido = Double.parseDouble(entradaPrecio);
              libroibsa.setPrecio(precioLeido);
            }

            System.out.print("¿Disponible? (S/N): ");
            String entradaDisp = sc.nextLine();
            if (!entradaDisp.trim().isEmpty()) {
              libroibsa.setDisponible(entradaDisp.equalsIgnoreCase("S"));
            }

            System.out.println("\nResumen de cambios:");
            System.out.println(libroibsa.toString());

            System.out.print("\n¿Confirmar los cambios? (S/N): ");
            String confirmar = sc.nextLine();

            if (confirmar.equalsIgnoreCase("S")) {
              try {
                servicio.actualizar(libroibsa);
                System.out.println("Actualización guardada correctamente.");
              } catch (Exception e) {
                System.out.println("Error al guardar: " + e.getMessage());
              }
            } else {
              System.out.println("Actualización cancelada por el usuario.");
            }
          }
          break;
        case 9:

          System.out.println("=== Estadísticas ===");

          long total = servicio.contarTotal();

          if (total == 0) {
            System.out.println("No hay libros en la biblioteca.");
          } else {
            long disp = servicio.contarDisponibles();
            long noDisp = total - disp;
            Libro caro = servicio.libroMasCaro();
            Libro barato = servicio.libroMasBarato();

            System.out.println("Total libros: " + total);
            System.out.println("Disponibles: " + disp);
            System.out.println("No disponibles: " + noDisp);
            System.out.printf("Precio medio: %.2f€\n", servicio.precioPromedio());

            if (caro != null)
              System.out.println("Más caro: " + caro.getTitulo() + " (" + caro.getPrecio() + "€)");
            if (barato != null)
              System.out.println("Más barato: " + barato.getTitulo() + " (" + barato.getPrecio() + "€)");
          }
          break;
        case 0:

          System.out.println("Saliendo del sistema...");
          sc.close();

          System.exit(0);

          break;
        default:

          System.out.println("⚠️ Opción no reconocida. Intente de nuevo.");
          break;

      }

    }

  }
}