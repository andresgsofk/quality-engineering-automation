package com.pichincha.stepdefinitions.api;

import com.pichincha.api.client.FakeStoreApiClient;
import com.pichincha.api.models.ApiScenarioData;
import com.pichincha.api.utils.ApiTestDataReader;
import com.pichincha.api.validations.ApiResponseValidator;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class FakeStoreApiStepDefinitions {

    private static final String DEFAULT_BASE_URL = "https://fakestoreapi.com";

    private FakeStoreApiClient fakeStoreApiClient;
    private Response response;
    private ApiScenarioData currentData;

    @Before(value = "@api", order = 0)
    public void configureApiClient() {
        String baseUrl = System.getProperty("api.base.url", DEFAULT_BASE_URL);
        fakeStoreApiClient = new FakeStoreApiClient(baseUrl);

        System.out.println("\n====================================================");
        System.out.println("API TEST CONFIGURATION");
        System.out.println("Base URL: " + baseUrl);
        System.out.println("====================================================\n");
    }

    @Given("que la API Fake Store se encuentra disponible")
    public void queLaApiFakeStoreSeEncuentraDisponible() {
        System.out.println("API Fake Store lista para ejecución de pruebas.");
    }

    @When("consulto el producto usando data {string}")
    public void consultoElProductoUsandoData(String dataId) {
        currentData = ApiTestDataReader.getScenarioById(dataId);
        response = fakeStoreApiClient.getProductById(currentData.getProductId());
    }

    @When("consulto los productos por categoria usando data {string}")
    public void consultoLosProductosPorCategoriaUsandoData(String dataId) {
        currentData = ApiTestDataReader.getScenarioById(dataId);
        response = fakeStoreApiClient.getProductsByCategory(currentData.getCategory());
    }

    @When("creo un producto usando data {string}")
    public void creoUnProductoUsandoData(String dataId) {
        currentData = ApiTestDataReader.getScenarioById(dataId);
        response = fakeStoreApiClient.createProduct(currentData.getPayload());
    }

    @When("actualizo un producto usando data {string}")
    public void actualizoUnProductoUsandoData(String dataId) {
        currentData = ApiTestDataReader.getScenarioById(dataId);
        response = fakeStoreApiClient.updateProduct(currentData.getProductId(), currentData.getPayload());
    }

    @When("consulto productos con limite usando data {string}")
    public void consultoProductosConLimiteUsandoData(String dataId) {
        currentData = ApiTestDataReader.getScenarioById(dataId);
        response = fakeStoreApiClient.getProductsWithLimit(currentData.getLimit());
    }

    @Then("el status code de la API debe coincidir con la data")
    public void elStatusCodeDeLaApiDebeCoincidirConLaData() {
        ApiResponseValidator.assertStatusCode(response, currentData.getExpectedStatusCode());
    }

    @And("la respuesta debe tener estructura valida del producto consultado")
    public void laRespuestaDebeTenerEstructuraValidaDelProductoConsultado() {
        ApiResponseValidator.assertValidProductStructure(response, currentData.getProductId());
    }

    @And("todos los productos deben pertenecer a la categoria definida en la data")
    public void todosLosProductosDebenPertenecerALaCategoriaDefinidaEnLaData() {
        ApiResponseValidator.assertProductsBelongToCategory(response, currentData.getCategory());
    }

    @And("la respuesta debe coincidir con el producto definido en la data")
    public void laRespuestaDebeCoincidirConElProductoDefinidoEnLaData() {
        ApiResponseValidator.assertProductMatchesPayload(response, currentData.getProductId(), currentData.getPayload());
    }

    @And("la respuesta debe manejar el producto inexistente")
    public void laRespuestaDebeManejarElProductoInexistente() {
        ApiResponseValidator.assertProductNotFound(response);
    }

    @And("la respuesta debe retornar una lista vacia para la categoria invalida")
    public void laRespuestaDebeRetornarUnaListaVaciaParaLaCategoriaInvalida() {
        ApiResponseValidator.assertEmptyCategoryResponse(response);
    }

    @And("la API debe manejar controladamente el payload invalido")
    public void laApiDebeManejarControladamenteElPayloadInvalido() {
        ApiResponseValidator.assertInvalidCreateResponseIsHandled(response);
    }

    @And("la respuesta debe retornar maximo el limite definido en la data")
    public void laRespuestaDebeRetornarMaximoElLimiteDefinidoEnLaData() {
        ApiResponseValidator.assertProductLimit(response, currentData.getLimit());
    }
}
