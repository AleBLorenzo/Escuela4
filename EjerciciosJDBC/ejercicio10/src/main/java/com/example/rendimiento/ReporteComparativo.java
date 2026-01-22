package com.example.rendimiento;

import java.util.Map;

public class ReporteComparativo {

    private Map<String, Map<String, Long>> resultados;
    private static final int ANCHO_GRAFICO = 50; // Caracteres máximos de la barra

    public ReporteComparativo(Map<String, Map<String, Long>> resultados) {
        this.resultados = resultados;
    }

    public void imprimirReporte() {
        System.out.println("\n============================================================");
        System.out.println("       INFORME DE RENDIMIENTO MULTI-SGBD (Nivel Avanzado)");
        System.out.println("============================================================");

        for (String operacion : resultados.keySet()) {
            System.out.println("\n📂 PRUEBA: " + operacion.toUpperCase());
            System.out.println("------------------------------------------------------------");

            Map<String, Long> tiempos = resultados.get(operacion);

            // Encontrar el tiempo máximo para escalar la gráfica
            long maxTiempo = tiempos.values().stream().max(Long::compare).orElse(1L);
            long mejorTiempo = Long.MAX_VALUE;
            String ganador = "";

            // Formato de tabla
            System.out.printf("%-15s | %-12s | %s%n", "SGBD", "Tiempo (ms)", "Gráfica Relativa");
            System.out.println("----------------+--------------+----------------------------");

            for (Map.Entry<String, Long> entrada : tiempos.entrySet()) {
                String sgbd = entrada.getKey();
                long tiempoNs = entrada.getValue();
                long tiempoMs = tiempoNs / 1_000_000; // Convertir a milisegundos

                // Determinar ganador
                if (tiempoNs < mejorTiempo) {
                    mejorTiempo = tiempoNs;
                    ganador = sgbd;
                }

                // Generar barra ASCII
                int longitudBarra = (int) ((double) tiempoNs / maxTiempo * ANCHO_GRAFICO);
                String barra = "█".repeat(longitudBarra);

                System.out.printf("%-15s | %10d ms | %s%n", sgbd, tiempoMs, barra);
            }

            System.out.println("------------------------------------------------------------");
            System.out.println("🏆 GANADOR: " + ganador.toUpperCase());
        }
        System.out.println("\n============================================================");
    }
}
