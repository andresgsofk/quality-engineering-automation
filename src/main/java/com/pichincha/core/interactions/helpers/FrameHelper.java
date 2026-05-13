package com.pichincha.core.interactions.helpers;

import net.serenitybdd.core.Serenity;
import org.openqa.selenium.*;

public class FrameHelper {

    public static void switchToSimulatorFrame() {

        WebDriver driver = Serenity.getDriver();

        driver.switchTo().defaultContent();

        WebElement frame = driver.findElement(
                By.cssSelector("iframe.microsite-iframe")
        );

        driver.switchTo().frame(frame);
    }

    public static void switchToDefault() {

        Serenity.getDriver().switchTo().defaultContent();
    }
}