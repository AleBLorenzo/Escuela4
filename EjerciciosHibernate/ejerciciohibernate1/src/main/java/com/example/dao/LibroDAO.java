package com.example.dao;

import java.util.List;

import com.example.model.Libro;

public interface LibroDAO {

    void guardar(Libro libro);

    Libro buscarPorId(Long id);

    Libro buscarPorIsbn(String isbn);

    List<Libro> listarTodos();

    List<Libro> listarDisponibles();

    List<Libro> buscarPorAutor(String autor);

    void actualizar(Libro libro);

    void eliminar(Long id);

    long contarTotal();

    long contarDisponibles();

    Double precioPromedio();

    Libro libroMasCaro();

    Libro libroMasBarato();

}
