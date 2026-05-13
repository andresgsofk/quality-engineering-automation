package com.pichincha.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.simulator.models.CreditSimulationData;

import java.io.InputStream;
import java.util.Map;

public class JsonReader {

    private static final String CREDITS_JSON = "/data/credit_simulations.json";

    private static Map<String, CreditSimulationData> creditDataMap;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = JsonReader.class.getResourceAsStream(CREDITS_JSON);
            creditDataMap = mapper.readValue(is, new TypeReference<Map<String, CreditSimulationData>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error cargando archivo JSON: " + CREDITS_JSON, e);
        }
    }

    public static CreditSimulationData getById(String idCredito) {
        CreditSimulationData credit = creditDataMap.get(idCredito);
        if (credit == null) {
            throw new RuntimeException("No se encontró el crédito: " + idCredito);
        }
        return credit;
    }
}