package com.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.example.model.Aula;
import com.example.model.Curso;
import com.example.model.Estudiante;
import com.example.model.Modalidad;
import com.example.model.NivelCurso;
import com.example.model.Profesor;
import com.example.model.RolProfesor;
import com.example.model.TipoEvaluacion;
import com.example.service.AcademiaService;
import com.example.service.ReporteService;

public class Main {
    public static void main(String[] args) {
        AcademiaService academiaService = new AcademiaService();
        ReporteService reporteService = new ReporteService();

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            try {
                System.out.print("Selecciona una opción: ");
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    // ================= CURSOS =================
                    case 1:
                        crearCurso(sc, academiaService);
                        break;
                    case 2:
                        listarCursos(academiaService);
                        break;
                    case 3:
                        buscarCursoPorCodigo(sc, academiaService);
                        break;
                    case 4:
                        actualizarCurso(sc, academiaService);
                        break;
                    case 5:
                        asignarAulaACurso(sc, academiaService);
                        break;
                    case 6:
                        verMatriculasCurso(sc, academiaService);
                        break;
                    case 7:
                        verProfesoresCurso(sc, academiaService);
                        break;

                    // ================= ESTUDIANTES =================
                    case 8:
                        registrarEstudiante(sc, academiaService);
                        break;
                    case 9:
                        matricularEstudiante(sc, academiaService);
                        break;
                    case 10:
                        darBajaMatricula(sc, academiaService);
                        break;
                    case 11:
                        verCursosDeEstudiante(sc, academiaService);
                        break;
                    case 12:
                        actualizarCalificacionFinal(sc, academiaService);
                        break;
                    case 13:
                        registrarEvaluacion(sc, academiaService);
                        break;

                    // ================= PROFESORES =================
                    case 14:
                        registrarProfesor(sc, academiaService);
                        break;
                    case 15:
                        asignarProfesorACurso(sc, academiaService);
                        break;
                    case 16:
                        verCursosDeProfesor(sc, academiaService);
                        break;
                    case 17:
                        calcularCargaHorariaProfesor(sc, academiaService);
                        break;

                    // ================= AULAS =================
                    case 18:
                        crearAula(sc, academiaService);
                        break;
                    case 19:
                        listarAulas(academiaService);
                        break;
                    case 20:
                        verOcupacionAulas(sc, academiaService);
                        break;

                    // ================= REPORTES =================
                    case 21:
                        reportarCursosMasMatriculados(reporteService);
                        break;
                    case 22:
                        reportarEstudiantesMejorPromedio(reporteService);
                        break;
                    case 23:
                        reportarCursosPorCompletarEsteMes(reporteService);
                        break;
                    case 24:
                        reportarIngresosTotalesPorCurso(reporteService);
                        break;
                    case 25:
                        reportarProfesoresMayorCarga(reporteService);
                        break;
                    case 26:
                        reportarTasaAbandonoPorCurso(reporteService);
                        break;
                    case 27:
                        reportarEstudiantesSinCursosActivos(reporteService);
                        break;
                    case 28:
                        listarCertificados(reporteService);
                        break;

                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("=== SISTEMA DE GESTIÓN DE ACADEMIA ===");
        System.out.println("\nGESTIÓN DE CURSOS\n");
        System.out.println("1. Crear curso");
        System.out.println("2. Listar todos los cursos");
        System.out.println("3. Buscar curso por código");
        System.out.println("4. Actualizar curso");
        System.out.println("5. Asignar aula a curso");
        System.out.println("6. Ver matrículas de un curso");
        System.out.println("7. Ver profesores de un curso");
        System.out.println("\nGESTIÓN DE ESTUDIANTES\n");
        System.out.println("8. Registrar estudiante");
        System.out.println("9. Matricular estudiante en curso");
        System.out.println("10. Dar de baja matrícula");
        System.out.println("11. Ver cursos de un estudiante");
        System.out.println("12. Actualizar calificación final");
        System.out.println("13. Registrar evaluación");
        System.out.println("\nGESTIÓN DE PROFESORES\n");
        System.out.println("14. Registrar profesor");
        System.out.println("15. Asignar profesor a curso");
        System.out.println("16. Ver cursos de un profesor");
        System.out.println("17. Calcular carga horaria de profesor");
        System.out.println("\nGESTIÓN DE AULAS\n");
        System.out.println("18. Crear aula");
        System.out.println("19. Listar aulas disponibles");
        System.out.println("20. Ver ocupación de aulas");
        System.out.println("\nREPORTES Y ESTADÍSTICAS\n");
        System.out.println("21. Cursos con más matriculados");
        System.out.println("22. Estudiantes con mejor promedio");
        System.out.println("23. Cursos por completar este mes");
        System.out.println("24. Ingresos totales por curso");
        System.out.println("25. Profesores con mayor carga horaria");
        System.out.println("26. Tasa de abandono por curso");
        System.out.println("27. Estudiantes sin cursos activos");
        System.out.println("28. Listado de certificados a emitir");
        System.out.println("\n0. Salir");
    }

    // ===================== MÉTODOS DE CURSOS =====================
    private static void crearCurso(Scanner sc, AcademiaService service) {
        try {
            System.out.print("Código (formato CUR-XXX): ");
            String codigo = sc.nextLine().trim();

            // Validar formato antes de llamar al service
            if (!codigo.matches("CUR-\\d{3}")) {
                System.out.println("ERROR: El código debe tener el formato CUR-XXX");
                return;
            }
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();

            System.out.print("Duración horas: ");
            int duracion = Integer.parseInt(sc.nextLine());

            System.out.print("Precio: ");
            double precio = Double.parseDouble(sc.nextLine());

            System.out.print("Nivel (BASICO, INTERMEDIO, AVANZADO, EXPERTO): ");
            NivelCurso nivel = NivelCurso.valueOf(sc.nextLine().toUpperCase());

            System.out.print("Modalidad (PRESENCIAL, ONLINE, HIBRIDA): ");
            Modalidad modalidad = Modalidad.valueOf(sc.nextLine().toUpperCase());

            System.out.print("Max estudiantes: ");
            int maxEst = Integer.parseInt(sc.nextLine());

            System.out.print("Fecha inicio (yyyy-MM-dd): ");
            LocalDate inicio = LocalDate.parse(sc.nextLine());

            System.out.print("Fecha fin (yyyy-MM-dd): ");

            LocalDate fin = LocalDate.parse(sc.nextLine());

            Curso c = new Curso();
            c.setCodigo(codigo);
            c.setNombre(nombre);
            c.setDescripcion(descripcion);
            c.setDuracionHoras(duracion);
            c.setPrecio(precio);
            c.setNivel(nivel);
            c.setModalidad(modalidad);
            c.setMaxEstudiantes(maxEst);
            c.setFechaInicio(inicio);
            c.setFechaFin(fin);

            service.crearCurso(c);
            System.out.println("Curso creado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al crear curso: " + e.getMessage());
        }
    }

    private static void listarCursos(AcademiaService service) {
        List<Curso> cursos = service.listarCursos();
        for (Curso c : cursos) {
            System.out.println(c.getCodigo() + " - " + c.getNombre() + " - " + c.getNivel());
        }
    }

    private static void buscarCursoPorCodigo(Scanner sc, AcademiaService service) {
        System.out.print("Código del curso: ");
        String codigo = sc.nextLine();
        Curso c = service.buscarCursoPorCodigo(codigo);
        if (c != null) {
            System.out.println("Curso: " + c.getNombre() + " | Nivel: " + c.getNivel());
        } else {
            System.out.println("Curso no encontrado.");
        }
    }

    private static void actualizarCurso(Scanner sc, AcademiaService service) {
        System.out.print("Código del curso a actualizar: ");
        String codigo = sc.nextLine();
        Curso c = service.buscarCursoPorCodigo(codigo);
        if (c != null) {
            System.out.print("Nuevo nombre: ");
            c.setNombre(sc.nextLine());
            service.actualizarCurso(c);
            System.out.println("Curso actualizado.");
        } else {
            System.out.println("Curso no encontrado.");
        }
    }

    private static void asignarAulaACurso(Scanner sc, AcademiaService service) {
        System.out.print("Código del curso: ");
        String codigoCurso = sc.nextLine();
        System.out.print("ID del aula: ");
        Long aulaId = Long.parseLong(sc.nextLine());
        service.asignarAulaACurso(codigoCurso, aulaId);
        System.out.println("Aula asignada correctamente.");
    }

    private static void verMatriculasCurso(Scanner sc, AcademiaService service) {
        System.out.print("Código del curso: ");
        String codigo = sc.nextLine();
        service.verMatriculasCurso(codigo)
                .forEach(m -> System.out.println(m.getEstudiante().getNombre() + " - " + m.getEstado()));
    }

    private static void verProfesoresCurso(Scanner sc, AcademiaService service) {
        System.out.print("Código del curso: ");
        String codigo = sc.nextLine();
        service.verProfesoresCurso(codigo)
                .forEach(p -> System.out.println(p.getNombre() + " - " + p.getEspecialidad()));
    }

    // ===================== MÉTODOS DE ESTUDIANTES =====================
    private static void registrarEstudiante(Scanner sc, AcademiaService service) {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Teléfono: ");
            String telefono = sc.nextLine();
            System.out.print("Fecha nacimiento (yyyy-MM-dd): ");
            LocalDate fechaNac = LocalDate.parse(sc.nextLine());
            System.out.print("Dirección: ");
            String direccion = sc.nextLine();

            Estudiante e = new Estudiante();
            e.setNombre(nombre);
            e.setEmail(email);
            e.setTelefono(telefono);
            e.setFechaNacimiento(fechaNac);
            e.setDireccion(direccion);

            service.registrarEstudiante(e);
            System.out.println("Estudiante registrado correctamente.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void matricularEstudiante(Scanner sc, AcademiaService service) {
        try {
            System.out.print("ID estudiante: ");
            Long estudianteId = Long.parseLong(sc.nextLine());
            System.out.print("ID curso: ");
            Long cursoId = Long.parseLong(sc.nextLine());

            service.matricularEstudiante(estudianteId, cursoId);
            System.out.println("Matrícula creada correctamente.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void darBajaMatricula(Scanner sc, AcademiaService service) {
        System.out.print("ID estudiante: ");
        Long estudianteId = Long.parseLong(sc.nextLine());
        System.out.print("ID curso: ");
        Long cursoId = Long.parseLong(sc.nextLine());
        service.darBajaMatricula(estudianteId, cursoId);
        System.out.println("Matrícula dada de baja.");
    }

    private static void verCursosDeEstudiante(Scanner sc, AcademiaService service) {
        System.out.print("ID estudiante: ");
        Long estudianteId = Long.parseLong(sc.nextLine());
        service.verCursosDeEstudiante(estudianteId)
                .forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNombre()));
    }

    private static void actualizarCalificacionFinal(Scanner sc, AcademiaService service) {
        System.out.print("ID estudiante: ");
        Long estudianteId = Long.parseLong(sc.nextLine());
        System.out.print("ID curso: ");
        Long cursoId = Long.parseLong(sc.nextLine());
        System.out.print("Nueva calificación final: ");
        Double cal = Double.parseDouble(sc.nextLine());
        service.actualizarCalificacionFinal(estudianteId, cursoId, cal);
        System.out.println("Calificación actualizada.");
    }

    private static void registrarEvaluacion(Scanner sc, AcademiaService service) {
        try {
            System.out.print("ID estudiante: ");
            Long estudianteId = Long.parseLong(sc.nextLine());
            System.out.print("ID curso: ");
            Long cursoId = Long.parseLong(sc.nextLine());
            System.out.print("Nota: ");
            Double nota = Double.parseDouble(sc.nextLine());
            System.out.print("Tipo (EXAMEN, PRACTICA, PROYECTO, PARTICIPACION, TRABAJO_FINAL): ");
            TipoEvaluacion tipo = TipoEvaluacion.valueOf(sc.nextLine().toUpperCase());
            System.out.print("Observaciones: ");
            String obs = sc.nextLine();

            service.registrarEvaluacion(estudianteId, cursoId, nota, tipo, obs);
            System.out.println("Evaluación registrada correctamente.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ===================== MÉTODOS DE PROFESORES =====================
    private static void registrarProfesor(Scanner sc, AcademiaService service) {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Teléfono: ");
            String telefono = sc.nextLine();
            System.out.print("Especialidad: ");
            String especialidad = sc.nextLine();
            System.out.print("Titulación: ");
            String titulacion = sc.nextLine();
            System.out.print("Fecha contratación (yyyy-MM-dd): ");
            LocalDate fecha = LocalDate.parse(sc.nextLine());
            System.out.print("Salario por hora: ");
            double salario = Double.parseDouble(sc.nextLine());

            Profesor p = new Profesor();
            p.setNombre(nombre);
            p.setEmail(email);
            p.setTelefono(telefono);
            p.setEspecialidad(especialidad);
            p.setTitulacion(titulacion);
            p.setFechaContratacion(fecha);
            p.setSalarioPorHora(salario);

            service.registrarProfesor(p);
            System.out.println("Profesor registrado correctamente.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void asignarProfesorACurso(Scanner sc, AcademiaService service) {
        System.out.print("ID profesor: ");
        Long profesorId = Long.parseLong(sc.nextLine());
        System.out.print("ID curso: ");
        Long cursoId = Long.parseLong(sc.nextLine());
        System.out.print("Rol (TITULAR, AUXILIAR, TUTOR, INVITADO): ");
        RolProfesor rol = RolProfesor.valueOf(sc.nextLine().toUpperCase());
        System.out.print("Horas por semana: ");
        int horas = Integer.parseInt(sc.nextLine());

        service.asignarProfesorACurso(profesorId, cursoId, rol, horas);
        System.out.println("Profesor asignado correctamente.");
    }

    private static void verCursosDeProfesor(Scanner sc, AcademiaService service) {
        System.out.print("ID profesor: ");
        Long profesorId = Long.parseLong(sc.nextLine());
        service.verCursosDeProfesor(profesorId).forEach(c -> System.out.println(c.getCodigo() + " - " + c.getNombre()));
    }

    private static void calcularCargaHorariaProfesor(Scanner sc, AcademiaService service) {
        System.out.print("ID profesor: ");
        Long profesorId = Long.parseLong(sc.nextLine());
        int carga = service.calcularCargaHorariaProfesor(profesorId);
        System.out.println("Carga horaria total: " + carga + " horas/semana.");
    }

    // ===================== MÉTODOS DE AULAS =====================
    private static void crearAula(Scanner sc, AcademiaService service) {
        try {
            System.out.print("Nombre aula: ");
            String nombre = sc.nextLine();
            System.out.print("Capacidad: ");
            int capacidad = Integer.parseInt(sc.nextLine());
            System.out.print("Edificio: ");
            String edificio = sc.nextLine();
            System.out.print("Piso: ");
            int piso = Integer.parseInt(sc.nextLine());
            System.out.print("Equipamiento: ");
            String equipamiento = sc.nextLine();

            Aula a = new Aula();
            a.setNombre(nombre);
            a.setCapacidad(capacidad);
            a.setEdificio(edificio);
            a.setPiso(piso);
            a.setEquipamiento(equipamiento);

            service.crearAula(a);
            System.out.println("Aula creada correctamente.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void listarAulas(AcademiaService service) {
        service.listarAulas().forEach(
                a -> System.out.println(a.getId() + " - " + a.getNombre() + " - Capacidad: " + a.getCapacidad()));
    }

    private static void verOcupacionAulas(Scanner sc, AcademiaService service) {
        System.out.print("ID aula: ");
        Long aulaId = Long.parseLong(sc.nextLine());
        int ocupacion = service.verOcupacionAula(aulaId);
        System.out.println("Ocupación actual: " + ocupacion + " estudiantes.");
    }
    // ===================== MÉTODOS DE REPORTES =====================

    private static void reportarCursosMasMatriculados(ReporteService service) {
        service.cursosMasMatriculados().forEach(dto -> System.out.println(dto.getCurso().getCodigo() + " - "
                + dto.getCurso().getNombre() + " - Matriculados: "
                + dto.getTotalMatriculados()));
    }

    private static void reportarEstudiantesMejorPromedio(ReporteService service) {
        service.estudiantesMejorPromedio()
                .forEach(dto -> System.out.println(dto.getEstudiante().getNombre() + " - Promedio: "
                        + String.format("%.2f", dto.getPromedio())));
    }

    private static void reportarCursosPorCompletarEsteMes(ReporteService service) {
        service.cursosPorCompletarEsteMes().forEach(curso -> System.out.println(curso.getCodigo() + " - "
                + curso.getNombre() + " - Fecha fin: "
                + curso.getFechaFin()));
    }

    private static void reportarIngresosTotalesPorCurso(ReporteService service) {
        service.ingresosTotalesPorCurso().forEach(dto -> System.out.println(dto.getCurso().getNombre() + " - Ingresos: "
                + String.format("%.2f", dto.getIngresos())));
    }

    private static void reportarProfesoresMayorCarga(ReporteService service) {
        service.profesoresMayorCarga()
                .forEach(dto -> System.out.println(dto.getProfesor().getNombre() + " - Carga horaria: "
                        + dto.getCargaHoraria()));
    }

    private static void reportarTasaAbandonoPorCurso(ReporteService service) {
        service.tasaAbandonoPorCurso()
                .forEach(dto -> System.out.println(dto.getCurso().getNombre() + " - Tasa abandono: "
                        + String.format("%.2f", dto.getTasa()) + "%"));
    }

    private static void reportarEstudiantesSinCursosActivos(ReporteService service) {
        service.estudiantesSinCursosActivos().forEach(dto -> System.out.println(dto.getNombre()));
    }

    private static void listarCertificados(ReporteService service) {
        service.listarCertificados().forEach(dto -> System.out.println(dto.getEstudiante().getNombre() + " - Curso: "
                + dto.getCurso().getNombre()));
    }

}
