package com.example.app;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.factory.DAOFactory;
import com.example.model.Categoria;
import com.example.rendimiento.MedidorRendimiento;
import com.example.rendimiento.ReporteComparativo;
import com.example.util.ConexionDB;
import com.example.util.ConfiguracionSGBD;


public class AplicacionInventario {
    public static void main(String[] args) {

        System.out.println("=== SISTEMA DE INVENTARIO MULTI-SGBD ===");

        String[] sgdbs = { "sqlite", "mysql", "postgresql" };

        // LinkedHashMap para mantener el orden de inserción
        Map<String, Map<String, Long>> resultados = new LinkedHashMap<>();

        for (String sgbd : sgdbs) {
            System.out.println("\n-------------------------------------------");
            System.out.println("Iniciando pruebas con SGBD: " + sgbd.toUpperCase());

            try {
                // 1. Cambiar conexión dinámicamente
                ConexionDB.getInstance().conectar(sgbd);
                DAOFactory factory = DAOFactory.getInstance();

                // 2. Preparar entorno
                ConfiguracionSGBD.crearTablas();
                factory.limpiarTablas();

                // 3. Insertar datos base (Categorías necesarias para FK)
                factory.getCategoriaDAO().agregar(new Categoria("Base", null, "Para pruebas"));

                MedidorRendimiento medidor = new MedidorRendimiento(factory);

                // --- TEST 1: Inserciones Masivas ---
                int[] lotes = { 1000, 5000 }; // Reducido para ejemplo rápido
                for (int lote : lotes) {
                    System.out.print(" Ejecutando inserción masiva (" + lote + ")... ");
                    long tiempo = medidor.medirInserciones(lote);

                    registrarResultado(resultados, "Inserción " + lote, sgbd, tiempo);
                    System.out.println("OK (" + (tiempo / 1_000_000) + " ms)");
                }

                // --- TEST 2: Consultas Complejas ---
                System.out.print(" Ejecutando consulta compleja... ");
                long tiempoCons = medidor.medirConsultas();
                registrarResultado(resultados, "Consulta Compleja", sgbd, tiempoCons);
                System.out.println("OK");

                // --- TEST 3: Actualizaciones ---
                System.out.print(" Ejecutando updates... ");
                long tiempoUpd = medidor.medirActualizaciones(100);
                registrarResultado(resultados, "Update (100 filas)", sgbd, tiempoUpd);
                System.out.println("OK");

            } catch (Exception e) {
                System.err.println("Error probando " + sgbd + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Generar reporte
        ReporteComparativo reporte = new ReporteComparativo(resultados);
        reporte.imprimirReporte();

        ConexionDB.getInstance().cerrar();
    }

    // Helper para guardar resultados de forma organizada
    private static void registrarResultado(Map<String, Map<String, Long>> resultados,
            String operacion, String sgbd, long tiempo) {
        resultados.computeIfAbsent(operacion, k -> new HashMap<>()).put(sgbd, tiempo);
    }
}