package com.pichincha.simulator.models.comparison;

import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.models.AmortizationRow;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductSimulationSnapshot {

    private final String scenarioId;
    private final String productType;
    private final String requestedAmount;
    private final String term;
    private final AmortizationResult result;

    public ProductSimulationSnapshot(String scenarioId,
                                     String productType,
                                     String requestedAmount,
                                     String term,
                                     AmortizationResult result) {
        this.scenarioId = scenarioId;
        this.productType = productType;
        this.requestedAmount = requestedAmount;
        this.term = term;
        this.result = result;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public String getProductType() {
        return productType;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public String getTerm() {
        return term;
    }

    public AmortizationResult getResult() {
        return result;
    }

    public BigDecimal getMonthlyFee() {
        return safe(result.getMonthlyFee());
    }

    public BigDecimal getAnnualRate() {
        return result.getAnnualRate() == null ? BigDecimal.ZERO : result.getAnnualRate();
    }

    public BigDecimal getCapitalShownByUi() {
        return safe(result.getCapital());
    }

    public BigDecimal getAmortizationBase() {
        if (result.getRows() == null || result.getRows().isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        AmortizationRow firstRow = result.getRows().get(0);
        return firstRow.getCapital()
                .add(firstRow.getBalance())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public int getInstallments() {
        return result.getRows() == null ? 0 : result.getRows().size();
    }

    private BigDecimal safe(BigDecimal value) {
        return value == null
                ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
                : value.setScale(2, RoundingMode.HALF_UP);
    }
}
