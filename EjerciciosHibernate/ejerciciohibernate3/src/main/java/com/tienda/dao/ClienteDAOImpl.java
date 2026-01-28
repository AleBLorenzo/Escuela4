package com.tienda.dao;

import java.util.List;

import com.tienda.model.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ClienteDAOImpl implements ClienteDAO {

    private EntityManager em;

    public ClienteDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.merge(cliente);
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
            Cliente c = em.find(Cliente.class, id);
            if (c != null)
                em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Cliente buscarPorId(Long id) {
        try {
            return em.find(Cliente.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        return em.createQuery("SELECT c FROM Cliente c ORDER BY c.nombre", Cliente.class)
                .getResultList();
    }

     @Override
    public List<Cliente> listarClientesConPedidos() {
        String jpql = "SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        return query.getResultList();
    }

}
