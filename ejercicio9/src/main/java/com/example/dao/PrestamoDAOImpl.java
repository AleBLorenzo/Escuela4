package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.example.model.Prestamo;

public class PrestamoDAOImpl implements IPrestamoDAO {

    private Connection conn;

    public PrestamoDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void registrarPrestamo(Prestamo Prestamo) {
        String sqlInsertPrestamo = """
                    INSERT INTO prestamos (libro_id, nombre_usuario, fecha_prestamo, fecha_devolucion_esperada, devuelto)
                    VALUES (?, ?, ?, ?, ?)
                """;
        String sqlActualizarLibro = "UPDATE libros SET disponible = false WHERE id = ?";

        try {
            // Desactivar autocommit para manejar la transacción manualmente
            conn.setAutoCommit(false);

            // Calcular fechas
            LocalDate hoy = LocalDate.now();
            LocalDate fechaDevolucion = hoy.plusDays(14); // préstamo por 14 días

            Prestamo.setFecha_prestamo(hoy);
            Prestamo.setFecha_devolucion_esperada(fechaDevolucion);
            Prestamo.setDevuelto(false);

            // 1. Insertar préstamo
            try (PreparedStatement psPrestamo = conn.prepareStatement(sqlInsertPrestamo)) {
                psPrestamo.setInt(1, Prestamo.getLibro_id());
                psPrestamo.setString(2, Prestamo.getNombre_usuario());
                psPrestamo.setObject(3, Prestamo.getFecha_prestamo());
                psPrestamo.setObject(4, Prestamo.getFecha_devolucion_esperada());
                psPrestamo.setBoolean(5, Prestamo.isDevuelto());
                psPrestamo.executeUpdate();
            }

            // 2. Marcar libro como no disponible
            try (PreparedStatement psLibro = conn.prepareStatement(sqlActualizarLibro)) {
                psLibro.setInt(1, Prestamo.getLibro_id());
                psLibro.executeUpdate();
            }

            // 3. Confirmar transacción
            conn.commit();
            System.out.println("Préstamo registrado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // revertir cambios si algo falla
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true); // reactivar autocommit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Prestamo buscarPorUsuario(String nombre_usuario) {

        String sql = "SELECT * FROM prestamos WHERE nombre_usuario = ? ORDER BY fecha_prestamo DESC LIMIT 1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre_usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prestamo p = new Prestamo();
                    p.setId(rs.getInt("id"));
                    p.setLibro_id(rs.getInt("libro_id"));
                    p.setNombre_usuario(rs.getString("nombre_usuario"));
                    p.setFecha_prestamo(rs.getObject("fecha_prestamo", LocalDate.class));
                    p.setFecha_devolucion_esperada(rs.getObject("fecha_devolucion_esperada", LocalDate.class));
                    p.setFecha_devolucion_real(rs.getObject("fecha_devolucion_real", LocalDate.class));
                    p.setDevuelto(rs.getBoolean("devuelto"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Prestamo> listarTodos() {
        String sql = "SELECT * FROM prestamos";
    }

    @Override
    public void actualizar(Prestamo Prestamo) {
        String sql = "UPDATE prestamos SET libro_id=?, nombre_usuario=?, fecha_prestamo=?, fecha_devolucion_esperada=?, fecha_devolucion_real=?, devuelto=? WHERE id=?";

    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM prestamos WHERE id=?";

    }

    @Override
    public void registrarDevolucion(int id) {

        String sql = "UPDATE prestamos SET fecha_devolucion_real=?, devuelto=true WHERE id=?";

    }

    @Override
    public List<Prestamo> listarPrestamosActivos() {

        String sql = "SELECT * FROM prestamos WHERE devuelto=false";
    }

    @Override
    public List<Prestamo> listarHistorialPrestamos() {
        return listarTodos();
    }

    @Override
    public List<Prestamo> buscarPrestamosPorUsuario(String nombre_usuario) {

        String sql = "SELECT * FROM prestamos WHERE nombre_usuario=?";

    }

}
