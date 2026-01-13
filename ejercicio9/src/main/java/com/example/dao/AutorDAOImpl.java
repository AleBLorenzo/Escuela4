package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Autor;

public class AutorDAOImpl implements IAutorDAO {

    private Connection conn;

    public AutorDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Autor Autor) {

        String sql = "INSERT INTO autores (nombre, apellidos, nacionalidad, fecha_nacimiento) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statemnet = conn.prepareStatement(sql)) {

            statemnet.setString(1, Autor.getNombre());
            statemnet.setString(2, Autor.getApellidos());
            statemnet.setString(3, Autor.getNacionalidad());
            statemnet.setObject(4, Autor.getFecha_nacimiento());

            statemnet.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Override
    public Autor buscarPorId(int id) {

        Autor autor = new Autor();
        String sql = "SELECT * FROM autores WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            ps.setInt(1, id);

            while (rs.next()) {

                autor.setId(rs.getInt("id"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellidos(rs.getString("apellidos"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                autor.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));

                return autor;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Autor> listarTodos() {

        Autor autor = new Autor();
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                
                autor.setId(rs.getInt("id"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellidos(rs.getString("apellidos"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                autor.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));

                lista.add(autor);

            }

            return lista;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void actualizar(Autor Autor) {

        String sql = """
                UPDATE autores
                SET nombre = ?,
                    apellidos = ?,
                    nacionalidad = ?,
                    fecha_nacimiento = ?
                WHERE id = ?
                """;

    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM autores WHERE id = ?";

    }

    @Override
    public List<Autor> buscarPorNacionalidad(String nacionalidad) {

        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores WHERE nacionalidad = ?";
        return lista;

    }

    @Override
    public List<Autor> buscarFechaNacimiento(LocalDate fecha_nacimiento) {

        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores WHERE fecha_nacimiento = ?";
        return lista;

    }

}
