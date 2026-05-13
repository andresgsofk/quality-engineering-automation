package com.pichincha.simulator.parsers;

import java.util.HashMap;
import java.util.Map;

public class FinancialData {

    private FinancialData() {}

    /**
     * Retorna un mapa de datos financieros según el tipo de crédito
     * @param creditKey ejemplo: "preciso_base" o "vivienda_base"
     */
    public static Map<String, String> get(String creditKey) {
        Map<String, String> data = new HashMap<>();

        switch (creditKey) {
            case "preciso_base":
                data.put("amount", "10000");
                data.put("term", "7 meses");
                // data.put("tipoAmortizacion", "Francesa"); // si lo necesitas
                break;
            case "vivienda_base":
                data.put("amount", "120000");
                data.put("term", "240 meses");
                // data.put("tipoAmortizacion", "Francesa");
                break;
            default:
                throw new IllegalArgumentException("Tipo de crédito desconocido: " + creditKey);
        }

        return data;
    }
}