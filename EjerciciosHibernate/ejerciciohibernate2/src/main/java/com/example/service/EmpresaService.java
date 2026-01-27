package com.example.service;

import java.time.LocalDate;
import java.util.List;

import com.example.dao.DepartamentoDAO;
import com.example.dao.DepartamentoDAOImpl;
import com.example.dao.EmpleadoDAO;
import com.example.dao.EmpleadoDAOImpl;
import com.example.model.Departamento;
import com.example.model.Empleado;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class EmpresaService {

    public void crearDepartamento(Departamento departamento) throws Exception {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);

        try {
            if (departamentoDAO.buscarPorCodigo(departamento.getCodigo()) != null) {
                throw new Exception("El Codigo ya existe.");
            }

            if (departamento.getPresupuesto() <= 0) {
                throw new Exception("Presupuesto incorrecto.");
            }

            em.getTransaction().begin();
            departamentoDAO.crear(departamento);
            em.getTransaction().commit();

            System.out.println("Departamento registrado con éxito. ID: " + departamento.getId());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }

    }

    public Departamento buscarPorCodigo(String codigo) {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);
        try {

            Departamento Departamento = departamentoDAO.buscarPorCodigo(codigo);

            if (Departamento == null) {
                throw new Exception("Error: El Codigo '" + codigo + "' ya existe.");
            }

            return Departamento;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Departamento> listarTodosDepartamento() {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);

        try {

            List<Departamento> lista = departamentoDAO.listarTodos();

            if (lista.isEmpty()) {
                System.out.println("No hay Departame.");
            }
            return lista;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            em.close();
        }
        return List.of();
    }

    public void actualizarDepartamento(Departamento departamento) {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);
        try {
            em.getTransaction().begin();

            departamentoDAO.actualizar(departamento);

            em.getTransaction().commit();

            System.out.println("Departamento actualizado correctamente en la base de datos.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void eliminarDepartamento(String codigo) {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);

        try {
            em.getTransaction().begin();

            departamentoDAO.eliminar(codigo);

            em.getTransaction().commit();

            System.out.println("Departamento eliminado correctamente en la base de datos.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Empleado> listarEmpleadosDeDepartamento(Departamento departamento) {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);

        try {

            List<Empleado> lista = departamentoDAO.listarEmpleados(departamento);

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados en el Departamento en este momento.");
            }

            return lista;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public Departamento DepartamentoConMasEmpleados() {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);

        try {
            Departamento departamento = departamentoDAO.DepartamentoConMasEmpleados();

            return departamento;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public List<Object[]> salarioPromedioPorDepartamento() {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);
        try {

            List<Object[]> resultados = departamentoDAO.salarioPromedioPorDepartamento();

            return resultados;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public List<Object[]> contarEmpleadosPorDepartamento() {

        EntityManager em = JPAUtil.getEntityManager();
        DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl(em);
        try {

            List<Object[]> resultados = departamentoDAO.contarEmpleadosPorDepartamento();

            return resultados;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    /* Service para EMPLEADOS */

    public void anadirEmpleado(Empleado empleado) throws Exception {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {
            if (empleadoDAO.buscarPorEmail(empleado.getEmail()) != null) {
                throw new Exception("El Email ya existe.");
            }

            if (empleado.getSalario() <= 0) {
                throw new Exception("Salario incorrecto.");
            }

            em.getTransaction().begin();
            empleadoDAO.anadir(empleado);
            em.getTransaction().commit();

            System.out.println("Empleado registrado con éxito. ID: " + empleado.getId());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }

    }

    public Empleado buscarPorEmail(String email) {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {

            Empleado empleado = empleadoDAO.buscarPorEmail(email);

            if (empleado == null) {
                throw new Exception("Error: El email '" + email + "' ya existe.");
            }

            return empleado;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Empleado> listarTodosEmpleados() {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {

            List<Empleado> lista = empleadoDAO.listarTodos();

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados.");
            }
            return lista;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            em.close();
        }
        return List.of();
    }

    public void actualizarEmpleados(Empleado empleado) {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {
            em.getTransaction().begin();

            empleadoDAO.actualizar(empleado);

            em.getTransaction().commit();

            System.out.println("Empleado actualizado correctamente en la base de datos.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void eliminarEmpelados(String email) {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {
            em.getTransaction().begin();

            empleadoDAO.eliminar(email);

            em.getTransaction().commit();

            System.out.println("Empleado eliminado correctamente en la base de datos.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Empleado> listarSinDepartamento() {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {

            List<Empleado> lista = empleadoDAO.listarSinDepartamento();

            if (lista.isEmpty()) {
                System.out.println("No hay Empleados sin Departamento en este momento.");
            }

            return lista;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public void reasignarDepartamento(Empleado empleado, Long iddepartamento) {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {
            em.getTransaction().begin();

            if (empleado.getDepartamento() != null) {

                empleado.getDepartamento().removeEmpleado(empleado); // quitar del viejo
            }
            if (iddepartamento != null) {

                Departamento nuevo = em.find(Departamento.class, iddepartamento);

                nuevo.addEmpleado(empleado); // añadir al nuevo

            } else {

                empleado.setDepartamento(null);
            }
            em.getTransaction().commit();
            
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public List<Empleado> listarEmpleadosConSalarioMayorQue(double salario) {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {

            List<Empleado> resultados = empleadoDAO.listarEmpleadosConSalarioMayorQue(salario);

            return resultados;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public List<Empleado> listarEmpleadosContratadosEntre(LocalDate fechaInicio, LocalDate fechaFin) {

        EntityManager em = JPAUtil.getEntityManager();
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);

        try {

            List<Empleado> resultados = empleadoDAO.listarEmpleadosContratadosEntre(fechaInicio, fechaFin);

            return resultados;

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

}
