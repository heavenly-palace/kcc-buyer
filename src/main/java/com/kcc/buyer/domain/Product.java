package com.kcc.buyer.domain;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = -3979081763631666793L;

    private Integer id;

    private String no;

    private Integer companyId;

    private String describe;

    private String currency;

    private String pack;

    private Double price;

    private Double taxRate;

    private Double aTPrice;

    private String type;

    private Integer status;

    public Product() {
    }

    public Product(Integer companyId) {
        this.companyId = companyId;
    }

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
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

    public Double getaTPrice() {
        return aTPrice;
    }

    public void setaTPrice(Double aTPrice) {
        this.aTPrice = aTPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
                ", describe='" + describe + '\'' +
                ", currency='" + currency + '\'' +
                ", pack='" + pack + '\'' +
                ", price=" + price +
                ", taxRate=" + taxRate +
                ", aTPrice=" + aTPrice +
                ", type='" + type + '\'' +
                ", status=" + status +
                '}';
    }
}