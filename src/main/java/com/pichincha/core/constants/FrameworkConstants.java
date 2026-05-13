package com.pichincha.core.constants;

// ==============================================
// FRAMEWORK CONFIGURATION
// ==============================================
// Propósito:
// Centralizar TODA configuración técnica del framework
// (timeouts, retries, polling, JS helpers, flags)
//
// REGLA:
// No duplicar configuraciones en múltiples clases
// ==============================================

public class FrameworkConstants {

    // ==============================================
    // TIMEOUTS
    // ==============================================
    public static final int DEFAULT_TIMEOUT = 10;
    public static final int MEDIUM_TIMEOUT = 20;
    public static final int LONG_TIMEOUT = 40;

    // ==============================================
    // POLLING (FLAKY CONTROL)
    // ==============================================
    public static final long DEFAULT_POLLING_MILLIS = 250;

    // ==============================================
    // RETRY CONFIGURATION
    // ==============================================
    public static final int DEFAULT_RETRY_ATTEMPTS = 3;

    // ==============================================
    // JAVASCRIPT UTILITIES
    // ==============================================
    public static final String SCROLL_CENTER_SCRIPT =
            "arguments[0].scrollIntoView({behavior:'instant', block:'nearest', inline:'nearest'});";

    public static final String CLICK_SCRIPT =
            "arguments[0].click();";

    // ==============================================
    // FRAMEWORK FLAGS
    // ==============================================
    public static final boolean ENABLE_HARD_WAIT_LOGGING = true;
}