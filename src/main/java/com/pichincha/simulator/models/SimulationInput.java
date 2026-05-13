package com.pichincha.simulator.models;

import java.math.BigDecimal;

public class SimulationInput {

    private final BigDecimal userAmount;
    private final BigDecimal approvedCapital;
    private final int term;
    private final BigDecimal annualRate;

    public SimulationInput(BigDecimal userAmount,
                           BigDecimal approvedCapital,
                           int term,
                           BigDecimal annualRate) {

        this.userAmount = userAmount;
        this.approvedCapital = approvedCapital;
        this.term = term;
        this.annualRate = annualRate;
    }

    public BigDecimal getUserAmount() {
        return userAmount;
    }

    public BigDecimal getApprovedCapital() {
        return approvedCapital;
    }

    public int getTerm() {
        return term;
    }

    public BigDecimal getAnnualRate() {
        return annualRate;
    }
}