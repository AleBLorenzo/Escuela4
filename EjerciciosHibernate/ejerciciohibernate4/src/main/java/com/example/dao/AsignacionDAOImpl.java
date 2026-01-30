package com.example.dao;

import com.example.model.Asignacion;
import com.example.model.AsignacionId;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class AsignacionDAOImpl implements AsignacionDAO {

    private EntityManager em;

    public AsignacionDAOImpl(EntityManager em) {
        this.em = em;
    }
 @Override
    public void guardar(Asignacion a) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(a);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void actualizar(Asignacion a) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(a);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void eliminar(Asignacion a) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(a) ? a : em.merge(a));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Asignacion buscarPorId(AsignacionId id) {
        return em.find(Asignacion.class, id);
    }

    @Override
    public int calcularCargaHorariaTotal(Long profesorId) {
        Query q = em.createQuery(
                "SELECT COALESCE(SUM(a.horasSemana), 0) " +
                        "FROM Asignacion a " +
                        "WHERE a.profesor.id = :profesorId " +
                        "AND (a.fechaFin IS NULL OR a.fechaFin >= CURRENT_DATE)");
        q.setParameter("profesorId", profesorId);
        return ((Number) q.getSingleResult()).intValue();
    }

}
