package com.example.dao;

import java.util.List;

import com.example.model.Libro;

public interface ILibroDAO {

    void insertar(Libro libro);

    Libro buscarPorISBN(String ISBN);

    List<Libro> listarTodos();

    void actualizar(Libro libro);

    void eliminar(int id);

    List<Libro> buscarPorAutor(int autorId);

    List<Libro> buscarDisponibles();

}
