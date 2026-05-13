package com.pichincha.stepdefinitions.web.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHooks {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(TestHooks.class);

    // =========================================
    // SETUP
    // =========================================

    @Before(order = 0)
    public void setTheStage() {

        LOGGER.info(
                "\n======================================\n" +
                        "STARTING SCREENPLAY CONFIGURATION\n" +
                        "======================================"
        );

        OnStage.setTheStage(new OnlineCast());

        OnStage.theActorCalled("QeAutomation");

        LOGGER.info("Actor initialized successfully");
    }

    // =========================================
    // TEARDOWN
    // =========================================

    @After(order = 0)
    public void tearDown() {

        LOGGER.info(
                "\n======================================\n" +
                        "FINISHING SCENARIO EXECUTION\n" +
                        "======================================"
        );

        OnStage.drawTheCurtain();

        LOGGER.info("Actors cleaned successfully");
    }
}