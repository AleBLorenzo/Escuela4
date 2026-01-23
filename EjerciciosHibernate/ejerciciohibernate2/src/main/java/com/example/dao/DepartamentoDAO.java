package com.example.dao;

import java.util.List;

import com.example.model.Departamento;
import com.example.model.Empleado;

public interface  DepartamentoDAO {

    void crear(Departamento departamento);

    List<Departamento> listarTodos();

    Departamento buscarPorCodigo(Long id);

    void actualizar(Departamento departamento);

    void eliminar(Long id);

    List<Empleado> listarEmpleados(Departamento departamento);

}
