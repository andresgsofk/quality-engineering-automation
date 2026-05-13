package com.pichincha.core.base;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.waits.WaitUntil.the;

public abstract class BaseQuestion<T> implements Question<T> {

    // ==============================================
    // ELEMENTO UI A CONSULTAR
    // ==============================================
    protected final Target target;

    // ==============================================
    // TIEMPO DE ESPERA EN SEGUNDOS
    // ==============================================
    protected final int timeoutInSeconds;

    // ==============================================
    // CONSTRUCTOR CON TIMEOUT PERSONALIZADO
    // ==============================================
    protected BaseQuestion(Target target, int timeoutInSeconds) {
        this.target = target;
        this.timeoutInSeconds = timeoutInSeconds;
    }

    // ==============================================
    // CONSTRUCTOR CON TIMEOUT POR DEFECTO
    // ==============================================
    protected BaseQuestion(Target target) {
        this(target, 10);
    }

    // ==============================================
    // ESPERA DE VISIBILIDAD DEL ELEMENTO
    // ==============================================
    protected void waitForVisibility(Actor actor) {
        actor.attemptsTo(
                the(target, isVisible())
                        .forNoMoreThan(timeoutInSeconds)
                        .seconds()
        );
    }

    // ==============================================
    // OBTENER TARGET
    // ==============================================
    protected Target getTarget() {
        return target;
    }

    // ==============================================
    // OBTENER TIMEOUT CONFIGURADO
    // ==============================================
    protected int getTimeoutInSeconds() {
        return timeoutInSeconds;
    }
}