package com.example.dao;

import java.util.List;

import com.example.model.Empleado;

public interface EmpleadoDAO {

    void anadir(Empleado empleado);

    List<Empleado> listarTodos();

    Empleado buscarPorEmail(String email);

    void actualizar(Empleado empleado);

    void eliminar(Long id);

    void reasignarDepartamento(Empleado empleado, Long id_departamento);

}
