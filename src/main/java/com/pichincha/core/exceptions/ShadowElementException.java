package com.pichincha.core.exceptions;

// ==============================================
// SHADOW ELEMENT EXCEPTION
// ==============================================
// Propósito:
// Manejar errores relacionados con Shadow DOM:
//
// - Elementos no encontrados en shadowRoot
// - Interacciones fallidas dentro de shadow DOM
// - Selectores inválidos en componentes web encapsulados
// ==============================================

public class ShadowElementException extends FrameworkException {

    public ShadowElementException(String message) {
        super(message);
    }

    public ShadowElementException(String message, Throwable cause) {
        super(message, cause);
    }
}