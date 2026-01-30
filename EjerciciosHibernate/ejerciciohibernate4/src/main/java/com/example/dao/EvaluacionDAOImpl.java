package com.example.dao;

import java.util.List;

import com.example.model.Evaluacion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EvaluacionDAOImpl  implements EvaluacionDAO {

       private EntityManager em;

    public EvaluacionDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Evaluacion evaluacion) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(evaluacion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void actualizar(Evaluacion evaluacion) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(evaluacion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void eliminar(Evaluacion evaluacion) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(evaluacion) ? evaluacion : em.merge(evaluacion));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public Evaluacion buscarPorId(Long id) {
        return em.find(Evaluacion.class, id);
    }

    @Override
    public List<Evaluacion> listarTodos() {
        return em.createQuery("SELECT e FROM Evaluacion e", Evaluacion.class)
                 .getResultList();
    }
}
