package com.tienda.dao;

import java.util.List;

import com.tienda.model.LineaPedido;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class LineaPedidoDAOImpl implements LineaPedidoDAO {

    private EntityManager em;

    public LineaPedidoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(LineaPedido linea) {
        try {
            em.getTransaction().begin();
            em.persist(linea);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(LineaPedido linea) {
        try {
            em.getTransaction().begin();
            em.merge(linea);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            em.getTransaction().begin();
            LineaPedido lp = em.find(LineaPedido.class, id);
            if(lp != null) em.remove(lp);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public LineaPedido buscarPorId(Long id) {
        try {
            return em.find(LineaPedido.class, id);
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public List<LineaPedido> listarPorPedido(Long pedidoId) {
        return em.createQuery("SELECT lp FROM LineaPedido lp WHERE lp.pedido.id = :id", LineaPedido.class)
                 .setParameter("id", pedidoId)
                 .getResultList();
    }
}