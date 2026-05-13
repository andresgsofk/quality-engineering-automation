package com.pichincha.core.interactions.helpers;

import com.pichincha.core.exceptions.ShadowElementException;
import net.serenitybdd.core.Serenity;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShadowDomHelper {

    private static final int TIMEOUT = 15;

    private ShadowDomHelper() {}

    private static WebDriver driver() {
        return Serenity.getDriver();
    }

    private static SearchContext shadowRoot(String hostCss) {

        try {

            WebElement host =
                    new WebDriverWait(driver(), Duration.ofSeconds(TIMEOUT))
                            .until(d -> d.findElement(By.cssSelector(hostCss)));

            return host.getShadowRoot();

        } catch (Exception e) {

            throw new ShadowElementException(
                    "Error obteniendo shadow root de: " + hostCss, e);
        }
    }

    // ==========================================
    // CLICK
    // ==========================================
    public static void click(String hostCss, String elementCss) {

        try {

            SearchContext root = shadowRoot(hostCss);

            WebElement element =
                    root.findElement(By.cssSelector(elementCss));

            scrollIntoView(element);

            Thread.sleep(400);

            jsClick(element);

        } catch (Exception e) {

            throw new ShadowElementException(
                    "Error haciendo click en shadow element: " + elementCss, e);
        }
    }

    // ==========================================
    // TYPE
    // ==========================================
    public static void type(String hostCss,
                            String inputCss,
                            String value) {

        try {

            SearchContext root = shadowRoot(hostCss);

            WebElement input =
                    root.findElement(By.cssSelector(inputCss));

            scrollIntoView(input);

            Thread.sleep(300);

            jsClick(input);

            Thread.sleep(300);

            // SOLO para estabilizar segundo input
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.DELETE);

            input.sendKeys(value);

            dispatchInputEvents(input);

        } catch (Exception e) {

            throw new ShadowElementException(
                    "Error escribiendo en shadow input: " + inputCss, e);
        }
    }

    // ==========================================
    // SELECT BY TEXT
    // ==========================================
    public static void selectByText(String hostCss,
                                    String optionText) {

        try {

            List<WebElement> options =
                    shadowRoot(hostCss)
                            .findElements(By.cssSelector("pichincha-typography"));

            for (WebElement option : options) {

                if (option.getText()
                        .trim()
                        .equalsIgnoreCase(optionText)) {

                    WebElement label =
                            option.findElement(By.xpath("./ancestor::label"));

                    scrollIntoView(label);

                    jsClick(label);

                    return;
                }
            }

            throw new ShadowElementException(
                    "Opción no encontrada: " + optionText);

        } catch (Exception e) {

            throw new ShadowElementException(
                    "Error seleccionando opción: " + optionText, e);
        }
    }

    // ==========================================
    // GET TEXT
    // ==========================================
    public static String getText(String hostCss,
                                 String elementCss) {

        try {

            WebElement el =
                    shadowRoot(hostCss)
                            .findElement(By.cssSelector(elementCss));

            return el.getText().trim();

        } catch (Exception e) {

            throw new ShadowElementException(
                    "Error obteniendo texto de: " + elementCss, e);
        }
    }

    // ==========================================
    // FIND ELEMENTS
    // ==========================================
    public static List<WebElement> findElements(String hostCss,
                                                String elementCss) {

        try {

            return shadowRoot(hostCss)
                    .findElements(By.cssSelector(elementCss));

        } catch (Exception e) {

            throw new ShadowElementException(
                    "Error obteniendo elementos shadow: " + elementCss, e);
        }
    }

    // ==========================================
    // SCROLL PÚBLICO
    // ==========================================
    public static void scrollTo(String cssSelector) {

        try {

            WebElement element =
                    driver().findElement(By.cssSelector(cssSelector));

            scrollIntoView(element);

        } catch (Exception e) {

            throw new ShadowElementException(
                    "Error haciendo scroll hasta: " + cssSelector, e);
        }
    }

    // ==========================================
    // PRIVATE HELPERS
    // ==========================================
    private static void scrollIntoView(WebElement element) {

        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].scrollIntoView({behavior:'instant', block:'nearest', inline:'nearest'});",
                element
        );
    }

    private static void jsClick(WebElement element) {

        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].click();",
                element
        );
    }

    private static void dispatchInputEvents(WebElement input) {

        ((JavascriptExecutor) driver()).executeScript("""
            arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
            arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
            arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));
        """, input);
    }
}