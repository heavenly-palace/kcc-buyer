package com.kcc.buyer.domain;

import java.io.Serializable;

public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 7101313205923875627L;

    private Integer id;

    private Integer orderId;

    private String currency;

    private String describe;

    private String pack;

    private Double price;

    private Double aTPrice;

    private Double totalPrice;

    private Double aTTotalPrice;

    private Integer quantity;

    private Double taxRate;

    private String manufacturerName;

    private String productModel;

    private String type;

    public OrderDetail() {
    }

    public OrderDetail(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack == null ? null : pack.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getaTPrice() {
        return aTPrice;
    }

    public void setaTPrice(Double aTPrice) {
        this.aTPrice = aTPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getaTTotalPrice() {
        return aTTotalPrice;
    }

    public void setaTTotalPrice(Double aTTotalPrice) {
        this.aTTotalPrice = aTTotalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", describe='" + describe + '\'' +
                ", pack='" + pack + '\'' +
                ", price=" + price +
                ", aTPrice=" + aTPrice +
                ", totalPrice=" + totalPrice +
                ", aTTotalPrice=" + aTTotalPrice +
                ", quantity=" + quantity +
                ", taxRate=" + taxRate +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", productModel='" + productModel + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}