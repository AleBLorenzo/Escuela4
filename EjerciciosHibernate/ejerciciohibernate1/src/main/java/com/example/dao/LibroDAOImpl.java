package com.example.dao;

import java.util.List;

import com.example.model.Libro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class LibroDAOImpl implements LibroDAO {

    private EntityManager em;

    public LibroDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void guardar(Libro libro) {

        em.persist(libro);
    }

    @Override
    public Libro buscarPorId(Long id) {

        try {
            return em.find(Libro.class, id);
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Libro buscarPorIsbn(String isbn) {

        try {
          
            return em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn", Libro.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 

    }

    @Override
    public List<Libro> listarTodos() {

        try {
            return em.createQuery(
                    "SELECT l FROM Libro l ORDER BY l.titulo",
                    Libro.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override

    public List<Libro> listarDisponibles() {

        try {
            return em.createQuery(
                    "SELECT l FROM Libro l WHERE l.disponible = true ORDER BY l.autor, l.titulo",
                    Libro.class)
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<Libro> buscarPorAutor(String autor) {

        try {
            return em.createQuery(
                    "SELECT l FROM Libro l WHERE LOWER(l.autor) LIKE LOWER(:autor)",
                    Libro.class)
                    .setParameter("autor", "%" + autor + "%")
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public void actualizar(Libro libro) {

        em.merge(libro);
    }

    @Override
    public void eliminar(Long id) {

        Libro libro = buscarPorId(id);
        if (libro != null) {
            em.remove(libro);
        }
    }

    @Override

    public long contarTotal() {

        try {
            return em.createQuery(
                    "SELECT COUNT(l) FROM Libro l",
                    Long.class)
                    .getSingleResult();

        } catch (NoResultException e) {
            return (Long) null;
        }

    }

    @Override
    public long contarDisponibles() {

        try {
            return em.createQuery(
                    "SELECT COUNT(l) FROM Libro l WHERE l.disponible = true",
                    Long.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return (Long) null;
        }

    }

    @Override
    public Double precioPromedio() {

        try {
            return em.createQuery(
                    "SELECT AVG(l.precio) FROM Libro l",
                    Double.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Libro libroMasCaro() {

        try {
            List<Libro> resultado = em.createQuery(
                    "SELECT l FROM Libro l ORDER BY l.precio DESC",
                    Libro.class)
                    .setMaxResults(1)
                    .getResultList();

            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado.get(0);
            }
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Libro libroMasBarato() {

        try {
            List<Libro> resultado = em.createQuery(
                    "SELECT l FROM Libro l ORDER BY l.precio ASC",
                    Libro.class)
                    .setMaxResults(1)
                    .getResultList();

            if (resultado.isEmpty()) {
                return null;
            } else {
                return resultado.get(0);
            }
        } catch (NoResultException e) {
            return null;
        }

    }

}
