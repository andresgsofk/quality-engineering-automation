package com.pichincha.simulator.validations.comparison;

import com.pichincha.simulator.models.comparison.ProductComparisonResult;
import com.pichincha.simulator.models.comparison.ProductSimulationSnapshot;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductComparisonValidator {

    private ProductComparisonValidator() {
    }

    public static void validate(ProductComparisonResult comparisonResult) {

        if (comparisonResult == null) {
            throw new AssertionError("No existe resultado de comparación de productos en memoria del actor.");
        }

        ProductSimulationSnapshot first = comparisonResult.getFirstProduct();
        ProductSimulationSnapshot second = comparisonResult.getSecondProduct();

        System.out.println("\n================================================================================");
        System.out.println("FLUJO 5 - VALIDACIÓN COMPARATIVA ENTRE PRODUCTOS");
        System.out.println("================================================================================");
        printComparisonTable(first, second);

        validateDifferentProducts(first, second);
        validateRatesAreDifferent(first, second);
        validateInstallmentsAreDifferent(first, second);
        validateMonthlyPaymentsAreDifferent(first, second);
        validateAmortizationBasesAreAvailable(first, second);

        System.out.println("\n✅ FLUJO 5 APROBADO - Comparación de productos validada correctamente");
        System.out.println("================================================================================\n");
    }

    private static void printComparisonTable(ProductSimulationSnapshot first,
                                             ProductSimulationSnapshot second) {

        BigDecimal monthlyFeeDifference = first.getMonthlyFee()
                .subtract(second.getMonthlyFee())
                .abs()
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal rateDifference = first.getAnnualRate()
                .subtract(second.getAnnualRate())
                .abs();

        int installmentsDifference = Math.abs(first.getInstallments() - second.getInstallments());

        System.out.printf("%-32s | %-22s | %-22s%n", "Campo", first.getScenarioId(), second.getScenarioId());
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-32s | %-22s | %-22s%n", "Producto", first.getProductType(), second.getProductType());
        System.out.printf("%-32s | %-22s | %-22s%n", "Plazo", first.getTerm(), second.getTerm());
        System.out.printf("%-32s | %-22s | %-22s%n", "Cuotas tabla", first.getInstallments(), second.getInstallments());
        System.out.printf("%-32s | %-22s | %-22s%n", "Base amortización", first.getAmortizationBase(), second.getAmortizationBase());
        System.out.printf("%-32s | %-22s | %-22s%n", "Capital mostrado UI", first.getCapitalShownByUi(), second.getCapitalShownByUi());
        System.out.printf("%-32s | %-22s | %-22s%n", "Cuota mensual UI", first.getMonthlyFee(), second.getMonthlyFee());
        System.out.printf("%-32s | %-22s | %-22s%n", "Tasa anual UI", first.getAnnualRate(), second.getAnnualRate());
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Diferencia cuota mensual : " + monthlyFeeDifference);
        System.out.println("Diferencia tasa anual    : " + rateDifference);
        System.out.println("Diferencia número cuotas : " + installmentsDifference);
    }

    private static void validateDifferentProducts(ProductSimulationSnapshot first,
                                                  ProductSimulationSnapshot second) {

        if (normalize(first.getProductType()).equals(normalize(second.getProductType()))) {
            throw new AssertionError(
                    "\n❌ Los productos comparados no son diferentes." +
                            "\nProducto 1: " + first.getProductType() +
                            "\nProducto 2: " + second.getProductType()
            );
        }

        System.out.println("✔ Productos diferentes: " + first.getProductType() + " vs " + second.getProductType());
    }

    private static void validateRatesAreDifferent(ProductSimulationSnapshot first,
                                                  ProductSimulationSnapshot second) {

        if (first.getAnnualRate().compareTo(second.getAnnualRate()) == 0) {
            throw new AssertionError(
                    "\n❌ Las tasas de interés son iguales y se esperaba diferencia entre productos." +
                            "\nTasa producto 1: " + first.getAnnualRate() +
                            "\nTasa producto 2: " + second.getAnnualRate()
            );
        }

        System.out.println("✔ Tasas diferentes: " + first.getAnnualRate() + " vs " + second.getAnnualRate());
    }

    private static void validateInstallmentsAreDifferent(ProductSimulationSnapshot first,
                                                         ProductSimulationSnapshot second) {

        if (first.getInstallments() == second.getInstallments()) {
            throw new AssertionError(
                    "\n❌ El número de cuotas es igual y se esperaba diferencia por condiciones del producto." +
                            "\nCuotas producto 1: " + first.getInstallments() +
                            "\nCuotas producto 2: " + second.getInstallments()
            );
        }

        System.out.println("✔ Plazos/cuotas diferentes: " + first.getInstallments() + " vs " + second.getInstallments());
    }

    private static void validateMonthlyPaymentsAreDifferent(ProductSimulationSnapshot first,
                                                            ProductSimulationSnapshot second) {

        if (first.getMonthlyFee().compareTo(second.getMonthlyFee()) == 0) {
            throw new AssertionError(
                    "\n❌ Las cuotas mensuales son iguales y se esperaba diferencia entre productos." +
                            "\nCuota producto 1: " + first.getMonthlyFee() +
                            "\nCuota producto 2: " + second.getMonthlyFee()
            );
        }

        System.out.println("✔ Cuotas mensuales diferentes: " + first.getMonthlyFee() + " vs " + second.getMonthlyFee());
    }

    private static void validateAmortizationBasesAreAvailable(ProductSimulationSnapshot first,
                                                              ProductSimulationSnapshot second) {

        if (first.getAmortizationBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AssertionError("\n❌ La base de amortización del primer producto no es válida: " + first.getAmortizationBase());
        }

        if (second.getAmortizationBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AssertionError("\n❌ La base de amortización del segundo producto no es válida: " + second.getAmortizationBase());
        }

        System.out.println("✔ Bases de amortización capturadas correctamente");
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }
}
