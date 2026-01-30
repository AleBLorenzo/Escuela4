package com.example.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.dao.AsignacionDAO;
import com.example.dao.AsignacionDAOImpl;
import com.example.dao.AulaDAO;
import com.example.dao.AulaDAOImpl;
import com.example.dao.CursoDAO;
import com.example.dao.CursoDAOImpl;
import com.example.dao.EstudianteDAO;
import com.example.dao.EstudianteDAOImpl;
import com.example.dao.EvaluacionDAO;
import com.example.dao.EvaluacionDAOImpl;
import com.example.dao.MatriculaDAO;
import com.example.dao.MatriculaDAOImpl;
import com.example.dao.ProfesorDAO;
import com.example.dao.ProfesorDAOImpl;
import com.example.model.Asignacion;
import com.example.model.Aula;
import com.example.model.Curso;
import com.example.model.EstadoMatricula;
import com.example.model.Estudiante;
import com.example.model.Evaluacion;
import com.example.model.Matricula;
import com.example.model.MatriculaId;
import com.example.model.Profesor;
import com.example.model.RolProfesor;
import com.example.model.TipoEvaluacion;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class AcademiaService {

    private EntityManager em = JPAUtil.getEntityManager();

    private final CursoDAO cursoDAO = new CursoDAOImpl(em);
    private final EstudianteDAO estudianteDAO = new EstudianteDAOImpl(em);
    private final ProfesorDAO profesorDAO = new ProfesorDAOImpl(em);
    private final AulaDAO aulaDAO = new AulaDAOImpl(em);
    private final MatriculaDAO matriculaDAO = new MatriculaDAOImpl(em);
    private final AsignacionDAO asignacionDAO = new AsignacionDAOImpl(em);
    private final EvaluacionDAO evaluacionDAO = new EvaluacionDAOImpl(em);

    // ------------------- CURSOS -------------------
    public void crearCurso(Curso curso) {
        cursoDAO.guardar(curso);
    }

    public List<Curso> listarCursos() {
        return cursoDAO.listarTodos();
    }

    public Curso buscarCursoPorCodigo(String codigo) {
        return cursoDAO.buscarPorCodigo(codigo);
    }

    public void actualizarCurso(Curso curso) {
        cursoDAO.actualizar(curso);
    }

    public void asignarAulaACurso(String codigoCurso, Long aulaId) {
        Curso curso = cursoDAO.buscarPorCodigo(codigoCurso);
        Aula aula = aulaDAO.buscarPorId(aulaId);
        curso.setAula(aula);
        cursoDAO.actualizar(curso);
    }

    public Set<Matricula> verMatriculasCurso(String codigoCurso) {
        Curso curso = cursoDAO.buscarPorCodigo(codigoCurso);
        return curso.getMatriculas();
    }

    public List<Profesor> verProfesoresCurso(String codigoCurso) {
        Curso curso = cursoDAO.buscarPorCodigo(codigoCurso);
        if (curso == null) {
            return Collections.emptyList();
        }
        return curso.getAsignaciones().stream()
                .map(Asignacion::getProfesor)
                .collect(Collectors.toList());
    }

    // ------------------- ESTUDIANTES -------------------
    public void registrarEstudiante(Estudiante e) {
        e.setFechaRegistro(LocalDate.now());
        estudianteDAO.guardar(e);
    }

    public Matricula matricularEstudiante(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteDAO.buscarPorId(estudianteId);
        Curso curso = cursoDAO.buscarPorId(cursoId);

        // Validaciones
        if (!curso.getActivo())
            throw new IllegalStateException("El curso no está activo");
        if (!estudiante.getActivo())
            throw new IllegalStateException("El estudiante no está activo");

        long matriculasActivas = matriculaDAO.contarMatriculasActivasPorCurso(cursoId);
        if (matriculasActivas >= curso.getMaxEstudiantes())
            throw new IllegalStateException("El curso ha alcanzado su capacidad máxima");

        MatriculaId id = new MatriculaId(estudianteId, cursoId);
        Matricula existente = matriculaDAO.buscarPorId(id);
        if (existente != null && existente.getEstado() == EstadoMatricula.ACTIVA)
            throw new IllegalStateException("El estudiante ya está matriculado en este curso");

        Matricula matricula = new Matricula(estudiante, curso);
        matriculaDAO.guardar(matricula);
        return matricula;
    }

    public void darBajaMatricula(Long estudianteId, Long cursoId) {
        MatriculaId id = new MatriculaId(estudianteId, cursoId);
        Matricula m = matriculaDAO.buscarPorId(id);
        if (m != null) {
            m.setEstado(EstadoMatricula.ABANDONADA);
            matriculaDAO.actualizar(m);
        }
    }

    public List<Curso> verCursosDeEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteDAO.buscarPorId(estudianteId);
        if (estudiante == null)
            return Collections.emptyList();

        return estudiante.getMatriculas().stream()
                .filter(m -> m.getEstado() == EstadoMatricula.ACTIVA)
                .map(Matricula::getCurso)
                .collect(Collectors.toList());
    }

    public void actualizarCalificacionFinal(Long estudianteId, Long cursoId, double calificacion) {
        MatriculaId id = new MatriculaId(estudianteId, cursoId);
        Matricula m = matriculaDAO.buscarPorId(id);
        if (m != null) {
            m.setCalificacionFinal(calificacion);
            matriculaDAO.actualizar(m);
        }
    }

    public void registrarEvaluacion(Long estudianteId, Long cursoId,
            double nota, TipoEvaluacion tipo, String observaciones) {

        MatriculaId id = new MatriculaId(estudianteId, cursoId);
        Matricula matricula = matriculaDAO.buscarPorId(id);

        if (matricula == null || matricula.getEstado() != EstadoMatricula.ACTIVA) {
            throw new IllegalStateException("Matrícula no encontrada o no activa");
        }

        Evaluacion eval = new Evaluacion();
        eval.setMatricula(matricula);
        eval.setNota(nota);
        eval.setTipo(tipo);
        eval.setObservaciones(observaciones);
        eval.setFecha(LocalDate.now());

        evaluacionDAO.guardar(eval);

        matricula.getEvaluaciones().add(eval);
        matricula.calcularCalificacionFinal();
        matriculaDAO.actualizar(matricula);
    }

    // ------------------- PROFESORES -------------------
    public void registrarProfesor(Profesor p) {
        profesorDAO.guardar(p);
    }

    public void asignarProfesorACurso(Long profesorId, Long cursoId, RolProfesor rol, int horasSemana) {
        Profesor profesor = profesorDAO.buscarPorId(profesorId);
        Curso curso = cursoDAO.buscarPorId(cursoId);

        if (profesor == null || curso == null) {
            throw new IllegalArgumentException("Profesor o curso no encontrados");
        }

        // Validar fechas o duplicados si quieres

        Asignacion asignacion = new Asignacion();
        asignacion.setProfesor(profesor);
        asignacion.setCurso(curso);
        asignacion.setRol(rol);
        asignacion.setHorasSemana(horasSemana);
        asignacion.setFechaInicio(LocalDate.now());

        asignacionDAO.guardar(asignacion);

        // Añadir a los sets para mantener la relación bidireccional
        profesor.getAsignaciones().add(asignacion);
        curso.getAsignaciones().add(asignacion);

        profesorDAO.actualizar(profesor);
        cursoDAO.actualizar(curso);
    }

    public List<Curso> verCursosDeProfesor(Long profesorId) {
        Profesor profesor = profesorDAO.buscarPorId(profesorId);
        if (profesor == null)
            return Collections.emptyList();

        return profesor.getAsignaciones().stream()
                .map(Asignacion::getCurso)
                .collect(Collectors.toList());
    }

    public int calcularCargaHorariaProfesor(Long profesorId) {
        return asignacionDAO.calcularCargaHorariaTotal(profesorId);
    }

    // ------------------- AULAS -------------------
    public void crearAula(Aula a) {
        aulaDAO.guardar(a);
    }

    public List<Aula> listarAulas() {
        return aulaDAO.listarTodos();
    }

    public int verOcupacionAula(Long aulaId) {
        Aula a = aulaDAO.buscarPorId(aulaId);
        return a.getCursos().size();
    }

}
