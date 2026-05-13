package com.pichincha.simulator.validations;

import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.models.AmortizationRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class FinancialCalculationValidator {

    private static final BigDecimal TOLERANCE = new BigDecimal("1.50");
    private static final BigDecimal MONTHLY_FEE_TOLERANCE = new BigDecimal("5.00");

    private FinancialCalculationValidator() {
    }

    public static void validateScenario(String scenarioId, AmortizationResult result) {

        List<AmortizationRow> rows = result.getRows();

        if (rows == null || rows.isEmpty()) {
            throw new AssertionError("No se encontraron filas en la tabla de amortización para el escenario: " + scenarioId);
        }

        BigDecimal approvedCapital = safe(result.getCapital());
        BigDecimal amortizationBase = calculateAmortizationBase(rows);

        System.out.println("\n================================================================================");
        System.out.println("FLUJO 4 - VALIDACIÓN DE CÁLCULOS FINANCIEROS");
        System.out.println("ESCENARIO: " + scenarioId);
        System.out.println("================================================================================");
        System.out.println("Capital aprobado mostrado UI      : " + money(approvedCapital));
        System.out.println("Base real de amortización tabla   : " + money(amortizationBase));
        System.out.println("Diferencia detectada              : " + money(amortizationBase.subtract(approvedCapital).abs()));
        System.out.println("Tasa anual UI                     : " + result.getAnnualRate());
        System.out.println("Cuota mensual UI                  : " + money(result.getMonthlyFee()));
        System.out.println("Filas evaluadas                   : " + rows.size());
        System.out.println("Tolerancia aplicada               : +/- " + money(TOLERANCE));
        System.out.println("================================================================================");

        validateFeeComposition(rows);
        validateBalanceProgression(rows, amortizationBase);
        validateCapitalTotalAgainstAmortizationBase(rows, amortizationBase);
        validateMonthlyFeeConsistency(result);

        System.out.println("\n✅ FLUJO 4 APROBADO - Cálculos financieros consistentes para: " + scenarioId);
        System.out.println("================================================================================\n");
    }

    private static BigDecimal calculateAmortizationBase(List<AmortizationRow> rows) {
        AmortizationRow firstRow = rows.get(0);

        return firstRow.getCapital()
                .add(firstRow.getBalance())
                .setScale(2, RoundingMode.HALF_UP);
    }

    private static void validateFeeComposition(List<AmortizationRow> rows) {

        System.out.println("\n[1] Validación: CUOTA TOTAL = CAPITAL + INTERÉS + SEGURO");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-12s | %-12s | %-12s | %-14s | %-14s | %-10s%n",
                "Cuota", "Capital", "Interés", "Seguro", "Suma calc.", "Cuota UI", "Dif.");
        System.out.println("--------------------------------------------------------------------------------");

        for (AmortizationRow row : rows) {
            BigDecimal calculatedFee = row.getCapital()
                    .add(row.getInterest())
                    .add(row.getInsurance())
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal diff = calculatedFee.subtract(row.getFee()).abs();

            System.out.printf("%-6d | %-12s | %-12s | %-12s | %-14s | %-14s | %-10s%n",
                    row.getInstallment(),
                    money(row.getCapital()),
                    money(row.getInterest()),
                    money(row.getInsurance()),
                    money(calculatedFee),
                    money(row.getFee()),
                    money(diff));

            assertWithinTolerance(
                    "Composición de cuota " + row.getInstallment(),
                    calculatedFee,
                    row.getFee(),
                    diff
            );
        }
    }

    private static void validateBalanceProgression(List<AmortizationRow> rows,
                                                   BigDecimal amortizationBase) {

        System.out.println("\n[2] Validación: SALDO ACTUAL = SALDO ANTERIOR - CAPITAL ABONADO");
        System.out.println("Nota técnica: la validación usa la base real de amortización derivada de la tabla,");
        System.out.println("no el capital aprobado mostrado en el resumen, porque el simulador maneja ambos valores.");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-16s | %-14s | %-16s | %-14s | %-10s%n",
                "Cuota", "Saldo anterior", "Capital", "Saldo calculado", "Saldo UI", "Dif.");
        System.out.println("--------------------------------------------------------------------------------");

        BigDecimal previousBalance = amortizationBase;

        for (AmortizationRow row : rows) {
            BigDecimal calculatedBalance = previousBalance
                    .subtract(row.getCapital())
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal diff = calculatedBalance.subtract(row.getBalance()).abs();

            System.out.printf("%-6d | %-16s | %-14s | %-16s | %-14s | %-10s%n",
                    row.getInstallment(),
                    money(previousBalance),
                    money(row.getCapital()),
                    money(calculatedBalance),
                    money(row.getBalance()),
                    money(diff));

            assertWithinTolerance(
                    "Progresión de saldo en cuota " + row.getInstallment(),
                    calculatedBalance,
                    row.getBalance(),
                    diff
            );

            previousBalance = row.getBalance();
        }
    }

    private static void validateCapitalTotalAgainstAmortizationBase(List<AmortizationRow> rows,
                                                                    BigDecimal amortizationBase) {

        BigDecimal totalCapitalFromTable = rows.stream()
                .map(AmortizationRow::getCapital)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal diff = totalCapitalFromTable.subtract(amortizationBase).abs();

        System.out.println("\n[3] Validación: SUMA DE CAPITAL ABONADO VS BASE REAL DE AMORTIZACIÓN");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Base real de amortización : " + money(amortizationBase));
        System.out.println("Suma capital tabla        : " + money(totalCapitalFromTable));
        System.out.println("Diferencia                : " + money(diff));

        assertWithinTolerance(
                "Total de capital abonado vs base real de amortización",
                totalCapitalFromTable,
                amortizationBase,
                diff
        );
    }

    private static void validateMonthlyFeeConsistency(AmortizationResult result) {

        BigDecimal monthlyFee = safe(result.getMonthlyFee());
        BigDecimal maxDiff = BigDecimal.ZERO;

        System.out.println("\n[4] Validación: CONSISTENCIA DE CUOTA MENSUAL EN TABLA");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-16s | %-16s | %-10s%n",
                "Cuota", "Cuota mensual", "Cuota fila", "Dif.");
        System.out.println("--------------------------------------------------------------------------------");

        for (AmortizationRow row : result.getRows()) {
            BigDecimal diff = monthlyFee.subtract(row.getFee()).abs();

            if (diff.compareTo(maxDiff) > 0) {
                maxDiff = diff;
            }

            System.out.printf("%-6d | %-16s | %-16s | %-10s%n",
                    row.getInstallment(),
                    money(monthlyFee),
                    money(row.getFee()),
                    money(diff));
        }

        if (maxDiff.compareTo(MONTHLY_FEE_TOLERANCE) > 0) {
            throw new AssertionError(
                    "\n❌ La cuota mensual no es consistente con la tabla." +
                            "\nDiferencia máxima: " + money(maxDiff) +
                            "\nTolerancia       : " + money(MONTHLY_FEE_TOLERANCE)
            );
        }
    }

    private static void assertWithinTolerance(String validationName,
                                              BigDecimal expected,
                                              BigDecimal actual,
                                              BigDecimal diff) {
        if (diff.compareTo(TOLERANCE) > 0) {
            throw new AssertionError(
                    "\n❌ Error en validación financiera: " + validationName +
                            "\nExpected/calculado: " + money(expected) +
                            "\nActual UI         : " + money(actual) +
                            "\nDiferencia        : " + money(diff) +
                            "\nTolerancia        : " + money(TOLERANCE)
            );
        }
    }

    private static BigDecimal safe(BigDecimal value) {
        return value == null
                ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
                : value.setScale(2, RoundingMode.HALF_UP);
    }

    private static String money(BigDecimal value) {
        return safe(value).toPlainString();
    }
}