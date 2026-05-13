package com.pichincha.stepdefinitions.web.commonsteps;

import com.pichincha.simulator.tasks.OpenSimulator;
import io.cucumber.java.en.Given;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.actors.OnStage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenCreditSimulator {

    private static final int TIMEOUT = 30;

    @Given("que el usuario ingresa al simulador de creditos")
    public void queElUsuarioIngresaAlSimuladorDeCreditos() {

        OnStage.theActorInTheSpotlight()
                .attemptsTo(
                        OpenSimulator.openWeb()
                );

        switchToSimulatorIframe();

        scrollToSimulator();
    }

    // =========================================
    // IFRAME
    // =========================================

    private void switchToSimulatorIframe() {

        WebDriver driver = Serenity.getDriver();

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(TIMEOUT)
                );

        WebElement iframe =
                wait.until(
                        ExpectedConditions
                                .presenceOfElementLocated(
                                        By.cssSelector("iframe")
                                )
                );

        driver.switchTo().frame(iframe);
    }

    // =========================================
    // SCROLL
    // =========================================

    private void scrollToSimulator() {

        WebDriver driver = Serenity.getDriver();

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(TIMEOUT)
                );

        WebElement dropdown =
                wait.until(
                        ExpectedConditions
                                .presenceOfElementLocated(
                                        By.cssSelector("pichincha-dropdown")
                                )
                );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({behavior:'instant', block:'nearest', inline:'nearest'});",
                        dropdown
                );
    }
}