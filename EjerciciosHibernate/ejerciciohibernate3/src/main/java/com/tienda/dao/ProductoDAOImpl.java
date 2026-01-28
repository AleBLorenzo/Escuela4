package com.tienda.dao;

import java.util.List;

import com.tienda.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class ProductoDAOImpl implements  ProductoDAO{

        private EntityManager em;

    public ProductoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Producto producto) {
        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Producto producto) {
        try {
            em.getTransaction().begin();
            em.merge(producto);
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
            Producto p = em.find(Producto.class, id);
            if(p != null) em.remove(p);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Producto buscarPorId(Long id) {
        try {
            return em.find(Producto.class, id);
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Producto> listarTodos() {
        return em.createQuery("SELECT p FROM Producto p ORDER BY p.nombre", Producto.class)
                 .getResultList();
    }

    @Override
    public List<Producto> listarPorCategoria(Long idCategoria) {
        return em.createQuery("SELECT p FROM Producto p WHERE p.categoria.id = :id ORDER BY p.nombre", Producto.class)
                 .setParameter("id", idCategoria)
                 .getResultList();
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return em.createQuery("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE :nombre ORDER BY p.nombre", Producto.class)
                 .setParameter("nombre", "%" + nombre.toLowerCase() + "%")
                 .getResultList();
    }

    @Override
    public List<Producto> productosConStockBajo(int stockMinimo) {
        return em.createQuery("SELECT p FROM Producto p WHERE p.stock < :minimo ORDER BY p.stock ASC", Producto.class)
                 .setParameter("minimo", stockMinimo)
                 .getResultList();
    }

}
