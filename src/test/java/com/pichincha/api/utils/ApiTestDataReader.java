package com.pichincha.api.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.api.models.ApiScenarioData;

import java.io.InputStream;
import java.util.Map;

public final class ApiTestDataReader {

    private static final String API_DATA_JSON = "/data/api/api_products_data.json";
    private static final Map<String, ApiScenarioData> API_DATA = loadApiData();

    private ApiTestDataReader() {
    }

    public static ApiScenarioData getScenarioById(String id) {
        ApiScenarioData data = API_DATA.get(id);

        if (data == null) {
            throw new IllegalArgumentException("No existe data API configurada con ID: " + id);
        }

        printLoadedData(id, data);
        return data;
    }

    private static Map<String, ApiScenarioData> loadApiData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = ApiTestDataReader.class.getResourceAsStream(API_DATA_JSON);

            if (inputStream == null) {
                throw new IllegalStateException("No se encontró el archivo: " + API_DATA_JSON);
            }

            return mapper.readValue(inputStream, new TypeReference<Map<String, ApiScenarioData>>() {});
        } catch (Exception exception) {
            throw new RuntimeException("Error cargando datos API desde: " + API_DATA_JSON, exception);
        }
    }

    private static void printLoadedData(String id, ApiScenarioData data) {
        System.out.println("\n========== API DATA LOADED ==========");
        System.out.println("ID              : " + id);
        System.out.println("Product ID      : " + data.getProductId());
        System.out.println("Category        : " + data.getCategory());
        System.out.println("Limit           : " + data.getLimit());
        System.out.println("Expected status : " + data.getExpectedStatusCode());
        System.out.println("Payload defined : " + data.hasPayload());
        System.out.println("=====================================\n");
    }
}
