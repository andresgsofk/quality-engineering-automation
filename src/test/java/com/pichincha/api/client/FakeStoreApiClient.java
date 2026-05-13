package com.pichincha.api.client;

import com.pichincha.api.constants.ApiEndpoints;
import com.pichincha.api.models.ProductPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;


public class FakeStoreApiClient {

    private final String baseUrl;

    public FakeStoreApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response getProductById(int productId) {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .pathParam("id", productId)
                .log().all()
                .when()
                .get(ApiEndpoints.PRODUCT_BY_ID)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getProductsByCategory(String category) {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .pathParam("category", category)
                .log().all()
                .when()
                .get(ApiEndpoints.PRODUCTS_BY_CATEGORY)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response createProduct(ProductPayload payload) {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .log().all()
                .when()
                .post(ApiEndpoints.PRODUCTS)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response updateProduct(int productId, ProductPayload payload) {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("id", productId)
                .body(payload)
                .log().all()
                .when()
                .put(ApiEndpoints.PRODUCT_BY_ID)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getProductsWithLimit(int limit) {
        return SerenityRest
                .given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .queryParam("limit", limit)
                .log().all()
                .when()
                .get(ApiEndpoints.PRODUCTS)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
