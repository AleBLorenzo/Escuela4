package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Categoria;
import com.example.util.ConexionDB;

public class CategoriaDAOImpl implements CategoriaDAO {

    private Connection conn;

    public CategoriaDAOImpl() {
        this.conn = ConexionDB.getInstance().getConnection();
    }

    @Override
    public void agregar(Categoria c) {

        String sql = "INSERT INTO categorias (nombre, descripcion) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, c.getNombre());
            st.setString(2, c.getDescripcion());
            st.executeUpdate();

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

    }

    @Override
    public void actualizar(Categoria c) {

        String sql = "UPDATE categorias SET nombre=?, descripcion=? WHERE id=?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, c.getNombre());
            st.setString(2, c.getDescripcion());
            st.setLong(3, c.getId());
            st.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            throw new RuntimeException("Error actualizando categoría", e);
        }
       
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM categorias WHERE id=?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error eliminando categoría", e);
        }
        
    }

    @Override
    public Categoria obtenerPorId(int id) {

        String sql = "SELECT id, nombre, descripcion FROM categorias WHERE id=?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                Categoria c = new Categoria();
                c.setId(rs.getLong("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo categoría", e);
        }
        return null;

    }

    @Override
    public List<Categoria> listarTodos() {

        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM categorias";

        try (PreparedStatement st = conn.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getLong("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error listando categorías", e);
        }

        return lista;

    }

}
