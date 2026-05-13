package com.pichincha.core.interactions.actions;

import net.serenitybdd.core.Serenity;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FrameActions {

    private FrameActions() {}

    public static void clickFrenchMethod() {

        WebDriver driver = Serenity.getDriver();

        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement label = wait.until(d -> {

                WebElement l = d.findElement(By.cssSelector("label[for='FRANCESA']"));

                return l.isDisplayed() ? l : null;
            });

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({behavior:'instant', block:'nearest', inline:'nearest'});",
                            label
                    );

            Thread.sleep(500);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", label);

        } catch (Exception e) {

            throw new RuntimeException(
                    "❌ Error haciendo click Método Francés",
                    e
            );
        }
    }
}