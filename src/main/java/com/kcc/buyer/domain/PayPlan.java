package com.kcc.buyer.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PayPlan {

    private Integer id;

    private String describe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planDate;

    private String payRatio;

    private Double payMoney;

    private Integer orderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getPayRatio() {
        return payRatio;
    }

    public void setPayRatio(String payRatio) {
        this.payRatio = payRatio;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PayPlan{" +
                "id=" + id +
                ", describe='" + describe + '\'' +
                ", planDate=" + planDate +
                ", payRatio='" + payRatio + '\'' +
                ", payMoney=" + payMoney +
                ", orderId=" + orderId +
                '}';
    }
}
