package com.pichincha.simulator.validations;

import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.models.AmortizationRow;

import java.math.BigDecimal;
import java.util.List;

public class FinancialValidator {

    // tolerancia más realista para UI (redondeos + backend)
    private static final BigDecimal TOLERANCE =
            new BigDecimal("1.50");

    private FinancialValidator() {}

    public static void compare(AmortizationResult actual,
                               AmortizationResult expected) {

        validateMonthlyFee(actual, expected);
        validateRows(actual.getRows(), expected.getRows());
    }

    private static void validateMonthlyFee(AmortizationResult actual,
                                           AmortizationResult expected) {

        BigDecimal diff =
                actual.getMonthlyFee()
                        .subtract(expected.getMonthlyFee())
                        .abs();

        System.out.println("\n=== MONTHLY FEE DEBUG ===");
        System.out.println("UI      : " + actual.getMonthlyFee());
        System.out.println("EXPECTED: " + expected.getMonthlyFee());
        System.out.println("DIFF    : " + diff);

        if (diff.compareTo(TOLERANCE) > 0) {
            throw new AssertionError(
                    "\n❌ Monthly fee mismatch" +
                            "\nExpected: " + expected.getMonthlyFee() +
                            "\nActual: " + actual.getMonthlyFee() +
                            "\nDiff: " + diff
            );
        }
    }

    private static void validateRows(List<AmortizationRow> actualRows,
                                     List<AmortizationRow> expectedRows) {

        if (actualRows.size() != expectedRows.size()) {
            throw new AssertionError("\n❌ Row count mismatch");
        }

        for (int i = 0; i < actualRows.size(); i++) {

            AmortizationRow a = actualRows.get(i);
            AmortizationRow e = expectedRows.get(i);

            System.out.println("\n==============================");
            System.out.println("ROW " + a.getInstallment());
            System.out.println("==============================");

            compareField("CAPITAL", a.getCapital(), e.getCapital());
            compareField("INTEREST", a.getInterest(), e.getInterest());
            compareField("INSURANCE", a.getInsurance(), e.getInsurance());
            compareField("FEE", a.getFee(), e.getFee());
            compareField("BALANCE", a.getBalance(), e.getBalance());
        }
    }

    private static void compareField(String label,
                                     BigDecimal actual,
                                     BigDecimal expected) {

        BigDecimal diff = actual.subtract(expected).abs();

        System.out.println(label);
        System.out.println("UI      : " + actual);
        System.out.println("EXPECTED: " + expected);
        System.out.println("DIFF    : " + diff);

        if (diff.compareTo(TOLERANCE) > 0) {
            throw new AssertionError(
                    "\n❌ Validation error: " + label +
                            "\nExpected: " + expected +
                            "\nActual: " + actual +
                            "\nDiff: " + diff
            );
        }
    }
}