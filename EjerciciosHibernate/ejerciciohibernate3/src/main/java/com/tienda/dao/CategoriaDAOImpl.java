package com.tienda.dao;

import java.util.List;

import com.tienda.model.Categoria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class CategoriaDAOImpl implements CategoriaDAO {

    private EntityManager em;

    public CategoriaDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Categoria categoria) {
        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Categoria categoria) {
        try {
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            em.getTransaction().begin();
            Categoria c = em.find(Categoria.class, id);
            if (c != null)
                em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Categoria buscarPorId(Long id) {
        try {
            return em.find(Categoria.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Categoria> listarTodos() {
        try {
            return em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre", Categoria.class)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

}
