package com.pichincha.core.base;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public abstract class BasePage extends PageObject {

    private static final int DEFAULT_TIMEOUT = 15;

    // ==========================================
    // WAIT FOR ELEMENT
    // ==========================================

    protected WebElementFacade waitForElement(By locator) {
        return $(locator).waitUntilVisible();
    }

    protected WebElementFacade waitForElement(By locator, int timeoutSeconds) {
        return $(locator).withTimeoutOf(Duration.ofSeconds(timeoutSeconds))
                .waitUntilVisible();
    }

    // ==========================================
    // CLICK ACTIONS
    // ==========================================

    protected void click(By locator) {
        waitForElement(locator).click();
    }

    protected void click(By locator, int timeoutSeconds) {
        waitForElement(locator, timeoutSeconds).click();
    }

    // ==========================================
    // TYPE ACTIONS
    // ==========================================

    protected void type(By locator, String text) {
        WebElementFacade element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    // ==========================================
    // GET TEXT
    // ==========================================

    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }

    // ==========================================
    // VISIBILITY CHECKS
    // ==========================================

    protected boolean isVisible(By locator) {
        try {
            return waitForElement(locator).isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    // ==========================================
    // SAFE WAIT (FALLBACK SIMPLE)
    // ==========================================

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    // ==========================================
    // SCROLL (UTILIDAD SIMPLE)
    // ==========================================

    protected void scrollTo(By locator) {
        evaluateJavascript("arguments[0].scrollIntoView({behavior:'instant', block:'nearest', inline:'nearest'});", $(locator));
    }
}