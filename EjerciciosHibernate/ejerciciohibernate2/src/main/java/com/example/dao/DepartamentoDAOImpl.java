package com.example.dao;

import java.util.List;

import com.example.model.Departamento;
import com.example.model.Empleado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class DepartamentoDAOImpl implements DepartamentoDAO {

    private EntityManager em;

    public DepartamentoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void crear(Departamento departamento) {

        try {
            em.persist(departamento);
        } catch (Exception e) {
        }
    }

    @Override
    public List<Departamento> listarTodos() {
        try {
            return em.createQuery(
                    "SELECT d FROM Departamento d ORDER BY d.nombre",
                    Departamento.class)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public Departamento buscarPorCodigo(String codigo) {
        try {
            return em.createQuery(
                    "SELECT d FROM Departamento d WHERE d.codigo = :codigo",
                    Departamento.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actualizar(Departamento departamento) {

        try {
            em.merge(departamento);
        } catch (Exception e) {
        }
    }

    @Override
    public void eliminar(String codigo) {

        try {
            Departamento departamento = buscarPorCodigo(codigo);
            if (departamento != null) {
                em.remove(departamento);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public List<Empleado> listarEmpleados(Departamento departamento) {
        try {
            return em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.departamento = :departamento",
                    Empleado.class)
                    .setParameter("departamento", departamento)
                    .getResultList();
        } catch (NoResultException e) {
            // Si no hay empleados, devolvemos lista vacía
            return List.of();
        } catch (Exception e) {
            // Imprime el error y devuelve lista vacía para no romper el flujo
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Departamento DepartamentoConMasEmpleados() {
        try {
            return em.createQuery(
                    "SELECT d FROM Departamento d LEFT JOIN d.empleados e " +
                            "GROUP BY d ORDER BY COUNT(e) DESC",
                    Departamento.class)
                    .setMaxResults(1) // Solo el primero
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Object[]> salarioPromedioPorDepartamento() {
        try {
            return em.createQuery(
                    "SELECT d.nombre, AVG(e.salario) " +
                            "FROM Departamento d JOIN d.empleados e " +
                            "GROUP BY d.nombre",
                    Object[].class)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public List<Object[]> contarEmpleadosPorDepartamento() {
        try {
            return em.createQuery(
                    "SELECT d.nombre, COUNT(e) " +
                            "FROM Departamento d LEFT JOIN d.empleados e " +
                            "GROUP BY d.nombre",
                    Object[].class)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

}
