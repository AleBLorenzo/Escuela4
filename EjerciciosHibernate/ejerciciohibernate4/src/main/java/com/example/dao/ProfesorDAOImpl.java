package com.example.dao;

import java.util.List;

import com.example.model.Profesor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ProfesorDAOImpl implements ProfesorDAO {

    private EntityManager em;

    public ProfesorDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Profesor p) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }

    }

    @Override
    public void actualizar(Profesor p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(p);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }

    }

    @Override
    public void eliminar(Profesor p) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(p) ? p : em.merge(p));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }

    }

    @Override
    public Profesor buscarPorId(Long id) {
        return em.find(Profesor.class, id);
    }

    @Override
    public List<Profesor> listarTodos() {
        return em.createQuery("SELECT p FROM Profesor p", Profesor.class).getResultList();
    }

    @Override
    public List<Profesor> profesoresMayorCargaHoraria(int top) {
        TypedQuery<Profesor> query = em.createQuery(
                "SELECT p FROM Profesor p JOIN p.asignaciones a " +
                        "GROUP BY p.id ORDER BY SUM(a.horasSemana) DESC",
                Profesor.class);
        query.setMaxResults(top);
        return query.getResultList();
    }

}
