package com.pichincha.core.resilience;

import com.pichincha.core.reporting.SerenityLogger;

// ==============================================
// RETRY EXECUTOR
// ==============================================
// Propósito:
// Ejecutar acciones con retry controlado
// ==============================================

public class RetryExecutor {

    public static void execute(Runnable action) {
        execute(action, RetryPolicy.DEFAULT_MAX_ATTEMPTS);
    }

    public static void execute(Runnable action, int maxAttempts) {

        int attempt = 0;

        long delay = RetryPolicy.DEFAULT_DELAY_MS;

        while (attempt < maxAttempts) {

            try {

                attempt++;

                action.run();

                return;

            } catch (Exception e) {

                SerenityLogger.warn(
                        "Retry attempt " + attempt + " failed: " + e.getMessage()
                );

                if (attempt >= maxAttempts) {
                    throw e;
                }

                sleep(delay);

                delay *= RetryPolicy.BACKOFF_MULTIPLIER;
            }
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}