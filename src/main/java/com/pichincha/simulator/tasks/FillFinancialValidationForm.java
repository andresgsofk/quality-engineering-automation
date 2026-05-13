package com.pichincha.simulator.tasks;

import com.pichincha.core.interactions.actions.ShadowActions;
import com.pichincha.core.interactions.synchronization.SmartWait;
import com.pichincha.simulator.models.FinancialValidationData;
import com.pichincha.simulator.ui.FinancialForm;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class FillFinancialValidationForm implements Task {

    private final FinancialValidationData validationData;

    public FillFinancialValidationForm(
            FinancialValidationData validationData) {

        this.validationData = validationData;
    }

    public static FillFinancialValidationForm withData(
            FinancialValidationData validationData) {

        return Tasks.instrumented(
                FillFinancialValidationForm.class,
                validationData
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        System.out.println(
                "\n=== VALIDACION FORMULARIO ==="
        );

        // =========================================
        // MONTO PRESTAMO
        // =========================================

        if (validationData.getMontoPrestamo() != null) {

            ShadowActions.type(
                    FinancialForm.INPUT_AMOUNT_HOST,
                    FinancialForm.INPUT_AMOUNT,
                    validationData.getMontoPrestamo()
            );
        }

        // =========================================
        // MONTO VIVIENDA
        // =========================================

        if (validationData.getMontoVivienda() != null) {

            ShadowActions.type(
                    FinancialForm.INPUT_SECOND_AMOUNT_HOST,
                    FinancialForm.INPUT_SECOND_AMOUNT,
                    validationData.getMontoVivienda()
            );
        }

        // =========================================
        // CLICK SIMULAR
        // =========================================

        ShadowActions.click(
                FinancialForm.SIMULATE_BUTTON_HOST,
                FinancialForm.PRIMARY_BUTTON
        );


    }
}