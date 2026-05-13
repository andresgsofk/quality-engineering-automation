package com.pichincha.simulator.tasks;

import com.pichincha.core.interactions.actions.FrameActions;
import com.pichincha.core.interactions.actions.ShadowActions;
import com.pichincha.core.interactions.synchronization.SmartWait;

import com.pichincha.simulator.models.CreditSimulationData;
import com.pichincha.simulator.ui.FinancialForm;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class EnterFinancialInfo implements Task {

    private final CreditSimulationData creditData;

    // =========================================
    // NUEVO → CONTROL DE FLUJO
    // =========================================
    private final boolean executeSimulation;

    // =========================================
    // CONSTRUCTOR ACTUAL
    // NO TOCAR → HAPPY PATH FUNCIONAL
    // =========================================
    public EnterFinancialInfo(
            CreditSimulationData creditData) {

        this.creditData = creditData;
        this.executeSimulation = true;
    }

    // =========================================
    // NUEVO CONSTRUCTOR
    // =========================================
    public EnterFinancialInfo(
            CreditSimulationData creditData,
            boolean executeSimulation) {

        this.creditData = creditData;
        this.executeSimulation = executeSimulation;
    }

    // =========================================
    // FACTORY ORIGINAL
    // NO TOCAR
    // =========================================
    public static EnterFinancialInfo from(
            CreditSimulationData creditData) {

        return Tasks.instrumented(
                EnterFinancialInfo.class,
                creditData
        );
    }

    // =========================================
    // NUEVO FACTORY PARA VALIDACIONES
    // =========================================
    public static EnterFinancialInfo validation(
            CreditSimulationData creditData) {

        return Tasks.instrumented(
                EnterFinancialInfo.class,
                creditData,
                false
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        System.out.println(
                "=== DEBUG: Ingresando valores financieros ==="
        );

        // =========================================
        // MONTO PRESTAMO
        // =========================================
        if (creditData.getMontoPrestamo() != null) {

            System.out.println(
                    "Ingresando monto PRESTAMO: "
                            + creditData.getMontoPrestamo()
            );

            ShadowActions.type(
                    FinancialForm.INPUT_AMOUNT_HOST,
                    FinancialForm.INPUT_AMOUNT,
                    creditData.getMontoPrestamo()
            );
        }

        // =========================================
        // MONTO VIVIENDA
        // =========================================
        if (creditData.getMontoVivienda() != null) {

            System.out.println(
                    "Ingresando monto VIVIENDA: "
                            + creditData.getMontoVivienda()
            );

            ShadowActions.type(
                    FinancialForm.INPUT_SECOND_AMOUNT_HOST,
                    FinancialForm.INPUT_SECOND_AMOUNT,
                    creditData.getMontoVivienda()
            );
        }

        // =========================================
        // PLAZO
        // =========================================
        ShadowActions.click(
                FinancialForm.TERM_DROPDOWN_HOST,
                FinancialForm.TERM_DROPDOWN
        );

        ShadowActions.select(
                FinancialForm.TERM_DROPDOWN_HOST,
                creditData.getTerm()
        );

        // =========================================
        // MÉTODO FRANCÉS
        // =========================================
        FrameActions.clickFrenchMethod();

        // =========================================
        // HAPPY PATH
        // =========================================
        if (executeSimulation) {

            // BOTÓN SIMULAR
            ShadowActions.click(
                    FinancialForm.SIMULATE_BUTTON_HOST,
                    FinancialForm.PRIMARY_BUTTON
            );

            SmartWait.untilTextPresent(
                    "Tabla de amortización"
            );

            // ABRIR TABLA
            ShadowActions.click(
                    FinancialForm.AMORTIZATION_BUTTON_HOST,
                    FinancialForm.AMORTIZATION_BUTTON
            );

            SmartWait.untilVisible(
                    FinancialForm.MODAL_TITLE
            );

            ShadowActions.scrollTo(
                    FinancialForm.MODAL_TITLE
            );
        }
    }
}