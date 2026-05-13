package com.pichincha.core.reporting;

import net.serenitybdd.core.Serenity;

public class SerenityLogger {

    private SerenityLogger() {}

    public static void info(String message) {

        Serenity.recordReportData()
                .withTitle("INFO")
                .andContents(message);
    }

    public static void warn(String message) {

        Serenity.recordReportData()
                .withTitle("WARNING")
                .andContents(message);
    }

    public static void error(String message) {

        Serenity.recordReportData()
                .withTitle("ERROR")
                .andContents(message);
    }
}