package com.kcc.buyer.domain;

public class ProductType {

    private Integer id;

    private String productType;

    private Integer supplierId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", supplierId=" + supplierId +
                '}';
    }
}
