package com.pichincha.api.validations;

import com.pichincha.api.models.Product;
import com.pichincha.api.models.ProductPayload;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.util.List;

public final class ApiResponseValidator {

    private ApiResponseValidator() {
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        printValidation("STATUS CODE", String.valueOf(expectedStatusCode), String.valueOf(actualStatusCode));

        Assertions.assertThat(actualStatusCode)
                .as("El status code de la respuesta no coincide")
                .isEqualTo(expectedStatusCode);
    }

    public static void assertValidProductStructure(Response response, int expectedProductId) {
        Product product = response.as(Product.class);

        printProductDebug("VALIDACIÓN ESTRUCTURA PRODUCTO", product);

        Assertions.assertThat(product.getId())
                .as("El ID del producto no corresponde")
                .isEqualTo(expectedProductId);

        assertProductBusinessFields(product);
    }

    public static void assertProductsBelongToCategory(Response response, String expectedCategory) {
        List<Product> products = response.jsonPath().getList("", Product.class);

        System.out.println("========== API DEBUG - PRODUCTOS POR CATEGORÍA ==========");
        System.out.println("Categoría esperada: " + expectedCategory);
        System.out.println("Productos retornados: " + products.size());
        System.out.println("=========================================================");

        Assertions.assertThat(products)
                .as("La categoría debe retornar al menos un producto")
                .isNotEmpty();

        for (Product product : products) {
            printProductDebug("PRODUCTO CATEGORÍA", product);

            Assertions.assertThat(product.getCategory())
                    .as("El producto no pertenece a la categoría consultada")
                    .isEqualTo(expectedCategory);

            assertProductBusinessFields(product);
        }
    }

    public static void assertProductMatchesPayload(Response response, Integer expectedProductId, ProductPayload expectedPayload) {
        Product product = response.as(Product.class);

        printProductDebug("VALIDACIÓN PRODUCTO", product);
        assertReturnedPayloadMatchesExpected(product, expectedPayload);

        Assertions.assertThat(product.getId())
                .as("La respuesta debe retornar ID de producto")
                .isNotNull();

        if (expectedProductId != null) {
            Assertions.assertThat(product.getId())
                    .as("El producto debe mantener el ID definido en la data")
                    .isEqualTo(expectedProductId);
        }
    }

    public static void assertProductNotFound(Response response) {
        String body = response.getBody().asString();

        System.out.println("========== API DEBUG - PRODUCTO NO ENCONTRADO ==========");
        System.out.println("Body: " + body);
        System.out.println("=======================================================");

        Assertions.assertThat(body == null || body.isBlank() || body.equals("null") || body.equals("{}"))
                .as("Para producto inexistente se esperaba respuesta vacía, null o {}")
                .isTrue();
    }

    public static void assertEmptyCategoryResponse(Response response) {
        List<Object> products = response.jsonPath().getList("");

        System.out.println("========== API DEBUG - CATEGORÍA INVÁLIDA ==========");
        System.out.println("Elementos retornados: " + products.size());
        System.out.println("===================================================");

        Assertions.assertThat(products)
                .as("Una categoría inexistente debe retornar arreglo vacío")
                .isEmpty();
    }

    public static void assertInvalidCreateResponseIsHandled(Response response) {
        String body = response.getBody().asString();

        System.out.println("========== API DEBUG - CREACIÓN CON PAYLOAD VACÍO ==========");
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Body       : " + body);
        System.out.println("===========================================================");

        Assertions.assertThat(body)
                .as("La respuesta no debe ser nula")
                .isNotBlank();

        Assertions.assertThat(response.jsonPath().getObject("id", Integer.class))
                .as("La API retorna ID aun cuando el payload está vacío; se documenta como comportamiento observado")
                .isNotNull();
    }

    public static void assertProductLimit(Response response, int expectedLimit) {
        List<Product> products = response.jsonPath().getList("", Product.class);

        System.out.println("========== API DEBUG - LÍMITE DE PRODUCTOS ==========");
        System.out.println("Límite solicitado    : " + expectedLimit);
        System.out.println("Productos retornados : " + products.size());
        System.out.println("====================================================");

        Assertions.assertThat(products.size())
                .as("La API debe retornar máximo el límite solicitado")
                .isLessThanOrEqualTo(expectedLimit);

        Assertions.assertThat(products)
                .as("El endpoint limitado debe retornar productos")
                .isNotEmpty();

        for (Product product : products) {
            assertProductBusinessFields(product);
        }
    }

    private static void assertProductBusinessFields(Product product) {
        Assertions.assertThat(product.getId()).as("ID requerido").isNotNull().isGreaterThan(0);
        Assertions.assertThat(product.getTitle()).as("Title requerido").isNotBlank();
        Assertions.assertThat(product.getPrice()).as("Price requerido").isNotNull().isGreaterThan(BigDecimal.ZERO);
        Assertions.assertThat(product.getDescription()).as("Description requerida").isNotBlank();
        Assertions.assertThat(product.getCategory()).as("Category requerida").isNotBlank();
        Assertions.assertThat(product.getImage()).as("Image requerida").isNotBlank();
        Assertions.assertThat(product.getRating()).as("Rating requerido").isNotNull();
        Assertions.assertThat(product.getRating().getRate()).as("Rating.rate requerido").isNotNull().isGreaterThanOrEqualTo(0.0);
        Assertions.assertThat(product.getRating().getCount()).as("Rating.count requerido").isNotNull().isGreaterThanOrEqualTo(0);
    }

    private static void assertReturnedPayloadMatchesExpected(Product product, ProductPayload expectedPayload) {
        Assertions.assertThat(expectedPayload)
                .as("La data del escenario debe tener payload configurado")
                .isNotNull();

        Assertions.assertThat(product.getTitle()).as("Title no coincide").isEqualTo(expectedPayload.getTitle());
        Assertions.assertThat(product.getPrice()).as("Price no coincide").isEqualByComparingTo(expectedPayload.getPrice());
        Assertions.assertThat(product.getDescription()).as("Description no coincide").isEqualTo(expectedPayload.getDescription());
        Assertions.assertThat(product.getImage()).as("Image no coincide").isEqualTo(expectedPayload.getImage());
        Assertions.assertThat(product.getCategory()).as("Category no coincide").isEqualTo(expectedPayload.getCategory());
    }

    private static void printProductDebug(String title, Product product) {
        System.out.println("========== API DEBUG - " + title + " ==========");
        System.out.println("ID          : " + product.getId());
        System.out.println("Title       : " + product.getTitle());
        System.out.println("Price       : " + product.getPrice());
        System.out.println("Category    : " + product.getCategory());
        System.out.println("Image       : " + product.getImage());
        System.out.println("Description : " + product.getDescription());

        if (product.getRating() != null) {
            System.out.println("Rating.rate : " + product.getRating().getRate());
            System.out.println("Rating.count: " + product.getRating().getCount());
        }

        System.out.println("================================================");
    }

    private static void printValidation(String title, String expected, String actual) {
        System.out.println("========== API DEBUG - " + title + " ==========");
        System.out.println("Expected: " + expected);
        System.out.println("Actual  : " + actual);
        System.out.println("===============================================");
    }
}
