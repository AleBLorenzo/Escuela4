package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcionPrincipal;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE EMPLEADOS ===");
            System.out.println("1. Gestión de departamentos");
            System.out.println("2. Gestión de empleados");
            System.out.println("3. Consultas y estadísticas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcionPrincipal = sc.nextInt();

            switch (opcionPrincipal) {
                case 1 -> menuDepartamentos(sc);
                case 2 -> menuEmpleados(sc);
                case 3 -> menuConsultas(sc);
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcionPrincipal != 0);

        sc.close();
    }

    // ===============================
    // MENÚ DEPARTAMENTOS
    // ===============================
    private static void menuDepartamentos(Scanner sc) {
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

            switch (opcion) {
                case 1 -> System.out.println("Crear departamento");
                /*
                 * - Solicitar: nombre, código, ubicación, presupuesto
                 * - Validar código único
                 * - Validar presupuesto >= 0
                 * - Guardar con fecha actual
                 */
                case 2 -> System.out.println("Listar departamentos");
                /*
                 * - Mostrar todos los departamentos ordenados por nombre
                 * - Indicar cantidad de empleados en cada uno
                 * - Formato claro y legible
                 */
                case 3 -> System.out.println("Buscar departamento");
                /*
                 * - Buscar departamento por código único
                 * - Mostrar información completa
                 * - Mostrar lista de empleados del departamento
                 */

                case 4 -> System.out.println("Actualizar departamento");
                /*
                 * - Permitir modificar nombre, ubicación, presupuesto
                 * - No permitir cambiar código (unique constraint)
                 * - Mantener empleados asociados
                 */
                case 5 -> System.out.println("Eliminar departamento");
                /*
                 * - Verificar si tiene empleados
                 * - Si tiene empleados, preguntar qué hacer:
                 * - Opción A: Reasignar empleados a otro departamento
                 * - Opción B: Dejar empleados sin departamento
                 * - Opción C: Cancelar eliminación
                 * - Confirmar antes de eliminar
                 */
                case 6 -> System.out.println("Ver empleados del departamento");
                /*
                 * - Listar todos los empleados del departamento
                 * - Mostrar información resumida de cada empleado
                 * - Calcular estadísticas: total empleados, salario promedio
                 */
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }

    // ===============================
    // MENÚ EMPLEADOS
    // ===============================
    private static void menuEmpleados(Scanner sc) {
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

            switch (opcion) {
                case 1 -> System.out.println("Añadir empleado");
                /*
                 * - Solicitar datos del empleado
                 * - Opcionalmente asignar a un departamento
                 * - Validar email único
                 * - Validar salario > 0
                 */
                case 2 -> System.out.println("Listar empleados");

                /*
                 * - Mostrar todos los empleados activos
                 * - Incluir nombre del departamento
                 * - Ordenar por apellidos
                 */
                case 3 -> System.out.println("Buscar empleado por email");
                /*
                 * - Buscar empleado por email
                 * - Mostrar información completa incluyendo departamento
                 */
                case 4 -> System.out.println("Actualizar empleado");
                /*
                 * - Permitir cambiar datos personales
                 * - NO permitir cambiar departamento (usar opción 12)
                 * - Actualizar salario
                 */
                case 5 -> System.out.println("Eliminar empleado");
                /*
                 * - Marcar como no activo (soft delete)
                 * - O eliminar físicamente según configuración
                 * - Confirmar acción
                 */
                case 6 -> System.out.println("Reasignar empleado");
                /*
                 * - Buscar empleado por email o ID
                 * - Mostrar departamento actual
                 * - Solicitar nuevo departamento
                 * - Actualizar relación manteniendo sincronía
                 */
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }

    // ===============================
    // MENÚ CONSULTAS Y ESTADÍSTICAS
    // ===============================
    private static void menuConsultas(Scanner sc) {
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

            switch (opcion) {
                case 1 -> System.out.println("Empleados sin departamento");
                case 2 -> System.out.println("Departamentos con más empleados");
                case 3 -> System.out.println("Salario promedio por departamento");
                case 4 -> System.out.println("Empleados con salario superior a X");
                case 5 -> System.out.println("Conteo de empleados por departamento");
                case 6 -> System.out.println("Empleados por rango de fechas");
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }
}