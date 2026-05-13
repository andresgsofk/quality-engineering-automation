package com.pichincha.stepdefinitions.web.comparison;

import com.pichincha.simulator.models.comparison.ProductComparisonResult;
import com.pichincha.simulator.tasks.comparison.CompareCreditProducts;
import com.pichincha.simulator.validations.comparison.ProductComparisonValidator;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;

public class ProductComparisonStepDefinitions {

    @When("el usuario compara el producto {string} contra el producto {string}")
    public void elUsuarioComparaElProductoContraElProducto(String firstScenarioId,
                                                           String secondScenarioId) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                CompareCreditProducts.between(firstScenarioId, secondScenarioId)
        );
    }

    @Then("se validan las diferencias financieras y condiciones entre los productos")
    public void seValidanLasDiferenciasFinancierasYCondicionesEntreLosProductos() {
        ProductComparisonResult comparisonResult = OnStage.theActorInTheSpotlight()
                .recall(ProductComparisonResult.MEMORY_KEY);

        ProductComparisonValidator.validate(comparisonResult);
    }
}
