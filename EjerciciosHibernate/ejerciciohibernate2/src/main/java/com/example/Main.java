package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.example.model.Departamento;
import com.example.model.Empleado;
import com.example.service.EmpresaService;

public class Main {

    private static final EmpresaService service = new EmpresaService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcionPrincipal;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE EMPLEADOS ===");
            System.out.println("1. Gestión de departamentos");
            System.out.println("2. Gestión de empleados");
            System.out.println("3. Consultas y estadísticas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcionPrincipal = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcionPrincipal) {
                case 1 -> menuDepartamentos();
                case 2 -> menuEmpleados();
                case 3 -> menuConsultas();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcionPrincipal != 0);

        sc.close();
    }

    // ===============================
    // MENÚ DEPARTAMENTOS
    // ===============================
    private static void menuDepartamentos() {
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE DEPARTAMENTOS ---");
            System.out.println("1. Crear nuevo departamento");
            System.out.println("2. Listar todos los departamentos");
            System.out.println("3. Buscar departamento por código");
            System.out.println("4. Actualizar departamento");
            System.out.println("5. Eliminar departamento");
            System.out.println("6. Ver empleados de un departamento");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearDepartamento();
                case 2 -> listarDepartamentos();
                case 3 -> buscarDepartamento();
                case 4 -> actualizarDepartamento();
                case 5 -> eliminarDepartamento();
                case 6 -> verEmpleadosDepartamento();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }

    private static void crearDepartamento() {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Código: ");
            String codigo = sc.nextLine();
            System.out.print("Ubicación: ");
            String ubicacion = sc.nextLine();
            System.out.print("Presupuesto: ");
            double presupuesto = sc.nextDouble();
            sc.nextLine();

            Departamento departamento = new Departamento();
            departamento.setNombre(nombre);
            departamento.setCodigo(codigo);
            departamento.setUbicacion(ubicacion);
            departamento.setPresupuesto(presupuesto);

            service.crearDepartamento(departamento);
        } catch (Exception e) {
            System.out.println("Error al crear departamento: " + e.getMessage());
        }
    }

    private static void listarDepartamentos() {
        List<Departamento> lista = service.listarTodosDepartamento();
        if (!lista.isEmpty()) {
            lista.forEach(d -> {
                System.out.println("ID: " + d.getId() + " | Código: " + d.getCodigo() +
                        " | Nombre: " + d.getNombre() + " | Presupuesto: " + d.getPresupuesto());
            });
        }
    }

    private static void buscarDepartamento() {
        System.out.print("Ingrese código del departamento: ");
        String codigo = sc.nextLine();
        Departamento d = service.buscarPorCodigo(codigo);
        if (d != null) {
            System.out.println("ID: " + d.getId() + " | Código: " + d.getCodigo() +
                    " | Nombre: " + d.getNombre() + " | Presupuesto: " + d.getPresupuesto());
            List<Empleado> empleados = service.listarEmpleadosDeDepartamento(d);
            System.out.println("Empleados del departamento:");
            if (empleados != null && !empleados.isEmpty()) {
                empleados.forEach(e -> System.out.println(" - " + e.getNombre() + " " + e.getApellidos()));
            } else {
                System.out.println("No hay empleados.");
            }
        }
    }

    private static void actualizarDepartamento() {
        System.out.print("Ingrese código del departamento a actualizar: ");
        String codigo = sc.nextLine();
        Departamento d = service.buscarPorCodigo(codigo);
        if (d != null) {
            System.out.print("Nuevo nombre (" + d.getNombre() + "): ");
            String nombre = sc.nextLine();
            if (!nombre.isEmpty())
                d.setNombre(nombre);

            System.out.print("Nueva ubicación (" + d.getUbicacion() + "): ");
            String ubicacion = sc.nextLine();
            if (!ubicacion.isEmpty())
                d.setUbicacion(ubicacion);

            System.out.print("Nuevo presupuesto (" + d.getPresupuesto() + "): ");
            String pres = sc.nextLine();
            if (!pres.isEmpty())
                d.setPresupuesto(Double.parseDouble(pres));

            service.actualizarDepartamento(d);
        }
    }

    private static void eliminarDepartamento() {
        System.out.print("Ingrese código del departamento a eliminar: ");
        String codigo = sc.nextLine();
        Departamento d = service.buscarPorCodigo(codigo);
        if (d != null) {
            List<Empleado> empleados = service.listarEmpleadosDeDepartamento(d);
            if (empleados != null && !empleados.isEmpty()) {
                System.out.println("El departamento tiene empleados. Qué desea hacer?");
                System.out.println("1. Reasignar a otro departamento");
                System.out.println("2. Dejar empleados sin departamento");
                System.out.println("3. Cancelar eliminación");
                int op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1 -> {
                        System.out.print("Ingrese código del departamento destino: ");
                        String codDestino = sc.nextLine();
                        Departamento destino = service.buscarPorCodigo(codDestino);
                        if (destino != null) {
                            empleados.forEach(e -> service.reasignarDepartamento(e, destino.getId()));
                        }
                    }
                    case 2 -> empleados.forEach(e -> service.reasignarDepartamento(e, null));
                    case 3 -> {
                        System.out.println("Eliminación cancelada.");
                        return;
                    }
                }
            }
            service.eliminarDepartamento(codigo);
        }
    }

    private static void verEmpleadosDepartamento() {
        System.out.print("Ingrese código del departamento: ");
        String codigo = sc.nextLine();
        Departamento d = service.buscarPorCodigo(codigo);
        if (d != null) {
            List<Empleado> empleados = service.listarEmpleadosDeDepartamento(d);
            if (empleados != null && !empleados.isEmpty()) {
                System.out.println("Empleados:");
                empleados.forEach(e -> System.out.println(" - " + e.getNombre() + " " + e.getApellidos() +
                        " | Salario: " + e.getSalario()));
            } else {
                System.out.println("No hay empleados en este departamento.");
            }
        }
    }

    // ===============================
    // MENÚ EMPLEADOS
    // ===============================
    private static void menuEmpleados() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
            System.out.println("1. Añadir nuevo empleado");
            System.out.println("2. Listar todos los empleados");
            System.out.println("3. Buscar empleado por email");
            System.out.println("4. Actualizar empleado");
            System.out.println("5. Eliminar empleado");
            System.out.println("6. Reasignar empleado a otro departamento");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> anadirEmpleado();
                case 2 -> listarEmpleados();
                case 3 -> buscarEmpleado();
                case 4 -> actualizarEmpleado();
                case 5 -> eliminarEmpleado();
                case 6 -> reasignarEmpleado();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    private static void anadirEmpleado() {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Salario: ");
            double salario = sc.nextDouble();
            sc.nextLine();

            Empleado empleado = new Empleado();
            empleado.setNombre(nombre);
            empleado.setApellidos(apellidos);
            empleado.setEmail(email);
            empleado.setSalario(salario);

            // Inicializar campos obligatorios
            empleado.setActivo(true); // necesario para listar
            empleado.setFechaContratacion(LocalDate.now()); // necesario para persistencia

            // Asignar departamento si se indicó

            System.out.print("¿Asignar a departamento? (código o dejar vacío): ");

            String codDep = sc.nextLine();

            if (!codDep.isEmpty()) {
                Departamento d = service.buscarPorCodigo(codDep);
                
                if (d != null) {
                    empleado.setDepartamento(d);
                } else {
                    System.out.println("Código de departamento no encontrado, empleado sin departamento.");
                }
            }

            service.anadirEmpleado(empleado);
        } catch (Exception e) {
            System.out.println("Error al añadir empleado: " + e.getMessage());
        }
    }

    private static void listarEmpleados() {
        List<Empleado> lista = service.listarTodosEmpleados();
        if (!lista.isEmpty()) {
            lista.forEach(e -> System.out.println("ID: " + e.getId() + " | Nombre: " + e.getNombre() +
                    " | Apellidos: " + e.getApellidos() + " | Email: " + e.getEmail() +
                    " | Departamento: "
                    + (e.getDepartamento() != null ? e.getDepartamento().getNombre() : "Sin departamento")));
        }
    }

    private static void buscarEmpleado() {
        System.out.print("Ingrese email del empleado: ");
        String email = sc.nextLine();
        Empleado e = service.buscarPorEmail(email);
        if (e != null) {
            System.out.println("ID: " + e.getId() + " | Nombre: " + e.getNombre() +
                    " | Apellidos: " + e.getApellidos() + " | Salario: " + e.getSalario() +
                    " | Departamento: "
                    + (e.getDepartamento() != null ? e.getDepartamento().getNombre() : "Sin departamento"));
        }
    }

    private static void actualizarEmpleado() {
        System.out.print("Ingrese email del empleado a actualizar: ");
        String email = sc.nextLine();
        Empleado e = service.buscarPorEmail(email);
        if (e != null) {
            System.out.print("Nuevo nombre (" + e.getNombre() + "): ");
            String nombre = sc.nextLine();
            if (!nombre.isEmpty())
                e.setNombre(nombre);

            System.out.print("Nuevos apellidos (" + e.getApellidos() + "): ");
            String apellidos = sc.nextLine();
            if (!apellidos.isEmpty())
                e.setApellidos(apellidos);

            System.out.print("Nuevo salario (" + e.getSalario() + "): ");
            String s = sc.nextLine();
            if (!s.isEmpty())
                e.setSalario(Double.parseDouble(s));

            service.actualizarEmpleados(e);
        }
    }

    private static void eliminarEmpleado() {
        System.out.print("Ingrese email del empleado a eliminar: ");
        String email = sc.nextLine();
        service.eliminarEmpelados(email);
    }

    private static void reasignarEmpleado() {
        System.out.print("Ingrese email del empleado: ");
        String email = sc.nextLine();
        Empleado e = service.buscarPorEmail(email);
        if (e != null) {
            System.out.print("Nuevo código de departamento (vacío para sin departamento): ");
            String codDep = sc.nextLine();
            Long idDep = null;
            if (!codDep.isEmpty()) {
                Departamento d = service.buscarPorCodigo(codDep);
                if (d != null)
                    idDep = d.getId();
            }
            service.reasignarDepartamento(e, idDep);
        }
    }

    // ===============================
    // MENÚ CONSULTAS Y ESTADÍSTICAS
    // ===============================
    private static void menuConsultas() {
        int opcion;
        do {
            System.out.println("\n--- CONSULTAS Y ESTADÍSTICAS ---");
            System.out.println("1. Listar empleados sin departamento");
            System.out.println("2. Departamentos con más empleados");
            System.out.println("3. Salario promedio por departamento");
            System.out.println("4. Empleados con salario superior a X");
            System.out.println("5. Contar empleados por departamento");
            System.out.println("6. Empleados contratados en un rango de fechas");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> listarSinDepartamento();
                case 2 -> departamentoConMasEmpleados();
                case 3 -> salarioPromedioPorDepartamento();
                case 4 -> empleadosConSalarioMayorQue();
                case 5 -> contarEmpleadosPorDepartamento();
                case 6 -> empleadosContratadosEntre();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }

    private static void listarSinDepartamento() {
        List<Empleado> lista = service.listarSinDepartamento();
        if (lista != null && !lista.isEmpty()) {
            lista.forEach(e -> System.out.println(e.getNombre() + " " + e.getApellidos() + " | " + e.getEmail()));
        } else {
            System.out.println("No hay empleados sin departamento.");
        }
    }

    private static void departamentoConMasEmpleados() {
        Departamento d = service.DepartamentoConMasEmpleados();
        if (d != null) {
            System.out.println("Departamento con más empleados: " + d.getNombre() + " (Código: " + d.getCodigo() + ")");
        } else {
            System.out.println("No hay datos disponibles.");
        }
    }

    private static void salarioPromedioPorDepartamento() {
        List<Object[]> resultados = service.salarioPromedioPorDepartamento();
        if (resultados != null) {
            resultados.forEach(r -> System.out.println("Departamento: " + r[0] + " | Salario promedio: " + r[1]));
        }
    }

    private static void empleadosConSalarioMayorQue() {
        System.out.print("Ingrese salario mínimo: ");
        double salario = sc.nextDouble();
        sc.nextLine();
        List<Empleado> lista = service.listarEmpleadosConSalarioMayorQue(salario);
        if (lista != null && !lista.isEmpty()) {
            lista.forEach(
                    e -> System.out.println(e.getNombre() + " " + e.getApellidos() + " | Salario: " + e.getSalario()));
        }
    }

    private static void contarEmpleadosPorDepartamento() {
        List<Object[]> resultados = service.contarEmpleadosPorDepartamento();
        if (resultados != null) {
            resultados.forEach(r -> System.out.println("Departamento: " + r[0] + " | Cantidad empleados: " + r[1]));
        }
    }

    private static void empleadosContratadosEntre() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.print("Fecha inicio (yyyy-MM-dd): ");
            LocalDate inicio = LocalDate.parse(sc.nextLine(), dtf);
            System.out.print("Fecha fin (yyyy-MM-dd): ");
            LocalDate fin = LocalDate.parse(sc.nextLine(), dtf);

            List<Empleado> lista = service.listarEmpleadosContratadosEntre(inicio, fin);
            if (lista != null && !lista.isEmpty()) {
                lista.forEach(e -> System.out.println(
                        e.getNombre() + " " + e.getApellidos() + " | Contratado: " + e.getFechaContratacion()));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar empleados: " + e.getMessage());
        }
    }
}