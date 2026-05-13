package com.pichincha.simulator.ui;

public class FinancialForm {

    private FinancialForm() {}

    // ================================
    // INPUTS DE MONTO
    // ================================

    public static final String INPUT_AMOUNT_HOST =
            "pichincha-input:nth-of-type(1)";

    public static final String INPUT_AMOUNT =
            "input.input__input-element";

    public static final String INPUT_SECOND_AMOUNT_HOST =
            "pichincha-input:nth-of-type(2)";

    public static final String INPUT_SECOND_AMOUNT =
            "input.input__input-element";

    // ================================
    // TERM DROPDOWN
    // ================================

    public static final String TERM_DROPDOWN_HOST =
            "pichincha-dropdown[placeholder='Selecciona un plazo']";

    public static final String TERM_DROPDOWN =
            ".bp-select-multiple__input-child";

    // ================================
    // MODAL
    // ================================

    public static final String MODAL =
            "#credit-simulator__modal";

    public static final String MODAL_TITLE =
            "#credit-simulator__modal pichincha-typography";

    public static final String MODAL_RESUME =
            "#credit-simulator__modal .modal_header__resume";

    public static final String PRODUCT_NAME =
            "#credit-simulator__modal .product-name";

    // ================================
    // TABLA
    // ================================

    public static final String AMORTIZATION_TABLE =
            "#credit-simulator__modal table";

    public static final String TABLE_ROWS =
            "#credit-simulator__modal tbody tr";

    // ================================
    // BOTONES
    // ================================

    public static final String SIMULATE_BUTTON_HOST =
            "pichincha-button";

    public static final String PRIMARY_BUTTON =
            "button";

    public static final String AMORTIZATION_BUTTON_HOST =
            "pichincha-link-button";

    public static final String AMORTIZATION_BUTTON =
            ".link-button-container";

    public static final String REQUEST_CREDIT_BUTTON_HOST =
            "#credit-simulator__modal pichincha-button[variant='primary']";

    // ================================
    // VALIDATION MESSAGE
    // ================================

    public static final String VALIDATION_MESSAGE =
            ".input__message";
}