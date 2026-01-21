package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Libro;

public class LibroDAOImpl implements ILibroDAO {

    private Connection conn;

    public LibroDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Libro libro) {

        String sql = "INSERT INTO libros (titulo, isbn, autor_id, genero, anio_publicacion, disponible) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setInt(3, libro.getAutorId());
            ps.setString(4, libro.getGenero());
            ps.setInt(5, libro.getAnioPublicacion());
            ps.setBoolean(6, libro.isDisponible());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Libro buscarPorISBN(String isbn) {

        String sql = "SELECT * FROM libros WHERE isbn = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setIsbn(rs.getString("isbn"));
                    libro.setAutorId(rs.getInt("autor_id"));
                    libro.setGenero(rs.getString("genero"));
                    libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                    libro.setDisponible(rs.getBoolean("disponible"));
                    return libro;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Libro> listarTodos() {

        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAutorId(rs.getInt("autor_id"));
                libro.setGenero(rs.getString("genero"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                libro.setDisponible(rs.getBoolean("disponible"));
                lista.add(libro);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;

    }

    @Override
    public void actualizar(Libro libro) {

         String sql = """
            UPDATE libros
            SET titulo = ?, autor_id = ?, genero = ?, anio_publicacion = ?, disponible = ?
            WHERE isbn = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getAutorId());
            ps.setString(3, libro.getGenero());
            ps.setInt(4, libro.getAnioPublicacion());
            ps.setBoolean(5, libro.isDisponible());
            ps.setString(6, libro.getIsbn());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    @Override
    public List<Libro> buscarPorAutor(int autorId) {

          List<Libro> lista = new ArrayList<>();

        String sql = "SELECT * FROM libros WHERE autor_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, autorId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Libro libro = new Libro();
                    libro.setId(rs.getInt("id"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setIsbn(rs.getString("isbn"));
                    libro.setAutorId(rs.getInt("autor_id"));
                    libro.setGenero(rs.getString("genero"));
                    libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                    libro.setDisponible(rs.getBoolean("disponible"));
                    lista.add(libro);

                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Libro> buscarDisponibles() {

       List<Libro> lista = new ArrayList<>();

        String sql = "SELECT * FROM libros WHERE disponible = true";

        try (PreparedStatement ps = conn.prepareStatement(sql);

             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAutorId(rs.getInt("autor_id"));
                libro.setGenero(rs.getString("genero"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                libro.setDisponible(rs.getBoolean("disponible"));
                lista.add(libro);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }
}

