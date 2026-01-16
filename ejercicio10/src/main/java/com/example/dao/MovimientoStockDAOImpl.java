package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.model.MovimientoStock;
import com.example.util.ConexionDB;

public class MovimientoStockDAOImpl implements MovimientoStockDAO {

    private Connection conn;

    public MovimientoStockDAOImpl() {
        this.conn = ConexionDB.getInstance().getConnection();
    }

    @Override
    public void agregar(MovimientoStock m) {
        String sql = "INSERT INTO movimientos_stock (producto_id, tipo, cantidad, motivo, fecha) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, m.getProducto_id());
            st.setString(2, m.getTipo());
            st.setInt(3, m.getCantidad());
            st.setString(4, m.getMotivo());
            st.setObject(5, m.getFecha());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error insertando movimiento de stock", e);
        }
    }

    @Override
    public void actualizar(MovimientoStock m) {
        String sql = "UPDATE movimientos_stock SET producto_id = ?, tipo = ?, cantidad = ?, motivo = ?, fecha = ? " +
                "WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, m.getProducto_id());
            st.setString(2, m.getTipo());
            st.setInt(3, m.getCantidad());
            st.setString(4, m.getMotivo());
            st.setObject(5, m.getFecha());
            st.setLong(6, m.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error actualizando movimiento de stock", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM movimientos_stock WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error eliminando movimiento de stock", e);
        }
    }

    @Override
    public MovimientoStock obtenerPorId(int id) {
        String sql = "SELECT id, producto_id, tipo, cantidad, motivo, fecha " +
                "FROM movimientos_stock WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    MovimientoStock m = new MovimientoStock();
                    m.setId(rs.getLong("id"));
                    m.setProducto_id(rs.getLong("producto_id"));
                    m.setTipo(rs.getString("tipo"));
                    m.setCantidad(rs.getInt("cantidad"));
                    m.setMotivo(rs.getString("motivo"));
                    m.setFecha(rs.getObject("fecha", LocalDate.class));
                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo movimiento de stock", e);
        }

        return null;
    }

    @Override
    public List<MovimientoStock> listarTodos() {
        List<MovimientoStock> lista = new ArrayList<>();
        String sql = "SELECT id, producto_id, tipo, cantidad, motivo, fecha FROM movimientos_stock";

        try (PreparedStatement st = conn.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                MovimientoStock m = new MovimientoStock();
                m.setId(rs.getLong("id"));
                m.setProducto_id(rs.getLong("producto_id"));
                m.setTipo(rs.getString("tipo"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setMotivo(rs.getString("motivo"));
                m.setFecha(rs.getObject("fecha", LocalDate.class));
                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error listando movimientos de stock", e);
        }

        return lista;
    }

}
