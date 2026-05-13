package com.pichincha.core.exceptions;

// ==============================================
// FRAMEWORK EXCEPTION
// ==============================================
// Propósito:
// Excepción base del framework de automatización.
//
// Uso:
// Todas las excepciones personalizadas deben heredar de esta clase
//
// Beneficio:
// Centraliza el control de errores del framework
// ==============================================

public class FrameworkException extends RuntimeException {

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}