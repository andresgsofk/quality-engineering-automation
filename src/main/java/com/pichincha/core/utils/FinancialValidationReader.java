package com.pichincha.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.simulator.models.FinancialValidationData;

import java.io.InputStream;
import java.util.Map;

public class FinancialValidationReader {

    private static final String VALIDATIONS_JSON =
            "/data/financial_form_validations.json";

    private static Map<String, FinancialValidationData> validationDataMap;

    static {

        try {

            ObjectMapper mapper = new ObjectMapper();

            InputStream is =
                    FinancialValidationReader.class
                            .getResourceAsStream(VALIDATIONS_JSON);

            if (is == null) {
                throw new RuntimeException(
                        "No se encontró el archivo JSON en: "
                                + VALIDATIONS_JSON
                );
            }

            validationDataMap = mapper.readValue(
                    is,
                    new TypeReference<Map<String, FinancialValidationData>>() {}
            );

            // ============================
            // DEBUG IMPORTANTE (TEMPORAL)
            // ============================
            System.out.println("\n========== VALIDATION DATA LOADED ==========");
            validationDataMap.keySet()
                    .forEach(key -> System.out.println("ID: " + key));
            System.out.println("===========================================\n");

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error cargando archivo JSON: "
                            + VALIDATIONS_JSON,
                    e
            );
        }
    }

    public static FinancialValidationData getById(String idValidation) {

        if (idValidation == null || idValidation.isBlank()) {
            throw new RuntimeException(
                    "ID de validación es null o vacío"
            );
        }

        FinancialValidationData validation =
                validationDataMap.get(idValidation);

        if (validation == null) {

            System.out.println("\n========== ERROR DEBUG ==========");
            System.out.println("ID SOLICITADO: " + idValidation);
            System.out.println("IDS DISPONIBLES:");
            validationDataMap.keySet()
                    .forEach(System.out::println);
            System.out.println("=================================\n");

            throw new RuntimeException(
                    "No se encontró validación con ID: "
                            + idValidation
            );
        }

        return validation;
    }
}