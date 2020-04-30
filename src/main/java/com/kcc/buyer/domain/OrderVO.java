package com.kcc.buyer.domain;

import java.util.List;

public class OrderVO extends Order {

    private CompanyVO supplier;

    private CompanyVO buyer;

    private BuyerAgent buyerAgent;

    private List<OrderDetail> orderDetailList;

    public BuyerAgent getBuyerAgent() {
        return buyerAgent;
    }

    public void setBuyerAgent(BuyerAgent buyerAgent) {
        this.buyerAgent = buyerAgent;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public CompanyVO getSupplier() {
        return supplier;
    }

    public void setSupplier(CompanyVO supplier) {
        this.supplier = supplier;
    }

    public CompanyVO getBuyer() {
        return buyer;
    }

    public void setBuyer(CompanyVO buyer) {
        this.buyer = buyer;
    }
}
