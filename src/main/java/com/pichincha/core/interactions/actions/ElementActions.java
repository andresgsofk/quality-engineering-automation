package com.pichincha.core.interactions.actions;

import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;

// ==============================================
// ELEMENT ACTIONS
// ==============================================
// Propósito:
// Wrappers simples de Screenplay para DOM normal
// ==============================================

public class ElementActions {

    public static void click(Target element) {
        Click.on(element);
    }

    public static void type(Target element, String value) {
        Enter.theValue(value).into(element);
    }
}
