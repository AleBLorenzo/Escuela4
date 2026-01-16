package com.example.rendimiento;

import java.util.Map;

public class ReporteComparativo {

    // Map<Operación, Map<SGBD, tiempo>>
    private Map<String, Map<String, Long>> resultados;

    public ReporteComparativo(Map<String, Map<String, Long>> resultados) {
        this.resultados = resultados;
    }

    public void imprimirReporte() {
        System.out.println("=== REPORTE COMPARATIVO DE RENDIMIENTO ===");
        System.out.println();

        for (String operacion : resultados.keySet()) {
            System.out.println("Operación: " + operacion);
            Map<String, Long> datos = resultados.get(operacion);
            long mejorTiempo = Long.MAX_VALUE;
            String ganador = "";
            for (String sgbd : datos.keySet()) {
                long tiempoNs = datos.get(sgbd);
                double tiempoSeg = tiempoNs / 1_000_000_000.0;
                System.out.printf("  %s: %.3f segundos%n", sgbd, tiempoSeg);
                if (tiempoNs < mejorTiempo) {
                    mejorTiempo = tiempoNs;
                    ganador = sgbd;
                }
            }
            System.out.println("  🏆 Ganador: " + ganador);
            System.out.println();
        }
    }
}
