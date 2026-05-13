package com.pichincha.simulator.calculations;

import com.pichincha.core.utils.MoneyUtils;
import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.models.AmortizationRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AmortizationCalculator {

    private static final BigDecimal INSURANCE_RATE =
            new BigDecimal("0.001415");

    public static AmortizationResult calculateFrenchAmortization(
            BigDecimal capital,
            int months,
            BigDecimal annualRate
    ) {

        BigDecimal monthlyRate =
                annualRate.divide(BigDecimal.valueOf(12),
                        12,
                        RoundingMode.HALF_UP);

        List<AmortizationRow> rows = new ArrayList<>();

        BigDecimal balance = capital;

        BigDecimal totalInterest = BigDecimal.ZERO;
        BigDecimal totalInsurance = BigDecimal.ZERO;

        for (int i = 1; i <= months; i++) {

            BigDecimal interest =
                    balance.multiply(monthlyRate);

            BigDecimal insurance =
                    balance.multiply(INSURANCE_RATE);

            // amortización real del sistema
            BigDecimal capitalPayment =
                    balance.divide(BigDecimal.valueOf(months - i + 1),
                            12,
                            RoundingMode.HALF_UP);

            BigDecimal fee =
                    capitalPayment.add(interest).add(insurance);

            balance = balance.subtract(capitalPayment);

            if (i == months) {
                balance = BigDecimal.ZERO;
            }

            totalInterest = totalInterest.add(interest);
            totalInsurance = totalInsurance.add(insurance);

            rows.add(new AmortizationRow(
                    i,
                    MoneyUtils.round(capitalPayment),
                    MoneyUtils.round(interest),
                    MoneyUtils.round(insurance),
                    MoneyUtils.round(fee),
                    MoneyUtils.round(balance.max(BigDecimal.ZERO))
            ));
        }

        return new AmortizationResult(
                MoneyUtils.round(rows.get(rows.size() - 1).getFee()),
                annualRate,
                MoneyUtils.round(capital),
                MoneyUtils.round(totalInterest),
                MoneyUtils.round(totalInsurance),
                rows
        );
    }
}