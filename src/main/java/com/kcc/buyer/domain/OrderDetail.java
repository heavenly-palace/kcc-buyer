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

    private Integer quantity;

    private String supplyDate;

    private Double taxRate;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(String supplyDate) {
        this.supplyDate = supplyDate == null ? null : supplyDate.trim();
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
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
                ", quantity=" + quantity +
                ", supplyDate='" + supplyDate + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }
}