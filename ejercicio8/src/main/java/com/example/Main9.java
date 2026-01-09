package com.example;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main9 {
    public static void main(String[] args) {

        try {
            String dirrecion = "jdbc:postgresql://100.87.253.157:5432/instituto";
            String user = "admin";
            String pass = "admin123";

            Class.forName("org.postgresql.Driver");

            Connection conex = null;

            try {

                conex = DriverManager.getConnection(dirrecion, user, pass);

                Statement senteciacreate = conex.createStatement();

                senteciacreate.executeUpdate("CREATE TABLE IF NOT EXISTS cuentas (\n" + //
                        "    id SERIAL PRIMARY KEY,\n" + //
                        "    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,\n" + //
                        "    titular VARCHAR(100) NOT NULL,\n" + //
                        "    saldo DECIMAL(12,2) NOT NULL DEFAULT 0.00,\n" + //
                        "    fecha_apertura DATE DEFAULT CURRENT_DATE,\n" + //
                        "    activa BOOLEAN DEFAULT TRUE,\n" + //
                        "    CONSTRAINT saldo_positivo CHECK (saldo >= 0)\n" + //
                        ");\n" + //
                        "\n" + //
                        "CREATE TABLE IF NOT EXISTS movimientos (\n" + //
                        "    id SERIAL PRIMARY KEY,\n" + //
                        "    cuenta_origen_id INTEGER REFERENCES cuentas(id),\n" + //
                        "    cuenta_destino_id INTEGER REFERENCES cuentas(id),\n" + //
                        "    concepto VARCHAR(200),\n" + //
                        "    importe DECIMAL(12,2) NOT NULL,\n" + //
                        "    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" + //
                        "    estado VARCHAR(20) DEFAULT 'COMPLETADO',\n" + //
                        "    CONSTRAINT importe_positivo CHECK (importe > 0)\n" + //
                        ");");

                senteciacreate
                        .executeUpdate("INSERT INTO cuentas (numero_cuenta, titular, saldo, fecha_apertura, activa)\n" + //
                                "VALUES\n" + //
                                "('ES0001', 'Ana Martínez', 1500.00, CURRENT_DATE, TRUE),\n" + //
                                "('ES0002', 'Carlos López', 320.50, CURRENT_DATE, TRUE),\n" + //
                                "('ES0003', 'María Gómez', 8750.75, CURRENT_DATE, TRUE)\n" + //
                                "ON CONFLICT (numero_cuenta) DO NOTHING;");

                conex.setAutoCommit(false);

                // OPERACIONES DE LA TRANSACCIÓN
                // ... (varias operaciones)

                BigDecimal importe = new BigDecimal("150.00");

                String consutasaldo = """
                            SELECT saldo FROM cuentas
                            WHERE id = ? AND activa = TRUE
                            FOR UPDATE
                        """;

                BigDecimal saldoOrigen;
                int cuentaOrigenId = 1;

                try (PreparedStatement statemnet = conex.prepareStatement(consutasaldo)) {
                    statemnet.setInt(1, cuentaOrigenId);

                    ResultSet resultSet = statemnet.executeQuery();

                    if (!resultSet.next()) {

                        throw new SQLException("Cuenta origen no existe o está inactiva");

                    }

                    saldoOrigen = resultSet.getBigDecimal("saldo");

                    System.out.println("→ Verificando cuenta origen... ✓");

                    BigDecimal saldodestino;
                    int cuentaDestinoId = 2;

                    try (PreparedStatement statemnet1 = conex.prepareStatement(consutasaldo)) {
                        statemnet1.setInt(1, cuentaDestinoId);

                        ResultSet resultSet1 = statemnet1.executeQuery();

                        if (!resultSet1.next()) {

                            throw new SQLException("Cuenta destino no existe o está inactiva");

                        }

                        saldodestino = resultSet1.getBigDecimal("saldo");
                    }
                    System.out.println("  → Verificando cuenta destino... ✓");

                    if (saldoOrigen.compareTo(importe) < 0) {

                        throw new SQLException("Saldo insuficiente");

                    }

                    System.out.println("→ Verificando saldo suficiente... ✓");

                    // Restar saldo origen
                    try (PreparedStatement ps = conex.prepareStatement(
                            "UPDATE cuentas SET saldo = saldo - ? WHERE id = ?")) {
                        ps.setBigDecimal(1, importe);
                        ps.setInt(2, cuentaOrigenId);
                        ps.executeUpdate();
                    }

                    System.out.println("→ Actualizando cuenta origen... ✓ ");

                    // Sumar saldo destino
                    try (PreparedStatement ps = conex.prepareStatement(
                            "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?")) {
                        ps.setBigDecimal(1, importe);
                        ps.setInt(2, cuentaDestinoId);
                        ps.executeUpdate();
                    }

                    System.out.println("→ Actualizando cuenta destino... ✓");

                    // Registrar movimiento
                    try (PreparedStatement ps = conex.prepareStatement(
                            """
                                    INSERT INTO movimientos
                                    (cuenta_origen_id, cuenta_destino_id, concepto, importe)
                                    VALUES (?, ?, ?, ?)
                                    """)) {

                        ps.setInt(1, cuentaOrigenId);
                        ps.setInt(2, cuentaDestinoId);
                        ps.setString(3, "comida");
                        ps.setBigDecimal(4, importe);
                        ps.executeUpdate();
                    }

                    System.out.println("→ Registrando movimiento... ✓");
                }
                // SI TODO OK → COMMIT
                conex.commit();

                System.out.println("[COMMIT REALIZADO]");

            } catch (SQLException e) {
                // SI ERROR → ROLLBACK
                if (conex != null) {
                    try {
                        conex.rollback();
                    } catch (SQLException ex) {
                        // Log del error de rollback
                    }
                }
            } finally {
                // RESTAURAR AUTOCOMMIT
                if (conex != null) {
                    conex.setAutoCommit(true);
                    conex.close();
                }
            }

        } catch (SQLException ex) {
            System.getLogger(Main9.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}