package com.pichincha.core.interactions.actions;

import com.pichincha.core.interactions.helpers.ShadowDomHelper;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShadowActions {

    private ShadowActions() {}

    public static void click(String hostCss, String elementCss) {
        ShadowDomHelper.click(hostCss, elementCss);
    }

    public static void type(String hostCss, String inputCss, String value) {
        ShadowDomHelper.type(hostCss, inputCss, value);
    }

    public static void select(String hostCss, String optionText) {
        ShadowDomHelper.selectByText(hostCss, optionText);
    }

    public static String getText(String hostCss, String elementCss) {
        return ShadowDomHelper.getText(hostCss, elementCss);
    }

    public static void scrollTo(String cssSelector) {
        ShadowDomHelper.scrollTo(cssSelector);
    }

    public static List<WebElement> findElements(String hostCss, String elementCss) {
        return ShadowDomHelper.findElements(hostCss, elementCss);
    }
}