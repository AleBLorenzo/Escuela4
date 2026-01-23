package com.example.dao;

import java.util.List;

import com.example.model.Departamento;
import com.example.model.Empleado;

public interface  DepartamentoDAO {

    void crear(Departamento departamento);

    List<Departamento> listarTodos();

    Departamento buscarPorCodigo(String codigo);

    void actualizar(Departamento departamento);

    void eliminar(String codigo);

    List<Empleado> listarEmpleados(Departamento departamento);

    Departamento DepartamentoConMasEmpleados();

    List<Object[]> salarioPromedioPorDepartamento();

    List<Object[]> contarEmpleadosPorDepartamento();


}
