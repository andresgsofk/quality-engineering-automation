package com.pichincha.stepdefinitions.web.validation;

import com.pichincha.core.utils.FinancialValidationReader;
import com.pichincha.simulator.models.FinancialValidationData;
import com.pichincha.simulator.questions.FormValidationQuestion;
import com.pichincha.simulator.tasks.FillFinancialValidationForm;
import com.pichincha.simulator.tasks.SelectCreditType;
import com.pichincha.simulator.ui.FinancialForm;
import com.pichincha.simulator.validations.FinancialFormValidator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;

public class FinancialValidationStepDefinitions {

    @When("el usuario selecciona el credito de validacion por ID {string}")
    public void seleccionaCreditoDeValidacionPorId(String idValidacion) {
        FinancialValidationData validationData = FinancialValidationReader.getById(idValidacion);

        OnStage.theActorInTheSpotlight().attemptsTo(
                SelectCreditType.from(validationData)
        );
    }

    @And("ingresa datos invalidos del simulador {string}")
    public void ingresaDatosInvalidosDelSimulador(String idValidacion) {
        FinancialValidationData validationData = FinancialValidationReader.getById(idValidacion);

        OnStage.theActorInTheSpotlight().attemptsTo(
                FillFinancialValidationForm.withData(validationData)
        );
    }

    @Then("el sistema muestra el mensaje de validacion esperado para {string}")
    public void elSistemaMuestraElMensajeDeValidacionEsperado(String idValidacion) {
        FinancialValidationData validationData = FinancialValidationReader.getById(idValidacion);
        String fieldHost = resolveValidationFieldHost(validationData);

        String actualMessage = OnStage.theActorInTheSpotlight().asksFor(
                FormValidationQuestion.fromField(fieldHost)
        );

        FinancialFormValidator.validate(
                actualMessage,
                validationData.getMensajeEsperado()
        );
    }

    private String resolveValidationFieldHost(FinancialValidationData validationData) {
        if (validationData.getCampoValidacion() == null || validationData.getCampoValidacion().isBlank()) {
            return FinancialForm.INPUT_AMOUNT_HOST;
        }

        return switch (validationData.getCampoValidacion().trim().toLowerCase()) {
            case "monto_vivienda", "vivienda", "valor_vivienda" -> FinancialForm.INPUT_SECOND_AMOUNT_HOST;
            case "monto_prestamo", "prestamo", "credito", "monto" -> FinancialForm.INPUT_AMOUNT_HOST;
            default -> throw new IllegalArgumentException(
                    "Campo de validación no soportado: " + validationData.getCampoValidacion()
            );
        };
    }
}
