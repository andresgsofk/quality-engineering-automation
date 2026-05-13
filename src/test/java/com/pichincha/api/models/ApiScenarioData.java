package com.pichincha.api.models;

public class ApiScenarioData {

    private Integer productId;
    private String category;
    private Integer limit;
    private Integer expectedStatusCode;
    private ProductPayload payload;

    public ApiScenarioData() {
    }

    public Integer getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getExpectedStatusCode() {
        return expectedStatusCode;
    }

    public ProductPayload getPayload() {
        return payload;
    }

    public boolean hasPayload() {
        return payload != null;
    }
}
