package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Producto;
import com.example.util.ConexionDB;

public class ProductoDAOImpl implements ProductoDAO {

    private Connection conn;

    public ProductoDAOImpl() {
        this.conn = ConexionDB.getInstance().getConnection();
    }

    @Override
    public void agregar(Producto p) {

        String sql = "INSERT INTO productos (codigo, nombre, categoria_id, precio, stock, fecha_alta, activo) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, p.getCodigo());
            st.setString(2, p.getNombre());
            st.setLong(3, p.getCategoria_id());
            st.setBigDecimal(4, p.getPrecio());
            st.setInt(5, p.getStock());
            st.setObject(6, p.getFecha_alta());
            st.setBoolean(7, p.isActivo());
            st.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void actualizar(Producto p) {
        String sql = "UPDATE productos SET codigo = ?, nombre = ?, categoria_id = ?, precio = ?, stock = ?, fecha_alta = ?, activo = ? "
                +
                "WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, p.getCodigo());
            st.setString(2, p.getNombre());
            st.setLong(3, p.getCategoria_id());
            st.setBigDecimal(4, p.getPrecio());
            st.setInt(5, p.getStock());
            st.setObject(6, p.getFecha_alta());
            st.setBoolean(7, p.isActivo());
            st.setLong(8, p.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT id, codigo, nombre, categoria_id, precio, stock, fecha_alta, activo " +
                "FROM productos WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {

            st.setLong(1, id);

            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getLong("id"));
                    p.setCodigo(rs.getString("codigo"));
                    p.setNombre(rs.getString("nombre"));
                    p.setCategoria_id(rs.getLong("categoria_id"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setStock(rs.getInt("stock"));
                    p.setFecha_alta(rs.getObject("fecha_alta", LocalDate.class));
                    p.setActivo(rs.getBoolean("activo"));
                    return p;
                }

            } catch (Exception e) {
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Producto> listarTodos() {

        List<Producto> pros = new ArrayList<>();
        
        String sql = "SELECT id, codigo, nombre, categoria_id, precio, stock, fecha_alta, activo " +
                "FROM productos";

        try (PreparedStatement st = conn.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {

            while (rs.next()) {

                Producto p = new Producto();

                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria_id(rs.getLong("categoria_id"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setFecha_alta(rs.getObject("fecha_alta", LocalDate.class));
                p.setActivo(rs.getBoolean("activo"));

                pros.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error listando categorías", e);
        }

        return pros;
    }

}
