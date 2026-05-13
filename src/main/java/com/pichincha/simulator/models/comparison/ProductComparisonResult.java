package com.pichincha.simulator.models.comparison;

public class ProductComparisonResult {

    public static final String MEMORY_KEY = "PRODUCT_COMPARISON_RESULT";

    private final ProductSimulationSnapshot firstProduct;
    private final ProductSimulationSnapshot secondProduct;

    public ProductComparisonResult(ProductSimulationSnapshot firstProduct,
                                   ProductSimulationSnapshot secondProduct) {
        this.firstProduct = firstProduct;
        this.secondProduct = secondProduct;
    }

    public ProductSimulationSnapshot getFirstProduct() {
        return firstProduct;
    }

    public ProductSimulationSnapshot getSecondProduct() {
        return secondProduct;
    }
}
