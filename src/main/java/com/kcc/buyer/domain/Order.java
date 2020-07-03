package com.kcc.buyer.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private static final long serialVersionUID = -254530439029632817L;

    private Integer id;

    private String orderUuid;

    private Double money;

    private String upperMoney;

    private Double atMoney;

    private String upperAtMoney;

    private String comment;

    private String inComment;

    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CompanyInfo> companyInfoList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderDetail> orderDetailList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PayPlan> payPlanList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid == null ? null : orderUuid.trim();
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getUpperMoney() {
        return upperMoney;
    }

    public void setUpperMoney(String upperMoney) {
        this.upperMoney = upperMoney;
    }

    public Double getAtMoney() {
        return atMoney;
    }

    public void setAtMoney(Double atMoney) {
        this.atMoney = atMoney;
    }

    public String getUpperAtMoney() {
        return upperAtMoney;
    }

    public void setUpperAtMoney(String upperAtMoney) {
        this.upperAtMoney = upperAtMoney;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getInComment() {
        return inComment;
    }

    public void setInComment(String inComment) {
        this.inComment = inComment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CompanyInfo> getCompanyInfoList() {
        return companyInfoList;
    }

    public void setCompanyInfoList(List<CompanyInfo> companyInfoList) {
        this.companyInfoList = companyInfoList;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public List<PayPlan> getPayPlanList() {
        return payPlanList;
    }

    public void setPayPlanList(List<PayPlan> payPlanList) {
        this.payPlanList = payPlanList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderUuid='" + orderUuid + '\'' +
                ", money=" + money +
                ", upperMoney='" + upperMoney + '\'' +
                ", atMoney=" + atMoney +
                ", upperAtMoney='" + upperAtMoney + '\'' +
                ", comment='" + comment + '\'' +
                ", inComment='" + inComment + '\'' +
                ", status=" + status +
                ", companyInfoList=" + companyInfoList +
                ", orderDetailList=" + orderDetailList +
                ", payPlanList=" + payPlanList +
                '}';
    }
}