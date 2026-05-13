package com.pichincha.simulator.models;

import java.math.BigDecimal;

public class AmortizationRow {

    private final int installment;
    private final BigDecimal capital;
    private final BigDecimal interest;
    private final BigDecimal insurance;
    private final BigDecimal fee;
    private final BigDecimal balance;

    public AmortizationRow(int installment,
                           BigDecimal capital,
                           BigDecimal interest,
                           BigDecimal insurance,
                           BigDecimal fee,
                           BigDecimal balance) {

        this.installment = installment;
        this.capital = capital;
        this.interest = interest;
        this.insurance = insurance;
        this.fee = fee;
        this.balance = balance;
    }

    public int getInstallment() { return installment; }
    public BigDecimal getCapital() { return capital; }
    public BigDecimal getInterest() { return interest; }
    public BigDecimal getInsurance() { return insurance; }
    public BigDecimal getFee() { return fee; }
    public BigDecimal getBalance() { return balance; }
}