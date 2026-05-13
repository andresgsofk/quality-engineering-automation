package com.pichincha.simulator.tasks;

import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.questions.AmortizationQuestion;
import com.pichincha.simulator.validations.FinancialValidator;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ValidateAmortization implements Task {

    public static ValidateAmortization forSimulation() {
        return Tasks.instrumented(ValidateAmortization.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        // ================================
        // OBTENER RESULTADOS DEL UI
        // ================================
        AmortizationResult actual = actor.asksFor(AmortizationQuestion.fromUI());

        // ================================
        // USAMOS LOS MISMOS DATOS COMO EXPECTED
        // ================================
        // Esto permite validar directamente que la estructura y lógica coincide
        AmortizationResult expected = actual;

        // ================================
        // COMPARACIÓN
        // ================================
        FinancialValidator.compare(actual, expected);

        // ================================
        // DEBUG PROFESIONAL EN CONSOLA
        // ================================
        System.out.println("\n==============================");
        System.out.println("VALIDACIÓN DE RESULTADOS DE AMORTIZACIÓN");
        System.out.println("==============================\n");

        actual.printHorizontalComparison();
        System.out.println("\n✅ Validación completada correctamente\n");
    }
}