package com.pichincha.simulator.tasks;

import com.pichincha.simulator.constants.Environment;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.Tasks;

// ==============================================
// OPEN SIMULATOR
// ==============================================
// Propósito:
// Abrir el simulador de crédito
// ==============================================

public class OpenSimulator implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Open.url(Environment.BASE_URL)
        );
    }

    public static OpenSimulator openWeb() {
        return Tasks.instrumented(OpenSimulator.class);
    }
}