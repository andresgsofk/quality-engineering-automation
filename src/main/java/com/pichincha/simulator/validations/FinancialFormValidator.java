package com.pichincha.simulator.validations;

public class FinancialFormValidator {

    private FinancialFormValidator() {}

    public static void validate(
            String actualMessage,
            String expectedMessage) {

        System.out.println(
                "\n========== VALIDATION DEBUG =========="
        );

        System.out.println(
                "EXPECTED: " + expectedMessage
        );

        System.out.println(
                "ACTUAL  : " + actualMessage
        );

        System.out.println(
                "======================================\n"
        );

        if (!actualMessage.contains(expectedMessage)) {

            throw new AssertionError(
                    "\n❌ Validation message mismatch" +
                            "\nExpected: " + expectedMessage +
                            "\nActual: " + actualMessage
            );
        }
    }
}