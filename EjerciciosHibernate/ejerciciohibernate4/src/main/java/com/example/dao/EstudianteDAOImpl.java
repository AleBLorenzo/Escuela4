package com.example.dao;

import java.util.List;

import com.example.model.Estudiante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class EstudianteDAOImpl implements EstudianteDAO {

    private EntityManager em;

    public EstudianteDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Estudiante e) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(e);
            tx.commit();

        } catch (Exception ex) {
            if (tx.isActive())
                tx.rollback();
            throw ex;
        }

    }

    @Override
    public void actualizar(Estudiante e) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(e);
            tx.commit();

        } catch (Exception ex) {
            if (tx.isActive())
                tx.rollback();
            throw ex;
        }

    }

    @Override
    public void eliminar(Estudiante e) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(e) ? e : em.merge(e));
            tx.commit();

        } catch (Exception ex) {
            if (tx.isActive())
                tx.rollback();
            throw ex;
        }

    }

    @Override
    public Estudiante buscarPorId(Long id) {
        return em.find(Estudiante.class, id);
    }

    @Override
    public List<Estudiante> listarTodos() {
        return em.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
    }

    @Override
    public List<Estudiante> estudiantesMejorPromedio(int top) {
        TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e JOIN e.matriculas m " +
                        "WHERE m.calificacionFinal IS NOT NULL " +
                        "GROUP BY e.id ORDER BY AVG(m.calificacionFinal) DESC",
                Estudiante.class);
        query.setMaxResults(top);
        return query.getResultList();
    }

    @Override
    public List<Estudiante> estudiantesSinCursosActivos() {
        TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE NOT EXISTS (" +
                        "SELECT m FROM Matricula m WHERE m.estudiante = e AND m.estado = 'ACTIVA')",
                Estudiante.class);
        return query.getResultList();
    }

}
