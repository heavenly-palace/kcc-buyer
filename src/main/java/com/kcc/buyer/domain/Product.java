package com.kcc.buyer.domain;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = -3136473120816263736L;

    private Integer id;

    private String no;

    private Integer companyId;

    private String currency;

    private String describe;

    private String pack;

    private Double price;

    private Double taxRate;

    private Integer type;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", companyId=" + companyId +
                ", currency='" + currency + '\'' +
                ", describe='" + describe + '\'' +
                ", pack='" + pack + '\'' +
                ", price=" + price +
                ", taxRate=" + taxRate +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}