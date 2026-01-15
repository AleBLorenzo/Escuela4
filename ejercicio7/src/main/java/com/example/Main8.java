package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main8 {
    public static void main(String[] args) {

        String user = "usuario";
        String password = "usuario123";
        String urlnativo = "jdbc:mysql://localhost:3306/";
        String urlNoTimezone = "jdbc:mysql://localhost:3306/academia";
        String url = urlNoTimezone + "?serverTimezone=UTC";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conexion = DriverManager.getConnection(url, user, password)) {

                System.out.println("Conexión establecida exitosamente");

                Statement sentecnia = creartablas(conexion);

                logininseguro(conexion);

                loginseguro(user, password, conexion);

                borradoinicial(conexion);

                insertar(conexion);

                update(conexion);

                delete(conexion);

                selectlike(conexion);

            } catch (SQLException e) {

                if (e.getMessage().contains("Access denied")) {

                    System.out.println("Error de autenticación en MySQL");
                    System.out.println("Access denied for user ");
                    e.printStackTrace();

                } else {

                    System.out.println("No se pudo conectar al servidor MySQL");
                    System.out.println("Causa: ");

                    System.getLogger(Main8.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);

                }

            }

            System.out.println("Conexión cerrada correctamente");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    private static void borradoinicial(Connection conexion) throws SQLException {
        try (Statement setnecia = conexion.createStatement()) {
            setnecia.executeUpdate("DELETE FROM usuarios");
        }
    }

    private static void selectlike(Connection conexion) throws SQLException {

        System.out.println("\n=== BÚSQUEDA DE USUARIOS ===");
        System.out.println("Patrón de búsqueda: %car%\n");

        String selectfiltro = "SELECT * FROM usuarios WHERE activo=? AND username LIKE ?";

        PreparedStatement pst = conexion.prepareStatement(selectfiltro);

        int contador = 0;

        pst.setBoolean(1, true);
        pst.setString(2, "%car%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            contador++;
            System.out.println("  - " + rs.getString("username") +
                    " (" + rs.getString("email") + ")");
        }

        System.out.println("Total: " + contador + " usuarios");
    }

    private static void delete(Connection conexion) throws SQLException {

        System.out.println("\nEliminando usuario con id 2");

        String delete = "DELETE FROM usuarios WHERE id=?";

        PreparedStatement pst = conexion.prepareStatement(delete);

        pst.setInt(1, 2);

        int filas = pst.executeUpdate();
        System.out.println("→ Filas eliminadas: " + filas);

    }

    private static Statement creartablas(final Connection conexion) throws SQLException {

        Statement sentecnia = conexion.createStatement();

        sentecnia.executeUpdate("CREATE TABLE  IF NOT EXISTS usuarios (\n" + //
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" + //
                "    username VARCHAR(50) UNIQUE NOT NULL,\n" + //
                "    password VARCHAR(255) NOT NULL,\n" + //
                "    email VARCHAR(100),\n" + //
                "    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" + //
                "    activo BOOLEAN DEFAULT TRUE\n" + //
                ");");

        return sentecnia;
    }

    private static void logininseguro(Connection conexion) throws SQLException {

        System.out.println("\nSQL Injection con Statement (vulnerable)");
        System.out.println("**Entrada**: \n" + //
                "- Username: `' OR '1'='1' --`\n" + //
                "- Password: `ignorado`\n" + //
                "");

        String sql = "SELECT * FROM usuarios WHERE username='" + "' OR '1'='1' -- " +
                "' AND password='" + "ignorado" + "'";

        try (Statement sentecnias = conexion.createStatement();
                ResultSet resuto = sentecnias.executeQuery(sql)) {

            if (resuto.next()) {
                System.out.println("⚠️ ALERTA: ¡SQL INJECTION EXITOSO!\n" + //
                        "✗ Login exitoso sin credenciales válidas\n" + //
                        "→ Se obtuvieron todos los usuarios de la base de datos\n" + //
                        "→ ESTO ES UNA VULNERABILIDAD CRÍTICA");
            } else {

                System.out.println("login fallido");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void loginseguro(String user, String password, Connection conexion) throws SQLException {

        System.out.println("\nIntento de SQL Injection con PreparedStatement (seguro)");

        System.out.println("**Entrada**:\n" + //
                "- Username: `' OR '1'='1' --`\n" + //
                "- Password: `ignorado`");

        String sqlok = "SELECT * FROM usuarios WHERE username=? AND password=?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sqlok)) {

            pstmt.setString(1, "' OR '1'='1' -- ");
            pstmt.setString(2, "ignorado");

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    System.out.println("\nLogin exitoso");

                } else {

                    System.out.println("\nResultado: Login fallido");
                    System.out.println("→ No se encontró ningún usuario");
                    System.out.println("→ SQL INJECTION BLOQUEADO CORRECTAMENTE");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void update(Connection conexion) throws SQLException {

        System.out.println("\nActualización de datos");
        System.out.println("Actualizando email de: carlos");

        int filas = 0;

        String update = "UPDATE usuarios SET email=?, activo=? WHERE username=?";

        PreparedStatement pstmt2 = conexion.prepareStatement(update);

        pstmt2.setString(1, "carlos.nuevo@email.com");
        pstmt2.setBoolean(2, true);
        pstmt2.setString(3, "carlos");
        filas += pstmt2.executeUpdate();

        System.out.println("Nuevo email: carlos.nuevo@email.com");
        System.out.println("✓ Usuario actualizado correctamente");
        System.out.println("→ Filas afectadas: " + filas);

    }

    private static void insertar(Connection conexion) throws SQLException {

        System.out.println(" \nInserción de usuario con PreparedStatement");

        String insert = "INSERT INTO usuarios (username, password, email) VALUES (?, ?, ?)";

        PreparedStatement pstmt1 = conexion.prepareStatement(insert);

        int totalInsertados = 0;

        pstmt1.setString(1, "carlos");
        pstmt1.setString(2, "carlos123");
        pstmt1.setString(3, "carlos123@gmail.com");
        totalInsertados += pstmt1.executeUpdate();

        pstmt1.setString(1, "ana");
        pstmt1.setString(2, "abcd");
        pstmt1.setString(3, "ana@email.com");
        totalInsertados += pstmt1.executeUpdate();

        pstmt1.setString(1, "luis");
        pstmt1.setString(2, "pass");
        pstmt1.setString(3, "luis@email.com");
        totalInsertados += pstmt1.executeUpdate();

        System.out.println("✓ Usuarios insertados correctamente");
        System.out.println("→ Total de filas insertadas: " + totalInsertados);

    }

}
