package com.example.dao;

import java.time.LocalDate;
import java.util.List;

import com.example.model.Departamento;
import com.example.model.Empleado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    private EntityManager em;

    public EmpleadoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void anadir(Empleado empleado) {

        try {
            em.persist(empleado);
        } catch (Exception e) {
        }

    }

    @Override
    public List<Empleado> listarTodos() {

        try {
            return em.createQuery("SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento WHERE e.activo = true ORDER BY e.apellidos", Empleado.class)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public Empleado buscarPorEmail(String email) {

        try {
            return em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.email = :email",
                    Empleado.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void actualizar(Empleado empleado) {

        try {
            em.merge(empleado);
        } catch (Exception e) {
        }
    }

    @Override
    public void eliminar(String email) {

        try {
            Empleado empleado = buscarPorEmail(email);
            if (empleado != null) {
                em.remove(empleado);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void reasignarDepartamento(Empleado empleado, Long id_departamento) {
        try {
            Departamento dep = em.find(Departamento.class, id_departamento);

            if (dep == null) {
                throw new IllegalArgumentException("Departamento no existe");
            }

            empleado.setDepartamento(dep);
            em.merge(empleado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Empleado> listarSinDepartamento() {
        try {
            return em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.departamento IS NULL AND e.activo = true",
                    Empleado.class)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public List<Empleado> listarEmpleadosConSalarioMayorQue(double salario) {
        try {
            return em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.salario > :salario AND e.activo = true ORDER BY e.salario DESC",
                    Empleado.class)
                    .setParameter("salario", salario)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

    @Override
    public List<Empleado> listarEmpleadosContratadosEntre(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            return em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.fechaContratacion BETWEEN :inicio AND :fin AND e.activo = true",
                    Empleado.class)
                    .setParameter("inicio", fechaInicio)
                    .setParameter("fin", fechaFin)
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        }
    }

}
