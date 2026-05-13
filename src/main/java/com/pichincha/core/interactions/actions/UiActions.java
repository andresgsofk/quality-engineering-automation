package com.pichincha.core.interactions.actions;

import net.serenitybdd.core.Serenity;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UiActions {

    public static void click(By locator) {

        WebDriver driver = Serenity.getDriver();

        WebElement el = driver.findElement(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'nearest', inline:'nearest'});", el);

        el.click();
    }
}
