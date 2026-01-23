package com.example.dao;

import java.time.LocalDate;
import java.util.List;

import com.example.model.Empleado;

public interface EmpleadoDAO {

    void anadir(Empleado empleado);

    List<Empleado> listarTodos();

    Empleado buscarPorEmail(String email);

    void actualizar(Empleado empleado);

    void eliminar(String email);

    void reasignarDepartamento(Empleado empleado, Long id_departamento);

    List<Empleado> listarSinDepartamento();

    List<Empleado> listarEmpleadosConSalarioMayorQue(double salario);

    List<Empleado> listarEmpleadosContratadosEntre(LocalDate fechaInicio, LocalDate fechaFin);

}
