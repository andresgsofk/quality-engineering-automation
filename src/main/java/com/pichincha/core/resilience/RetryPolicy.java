package com.pichincha.core.resilience;

// ==============================================
// RETRY POLICY
// ==============================================
// Propósito:
// Centralizar configuración de reintentos
// ==============================================

public class RetryPolicy {

    public static final int DEFAULT_MAX_ATTEMPTS = 3;

    public static final long DEFAULT_DELAY_MS = 500;

    public static final long BACKOFF_MULTIPLIER = 2;
}