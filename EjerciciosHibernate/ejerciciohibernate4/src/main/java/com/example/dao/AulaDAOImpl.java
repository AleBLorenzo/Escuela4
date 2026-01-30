package com.example.dao;

import java.util.List;

import com.example.model.Aula;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class AulaDAOImpl implements AulaDAO {

    private EntityManager em;

    public AulaDAOImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public void guardar(Aula a) {
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
    public void actualizar(Aula a) {
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
    public void eliminar(Aula a) {
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
    public Aula buscarPorId(Long id) {
        return em.find(Aula.class, id);
    }

    @Override
    public List<Aula> listarTodos() {
        return em.createQuery("SELECT a FROM Aula a", Aula.class).getResultList();
    }

    @Override
    public List<Aula> listarAulasDisponibles() {
        TypedQuery<Aula> query = em.createQuery(
                "SELECT a FROM Aula a WHERE SIZE(a.cursos) < a.capacidad", Aula.class);
        return query.getResultList();
    }

}
