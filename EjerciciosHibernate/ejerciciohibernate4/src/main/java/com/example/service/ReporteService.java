package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.CursoDAO;
import com.example.dao.CursoDAOImpl;
import com.example.dao.EstudianteDAO;
import com.example.dao.EstudianteDAOImpl;
import com.example.dao.MatriculaDAO;
import com.example.dao.MatriculaDAOImpl;
import com.example.dao.ProfesorDAO;
import com.example.dao.ProfesorDAOImpl;
import com.example.dto.CursoIngresoDTO;
import com.example.dto.CursoMatriculadosDTO;
import com.example.dto.CursoTasaAbandonoDTO;
import com.example.dto.EstudiantePromedioDTO;
import com.example.dto.ProfesorCargaDTO;
import com.example.model.Curso;
import com.example.model.EstadoMatricula;
import com.example.model.Estudiante;
import com.example.model.Matricula;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class ReporteService {

    private EntityManager em = JPAUtil.getEntityManager();
     private MatriculaDAO matriculaDAO = new MatriculaDAOImpl(em);
    private CursoDAO cursoDAO = new CursoDAOImpl(em);
    private EstudianteDAO estudianteDAO = new EstudianteDAOImpl(em);
    private ProfesorDAO profesorDAO = new ProfesorDAOImpl(em);

  
    // 1️⃣ Cursos con más matriculados
    public List<CursoMatriculadosDTO> cursosMasMatriculados() {
        return cursoDAO.listarTodos().stream()
                .map(c -> new CursoMatriculadosDTO(c, c.getMatriculas().size()))
                .sorted((a, b) -> Long.compare(b.getTotalMatriculados(), a.getTotalMatriculados()))
                .collect(Collectors.toList());
    }

    // 2️⃣ Estudiantes con mejor promedio
    public List<EstudiantePromedioDTO> estudiantesMejorPromedio() {
        return estudianteDAO.listarTodos().stream()
                .map(e -> {
                    double promedio = e.getMatriculas().stream()
                            .filter(m -> m.getCalificacionFinal() != null)
                            .mapToDouble(Matricula::getCalificacionFinal)
                            .average().orElse(0.0);
                    return new EstudiantePromedioDTO(e, promedio);
                })
                .filter(dto -> dto.getPromedio() >= 7.0)
                .sorted((a, b) -> Double.compare(b.getPromedio(), a.getPromedio()))
                .collect(Collectors.toList());
    }

    // 3️⃣ Cursos por completar este mes
    public List<Curso> cursosPorCompletarEsteMes() {
        LocalDate hoy = LocalDate.now();
        return cursoDAO.listarTodos().stream()
                .filter(c -> c.getFechaFin().getMonth() == hoy.getMonth() &&
                             c.getFechaFin().getYear() == hoy.getYear())
                .collect(Collectors.toList());
    }

    // 4️⃣ Ingresos totales por curso
    public List<CursoIngresoDTO> ingresosTotalesPorCurso() {
        return cursoDAO.listarTodos().stream()
                .map(c -> {
                    double ingresos = c.getMatriculas().stream()
                            .filter(m -> m.getEstado() != EstadoMatricula.ABANDONADA)
                            .count() * c.getPrecio();
                    return new CursoIngresoDTO(c, ingresos);
                })
                .collect(Collectors.toList());
    }

    // 5️⃣ Profesores con mayor carga horaria
    public List<ProfesorCargaDTO> profesoresMayorCarga() {
        return profesorDAO.listarTodos().stream()
                .map(p -> {
                    int carga = p.getAsignaciones().stream()
                            .mapToInt(a -> a.getHorasSemana())
                            .sum();
                    return new ProfesorCargaDTO(p, carga);
                })
                .sorted((a, b) -> Integer.compare(b.getCargaHoraria(), a.getCargaHoraria()))
                .collect(Collectors.toList());
    }

    // 6️⃣ Tasa de abandono por curso
    public List<CursoTasaAbandonoDTO> tasaAbandonoPorCurso() {
        return cursoDAO.listarTodos().stream()
                .map(c -> {
                    long total = c.getMatriculas().size();
                    long abandonos = c.getMatriculas().stream()
                            .filter(m -> m.getEstado() == EstadoMatricula.ABANDONADA)
                            .count();
                    double tasa = total > 0 ? abandonos * 100.0 / total : 0.0;
                    return new CursoTasaAbandonoDTO(c, tasa);
                })
                .collect(Collectors.toList());
    }

    // 7️⃣ Estudiantes sin cursos activos
    public List<Estudiante> estudiantesSinCursosActivos() {
        return estudianteDAO.listarTodos().stream()
                .filter(e -> e.getMatriculas().stream()
                        .noneMatch(m -> m.getEstado() == EstadoMatricula.ACTIVA))
                .collect(Collectors.toList());
    }

    // 8️⃣ Listado de certificados a emitir
    public List<Matricula> listarCertificados() {
        return matriculaDAO.listarTodos().stream()
                .filter(m -> m.getEstado() == EstadoMatricula.COMPLETADA)
                .collect(Collectors.toList());
    }

}
