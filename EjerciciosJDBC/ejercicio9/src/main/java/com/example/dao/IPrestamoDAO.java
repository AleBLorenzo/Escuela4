package com.example.dao;

import java.util.List;

import com.example.model.Prestamo;

public interface IPrestamoDAO {

    void registrarPrestamo(Prestamo Prestamo);

    Prestamo buscarPorUsuario(String nombre_usuario);

    List<Prestamo> listarTodos();

    void actualizar(Prestamo Prestamo);

    void eliminar(int id);

    void registrarDevolucion(int id);

    List<Prestamo> listarPrestamosActivos();

    List<Prestamo> listarHistorialPrestamos();

    List<Prestamo> buscarPrestamosPorUsuario(String nombre_usuario);

}
