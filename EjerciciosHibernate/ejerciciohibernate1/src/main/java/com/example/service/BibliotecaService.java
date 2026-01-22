package com.example.service;

import java.util.List;

import com.example.dao.LibroDAO;
import com.example.dao.LibroDAOImpl;
import com.example.model.Libro;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class BibliotecaService {

    private LibroDAO libroDAO;

   
    public void guardar(Libro libro) {

        EntityManager em = JPAUtil.getEntityManager();

        libroDAO = new LibroDAOImpl(em);
        try {

            em.getTransaction().begin();

           libroDAO.guardar(libro);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

    }


    public Libro buscarPorId(Long id) {

      EntityManager  em = JPAUtil.getEntityManager();

        try {

            return em.find(Libro.class, id);

        } finally {
            em.close();
        }

    }


    public Libro buscarPorIsbn(String isbn) {
        EntityManager em = JPAUtil.getEntityManager();

        try {

            return em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn", Libro.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Libro> listarTodos() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT l FROM Libro l ORDER BY l.titulo",
                    Libro.class)
                    .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            em.close();
        }
        return null;
    }


    public List<Libro> listarDisponibles() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT l FROM Libro l WHERE l.disponible = true ORDER BY l.autor, l.titulo",
                    Libro.class)
                    .getResultList();

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

 
    public List<Libro> buscarPorAutor(String autor) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT l FROM Libro l WHERE LOWER(l.autor) LIKE LOWER(:autor)",
                    Libro.class)
                    .setParameter("autor", "%" + autor + "%")
                    .getResultList();

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

 
    public void actualizar(Libro libro) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

  
    public void eliminar(Long id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Libro libro = em.find(Libro.class, id);
            if (libro != null) {

                em.remove(libro);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    
    public long contarTotal() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COUNT(l) FROM Libro l",
                    Long.class)
                    .getSingleResult();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return 0;
    }


    public long contarDisponibles() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COUNT(l) FROM Libro l WHERE l.disponible = true",
                    Long.class)
                    .getSingleResult();

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();

            return 0;
        }
    }


    public Double precioPromedio() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT AVG(l.precio) FROM Libro l",
                    Double.class)
                    .getSingleResult();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

  
    public Libro libroMasCaro() {

        EntityManager em = JPAUtil.getEntityManager();

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

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }


    public Libro libroMasBarato() {

        EntityManager em = JPAUtil.getEntityManager();

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

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }


}
