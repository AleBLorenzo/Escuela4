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

        String sql = "SELECT * FROM autores WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Autor autor = new Autor();

                    autor.setId(rs.getInt("id"));
                    autor.setNombre(rs.getString("nombre"));
                    autor.setApellidos(rs.getString("apellidos"));
                    autor.setNacionalidad(rs.getString("nacionalidad"));
                    autor.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));
                    return autor;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Autor> listarTodos() {

        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Autor autor = new Autor();
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

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Autor.getNombre());
            ps.setString(2, Autor.getApellidos());
            ps.setString(3, Autor.getNacionalidad());
            ps.setObject(4, Autor.getFecha_nacimiento());
            ps.setInt(5, Autor.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM autores WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Autor> buscarPorNacionalidad(String nacionalidad) {

        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores WHERE nacionalidad = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nacionalidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor();
                autor.setId(rs.getInt("id"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellidos(rs.getString("apellidos"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                autor.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));

                lista.add(autor);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    @Override
    public List<Autor> buscarFechaNacimiento(LocalDate fecha_nacimiento) {

        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores WHERE fecha_nacimiento = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, fecha_nacimiento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor();
                autor.setId(rs.getInt("id"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellidos(rs.getString("apellidos"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                autor.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));

                lista.add(autor);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
