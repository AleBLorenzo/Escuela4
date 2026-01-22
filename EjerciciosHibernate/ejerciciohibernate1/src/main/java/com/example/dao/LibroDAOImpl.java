package com.example.dao;

import java.util.List;

import com.example.model.Libro;

import jakarta.persistence.EntityManager;

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

        return em.find(Libro.class, id);
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {

        return em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn", Libro.class)
                .setParameter("isbn", isbn)
                .getSingleResult();

    }

    @Override
    public List<Libro> listarTodos() {

        return em.createQuery(
                "SELECT l FROM Libro l ORDER BY l.titulo",
                Libro.class)
                .getResultList();
    }

    @Override
    public List<Libro> listarDisponibles() {

        return em.createQuery(
                "SELECT l FROM Libro l WHERE l.disponible = true ORDER BY l.autor, l.titulo",
                Libro.class)
                .getResultList();
    }

    @Override
    public List<Libro> buscarPorAutor(String autor) {

        return em.createQuery(
                "SELECT l FROM Libro l WHERE LOWER(l.autor) LIKE LOWER(:autor)",
                Libro.class)
                .setParameter("autor", "%" + autor + "%")
                .getResultList();

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

        return em.createQuery(
                "SELECT COUNT(l) FROM Libro l",
                Long.class)
                .getSingleResult();
    }

    @Override
    public long contarDisponibles() {

        return em.createQuery(
                "SELECT COUNT(l) FROM Libro l WHERE l.disponible = true",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Double precioPromedio() {
        return em.createQuery(
                "SELECT AVG(l.precio) FROM Libro l",
                Double.class)
                .getSingleResult();
    }

    @Override
    public Libro libroMasCaro() {

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
    }

    @Override
    public Libro libroMasBarato() {

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
    }

}
