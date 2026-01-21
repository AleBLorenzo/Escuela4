package com.example.dao;

import java.time.LocalDate;
import java.util.List;

import com.example.model.Autor;

public interface IAutorDAO {

    void insertar(Autor Autor);

    Autor buscarPorId(int id);

    List<Autor> listarTodos();

    void actualizar(Autor Autor);

    void eliminar(int id);

    List<Autor> buscarPorNacionalidad(String nacionalidad);

    List<Autor> buscarFechaNacimiento(LocalDate fecha_nacimiento);

}
