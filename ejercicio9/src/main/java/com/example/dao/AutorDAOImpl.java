package com.example.dao;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import com.example.model.Autor;

public class AutorDAOImpl implements IAutorDAO {

    private Connection conn;

    public AutorDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Autor Autor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public Autor buscarPorId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    @Override
    public List<Autor> listarTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTodos'");
    }

    @Override
    public void actualizar(Autor Autor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public List<Autor> buscarPorNacionalidad(String nacionalidad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorNacionalidad'");
    }

    @Override
    public List<Autor> buscarFechaNacimiento(LocalDate fecha_nacimiento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarFechaNacimiento'");
    }

}
