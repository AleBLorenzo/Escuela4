package com.example.dao;

import java.util.List;

import com.example.model.Matricula;
import com.example.model.MatriculaId;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class MatriculaDAOImpl implements MatriculaDAO {

    private EntityManager em;

    public MatriculaDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Matricula m) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(m);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }

    }

    @Override
    public void actualizar(Matricula m) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(m);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }

    }

    @Override
    public void eliminar(Matricula m) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(m) ? m : em.merge(m));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }

    }

    @Override
    public Matricula buscarPorId(MatriculaId id) {
        return em.find(Matricula.class, id);
    }

    @Override
    public List<Matricula> listarTodos() {
        return em.createQuery("SELECT m FROM Matricula m", Matricula.class).getResultList();
    }

    @Override
    public long contarMatriculasActivasPorCurso(Long cursoId) {
        Query q = em
                .createQuery("SELECT COUNT(m) FROM Matricula m WHERE m.curso.id = :cursoId AND m.estado = 'ACTIVA'");
        q.setParameter("cursoId", cursoId);
        return (long) q.getSingleResult();
    }

    @Override
    public List<Object[]> estudiantesDeCursoConPromedios(String codigoCurso) {
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT e.nombre, m.porcentajeAsistencia, m.calificacionFinal " +
                        "FROM Matricula m JOIN m.estudiante e " +
                        "WHERE m.curso.codigo = :codigoCurso AND m.estado = 'ACTIVA' " +
                        "ORDER BY m.calificacionFinal DESC",
                Object[].class);
        q.setParameter("codigoCurso", codigoCurso);
        return q.getResultList();
    }

}
