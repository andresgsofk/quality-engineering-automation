package com.pichincha.core.exceptions;

// ==============================================
// WAIT ELEMENT EXCEPTION
// ==============================================
// Propósito:
// Manejar errores de sincronización:
//
// - Elementos que no aparecen en el tiempo esperado
// - Timeouts en SmartWait
// - Condiciones de espera fallidas
// ==============================================

public class WaitElementException extends FrameworkException {

    public WaitElementException(String message) {
        super(message);
    }

    public WaitElementException(String message, Throwable cause) {
        super(message, cause);
    }
}