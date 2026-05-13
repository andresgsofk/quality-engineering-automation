package com.pichincha.core.interactions.synchronization;

import net.serenitybdd.core.Serenity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SmartWait {

    private static final int DEFAULT_TIMEOUT = 15;

    private SmartWait() {}

    private static WebDriver driver() {
        return Serenity.getDriver();
    }

    // =========================================
    // CSS SELECTOR
    // =========================================

    public static void untilVisible(String cssSelector) {

        new WebDriverWait(driver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(driver -> {

                    try {

                        return driver.findElement(
                                By.cssSelector(cssSelector)
                        ).isDisplayed();

                    } catch (Exception e) {

                        return false;
                    }
                });
    }

    // =========================================
    // BACKWARD COMPATIBILITY
    // =========================================

    public static WebElement untilVisible(By locator,
                                          int timeoutSeconds) {

        return new WebDriverWait(
                driver(),
                Duration.ofSeconds(timeoutSeconds)
        ).until(driver -> {

            try {

                WebElement element =
                        driver.findElement(locator);

                return element.isDisplayed()
                        ? element
                        : null;

            } catch (Exception e) {

                return null;
            }
        });
    }

    // =========================================
    // TEXT PRESENT
    // =========================================

    public static void untilTextPresent(String text) {

        new WebDriverWait(driver(), Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(driver ->
                        driver.getPageSource()
                                .contains(text)
                );
    }
}