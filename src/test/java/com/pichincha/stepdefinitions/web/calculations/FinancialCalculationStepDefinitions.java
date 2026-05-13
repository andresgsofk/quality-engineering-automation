package com.pichincha.stepdefinitions.web.calculations;

import com.pichincha.simulator.tasks.ValidateFinancialCalculations;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;

public class FinancialCalculationStepDefinitions {

    @Then("se validan los calculos financieros de la simulacion {string}")
    public void seValidanLosCalculosFinancierosDeLaSimulacion(String idCredito) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                ValidateFinancialCalculations.forScenario(idCredito)
        );
    }
}
