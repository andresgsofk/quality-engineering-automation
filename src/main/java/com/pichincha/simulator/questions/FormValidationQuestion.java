package com.pichincha.simulator.questions;

import com.pichincha.simulator.ui.FinancialForm;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import org.openqa.selenium.*;

public class FormValidationQuestion
        implements Question<String> {

    private final String hostSelector;

    public FormValidationQuestion(
            String hostSelector
    ) {
        this.hostSelector = hostSelector;
    }

    @Override
    public String answeredBy(Actor actor) {

        WebDriver driver = Serenity.getDriver();

        // HOST SHADOW
        WebElement host =
                driver.findElement(
                        By.cssSelector(hostSelector)
                );

        // SHADOW ROOT
        SearchContext shadowRoot =
                host.getShadowRoot();

        // VALIDATION MESSAGE
        WebElement validation =
                shadowRoot.findElement(
                        By.cssSelector(
                                FinancialForm.VALIDATION_MESSAGE
                        )
                );

        String validationMessage =
                validation.getText().trim();

        // DEBUG PROFESIONAL
        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "HOST: " + hostSelector
        );

        System.out.println(
                "VALIDATION MESSAGE UI:"
        );

        System.out.println(
                "[" + validationMessage + "]"
        );

        System.out.println(
                "TAG: " + validation.getTagName()
        );

        System.out.println(
                "CLASS: "
                        + validation.getAttribute("class")
        );

        System.out.println(
                "INNER HTML: "
                        + validation.getAttribute("innerHTML")
        );

        System.out.println(
                "======================================\n"
        );

        return validationMessage;
    }

    public static FormValidationQuestion fromField(
            String hostSelector
    ) {
        return new FormValidationQuestion(
                hostSelector
        );
    }
}