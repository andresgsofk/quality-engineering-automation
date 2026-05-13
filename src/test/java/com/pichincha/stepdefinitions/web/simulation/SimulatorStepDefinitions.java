package com.pichincha.stepdefinitions.web.simulation;

import com.pichincha.simulator.models.CreditSimulationData;
import com.pichincha.simulator.tasks.EnterFinancialInfo;
import com.pichincha.simulator.tasks.SelectCreditType;
import com.pichincha.simulator.tasks.ValidateAmortization;
import com.pichincha.core.utils.JsonReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;

public class SimulatorStepDefinitions {

    // =========================================================
    // SELECT CREDIT (UNIFICADO Y NECESARIO)
    // =========================================================


    @When("el usuario selecciona el credito por ID {string}")
    public void seleccionaCreditoPorId(String idCredito) {

        CreditSimulationData creditData =
                JsonReader.getById(idCredito);

        if (creditData == null) {
            throw new RuntimeException("No se encontró el crédito: " + idCredito);
        }

        OnStage.theActorInTheSpotlight()
                .attemptsTo(
                        SelectCreditType.from(creditData)
                );
    }

    // =========================================================
    // INGRESO FINANCIERO
    // =========================================================
    @And("ingresa la informacion financiera del simulador {string}")
    public void ingresaLaInformacionFinanciera(String idCredito) {

        CreditSimulationData creditData =
                JsonReader.getById(idCredito);

        OnStage.theActorInTheSpotlight()
                .attemptsTo(
                        EnterFinancialInfo.from(creditData)
                );
    }

    // =========================================================
    // VALIDACIÓN FINAL
    // =========================================================
    @Then("se genera la simulacion del credito correctamente")
    public void seGeneraLaSimulacionDelCreditoCorrectamente() {
        System.out.println("✔ Simulación generada correctamente");
    }

    @And("se valida la cuota mensual, tasa de interes y tabla de amortizacion")
    public void validaResultados() {

        OnStage.theActorInTheSpotlight()
                .attemptsTo(
                        ValidateAmortization.forSimulation()
                );
    }
}