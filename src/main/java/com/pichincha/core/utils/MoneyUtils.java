package com.pichincha.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {

    private MoneyUtils() {}

    public static BigDecimal parseMoney(String value) {

        if (value == null || value.isBlank()) {
            return BigDecimal.ZERO;
        }

        String normalized = value
                .replace("$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();

        return new BigDecimal(normalized)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal parsePercentage(String value) {

        if (value == null || value.isBlank()) {
            return BigDecimal.ZERO;
        }

        String normalized = value
                .replace("%", "")
                .replace(",", ".")
                .trim();

        return new BigDecimal(normalized)
                .divide(BigDecimal.valueOf(100),
                        10,
                        RoundingMode.HALF_UP);
    }

    public static BigDecimal round(BigDecimal value) {

        return value.setScale(2, RoundingMode.HALF_UP);
    }
}