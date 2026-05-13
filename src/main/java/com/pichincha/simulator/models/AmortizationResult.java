package com.pichincha.simulator.models;

import java.math.BigDecimal;
import java.util.List;

public class AmortizationResult {

    private final BigDecimal monthlyFee;
    private final BigDecimal annualRate;
    private final BigDecimal capital;
    private final BigDecimal totalInterest;
    private final BigDecimal totalInsurance;
    private final List<AmortizationRow> rows;

    public AmortizationResult(BigDecimal monthlyFee,
                              BigDecimal annualRate,
                              BigDecimal capital,
                              BigDecimal totalInterest,
                              BigDecimal totalInsurance,
                              List<AmortizationRow> rows) {
        this.monthlyFee = monthlyFee;
        this.annualRate = annualRate;
        this.capital = capital;
        this.totalInterest = totalInterest;
        this.totalInsurance = totalInsurance;
        this.rows = rows;
    }

    public BigDecimal getMonthlyFee() { return monthlyFee; }
    public BigDecimal getAnnualRate() { return annualRate; }
    public BigDecimal getCapital() { return capital; }
    public BigDecimal getTotalInterest() { return totalInterest; }
    public BigDecimal getTotalInsurance() { return totalInsurance; }
    public List<AmortizationRow> getRows() { return rows; }

    // ================================
    // DEBUG HORIZONTAL PROFESIONAL
    // ================================
    public void printHorizontalComparison() {
        System.out.println("\n======================================================================");
        System.out.printf("%-6s | %-10s | %-10s | %-10s | %-12s | %-10s\n",
                "CUOTA", "CAPITAL", "INTERES", "SEGURO", "CUOTA TOTAL", "SALDO");
        System.out.println("======================================================================");

        for (AmortizationRow row : rows) {
            System.out.printf("%-6d | %-10.2f | %-10.2f | %-10.2f | %-12.2f | %-10.2f\n",
                    row.getInstallment(),
                    row.getCapital(),
                    row.getInterest(),
                    row.getInsurance(),
                    row.getFee(),
                    row.getBalance());
        }

        System.out.println("======================================================================");
        System.out.printf("CUOTA MENSUAL (UI)   : %.2f\n", monthlyFee);
        System.out.printf("TASA ANUAL (UI)      : %.10f\n", annualRate);
        System.out.printf("CAPITAL APROBADO (UI): %.2f\n", capital);
        System.out.println("======================================================================\n");
    }
}