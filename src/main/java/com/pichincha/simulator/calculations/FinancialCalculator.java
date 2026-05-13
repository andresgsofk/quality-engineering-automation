package com.pichincha.simulator.calculations;

import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.models.AmortizationRow;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class FinancialCalculator {

    private static final MathContext MC =
            new MathContext(12, RoundingMode.HALF_UP);

    // =========================================
    // INSURANCE RATE
    // =========================================

    private static final BigDecimal INSURANCE_RATE =
            new BigDecimal("0.001415");

    private FinancialCalculator() {
    }

    // =========================================
    // FRENCH AMORTIZATION
    // =========================================

    public static AmortizationResult calculateFrenchAmortization(
            BigDecimal capital,
            int months,
            BigDecimal annualRate
    ) {

        // =====================================
        // MONTHLY RATE
        // =====================================

        BigDecimal monthlyRate =
                annualRate.divide(
                        BigDecimal.valueOf(12),
                        12,
                        RoundingMode.HALF_UP
                );

        // =====================================
        // BASE MONTHLY FEE
        // =====================================

        BigDecimal onePlusRate =
                BigDecimal.ONE.add(monthlyRate);

        BigDecimal power =
                onePlusRate.pow(months, MC);

        BigDecimal numerator =
                capital.multiply(monthlyRate)
                        .multiply(power, MC);

        BigDecimal denominator =
                power.subtract(BigDecimal.ONE, MC);

        BigDecimal baseFee =
                numerator.divide(
                        denominator,
                        2,
                        RoundingMode.HALF_UP
                );

        // =====================================
        // TABLE
        // =====================================

        List<AmortizationRow> rows =
                new ArrayList<>();

        BigDecimal balance = capital;

        BigDecimal totalInterest =
                BigDecimal.ZERO;

        BigDecimal totalInsurance =
                BigDecimal.ZERO;

        BigDecimal finalFee =
                BigDecimal.ZERO;

        for (int installment = 1;
             installment <= months;
             installment++) {

            // ==============================
            // INTEREST
            // ==============================

            BigDecimal interest =
                    balance.multiply(monthlyRate)
                            .setScale(2, RoundingMode.HALF_UP);

            // ==============================
            // INSURANCE
            // ==============================

            BigDecimal insurance =
                    balance.multiply(INSURANCE_RATE)
                            .setScale(2, RoundingMode.HALF_UP);

            // ==============================
            // CAPITAL
            // ==============================

            BigDecimal capitalPayment =
                    baseFee.subtract(interest)
                            .setScale(2, RoundingMode.HALF_UP);

            // ==============================
            // MONTHLY FEE
            // ==============================

            BigDecimal fee =
                    baseFee.add(insurance)
                            .setScale(2, RoundingMode.HALF_UP);

            // ==============================
            // NEW BALANCE
            // ==============================

            balance =
                    balance.subtract(capitalPayment)
                            .setScale(2, RoundingMode.HALF_UP);

            if (installment == months) {
                balance = BigDecimal.ZERO;
            }

            totalInterest =
                    totalInterest.add(interest);

            totalInsurance =
                    totalInsurance.add(insurance);

            finalFee = fee;

            rows.add(
                    new AmortizationRow(
                            installment,
                            capitalPayment,
                            interest,
                            insurance,
                            fee,
                            balance
                    )
            );
        }

        return new AmortizationResult(
                finalFee,
                annualRate,
                capital,
                totalInterest,
                totalInsurance,
                rows
        );
    }
}