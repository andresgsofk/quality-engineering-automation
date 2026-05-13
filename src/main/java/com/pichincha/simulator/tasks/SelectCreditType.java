package com.pichincha.simulator.tasks;

import com.pichincha.core.interactions.actions.ShadowActions;
import com.pichincha.core.interactions.synchronization.SmartWait;
import com.pichincha.simulator.models.CreditSimulationData;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import org.openqa.selenium.By;

public class SelectCreditType implements Task {

    private final String tipoCredito;

    private static final String DROPDOWN_HOST =
            "pichincha-dropdown";

    private static final String DROPDOWN_CONTAINER =
            ".bp-select-multiple__input-child";

    // =========================
    // CONSTRUCTOR BASE (String)
    // =========================
    public SelectCreditType(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    // =========================
    // FACTORY METHOD (String)
    // =========================
    public static SelectCreditType from(String tipoCredito) {
        return Tasks.instrumented(
                SelectCreditType.class,
                tipoCredito
        );
    }

    // =========================
    // FACTORY METHOD (BACKWARD COMPATIBLE)
    // =========================
    public static SelectCreditType from(CreditSimulationData creditData) {
        return Tasks.instrumented(
                SelectCreditType.class,
                creditData.getTipoCredito()
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        SmartWait.untilVisible(
                By.cssSelector(DROPDOWN_HOST),
                15
        );

        // abrir dropdown
        ShadowActions.click(
                DROPDOWN_HOST,
                DROPDOWN_CONTAINER
        );

        // seleccionar tipo de crédito
        ShadowActions.select(
                DROPDOWN_HOST,
                tipoCredito
        );
    }
}