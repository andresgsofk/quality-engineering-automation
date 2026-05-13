package com.pichincha.core.utils;

// ==============================================
// ENVIRONMENT READER
// ==============================================
// Propósito:
// Centralizar lectura de variables de entorno
// URLs, flags, ambientes (qa/dev/staging/prod)
// ==============================================

public class EnvironmentReader {

    public static String getEnv(String key) {
        return System.getProperty(key, System.getenv(key));
    }

    public static String getBaseUrl() {
        return getEnv("webdriver.base.url");
    }

    public static String getEnvironment() {
        return getEnv("environment");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getEnv("headless"));
    }
}