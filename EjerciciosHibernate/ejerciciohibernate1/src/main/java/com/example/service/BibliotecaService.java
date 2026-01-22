package com.example.service;

import java.time.Year;
import java.util.List;

import com.example.dao.LibroDAO;
import com.example.dao.LibroDAOImpl;
import com.example.model.Libro;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class BibliotecaService {

    public void guardar(Libro libro) throws Exception {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);

        try {
            if (libroDAO.buscarPorIsbn(libro.getIsbn()) != null) {
                throw new Exception("El ISBN ya existe.");
            }

            int anioActual = Year.now().getValue();
            if (libro.getAnio() < 1000 || libro.getAnio() > anioActual) {
                throw new Exception("Año incorrecto.");
            }

            if (libro.getPrecio() <= 0) {
                throw new Exception("Precio incorrecto.");
            }

            em.getTransaction().begin();
            libroDAO.guardar(libro);
            em.getTransaction().commit();

            System.out.println("Libro registrado con éxito. ID: " + libro.getId());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }

    }

    public Libro buscarPorId(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);

        try {

            Libro librosolicutado = libroDAO.buscarPorId(id);

            if (librosolicutado == null) {
                throw new Exception("Error: El ID '" + id + "' ya existe.");
            }

            return librosolicutado;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public Libro buscarPorIsbn(String isbn) {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);

        try {

            Libro librosolicutado = libroDAO.buscarPorIsbn(isbn);

            if (librosolicutado == null) {
                throw new Exception("Error: El ISBN '" + isbn + "' ya existe.");
            }

            return librosolicutado;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Libro> listarTodos() {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);

        try {

            List<Libro> lista = libroDAO.listarTodos();

            if (lista.isEmpty()) {
                System.out.println("No hay libros.");
            }
            return lista;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            em.close();
        }
        return List.of();
    }

    public List<Libro> listarDisponibles() {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);
        try {

            List<Libro> lista = libroDAO.listarDisponibles();
            int cantidad = lista.size();

            if (lista.isEmpty()) {
                System.out.println("No hay libros disponibles en este momento.");
            } else {
                System.out.println("Total disponibles: " + lista.size());
            }

            return lista;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public List<Libro> buscarPorAutor(String autor) {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);
        try {
            List<Libro> lista = libroDAO.buscarPorAutor(autor);

            if (lista.isEmpty()) {
                System.out.println("No hay libros en este momento.");
            }

            return lista;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public void actualizar(Libro libro) {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);
        try {
            em.getTransaction().begin();

            libroDAO.actualizar(libro);

            em.getTransaction().commit();

            System.out.println("Libro actualizado correctamente en la base de datos.");

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
        LibroDAO libroDAO = new LibroDAOImpl(em);
        try {
            em.getTransaction().begin();

            libroDAO.eliminar(id);

            em.getTransaction().commit();

            System.out.println("Libro eliminado correctamente en la base de datos.");

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
        LibroDAO libroDAO = new LibroDAOImpl(em);
        try {

            long catidad = libroDAO.contarTotal();

            return catidad;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return 0;
    }

    public long contarDisponibles() {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);
        try {

            long catidad = libroDAO.contarDisponibles();

            return catidad;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();

            return 0;
        }
    }

    public Double precioPromedio() {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);
        try {

            double catidad = libroDAO.precioPromedio();

            return catidad;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public Libro libroMasCaro() {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);

        try {

            Libro libro = libroDAO.libroMasCaro();

            return libro;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public Libro libroMasBarato() {

        EntityManager em = JPAUtil.getEntityManager();
        LibroDAO libroDAO = new LibroDAOImpl(em);

        try {

            Libro libro = libroDAO.libroMasBarato();

            return libro;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

}
