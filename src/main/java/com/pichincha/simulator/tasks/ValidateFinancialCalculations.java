package com.pichincha.simulator.tasks;

import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.questions.AmortizationQuestion;
import com.pichincha.simulator.validations.FinancialCalculationValidator;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ValidateFinancialCalculations implements Task {

    private final String scenarioId;

    public ValidateFinancialCalculations(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public static ValidateFinancialCalculations forScenario(String scenarioId) {
        return Tasks.instrumented(ValidateFinancialCalculations.class, scenarioId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        AmortizationResult result = actor.asksFor(AmortizationQuestion.fromUI());
        FinancialCalculationValidator.validateScenario(scenarioId, result);
    }
}
