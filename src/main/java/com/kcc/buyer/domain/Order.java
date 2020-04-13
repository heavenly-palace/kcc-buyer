package com.kcc.buyer.domain;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = 644897864154466065L;

    private Integer id;

    private String comment;

    private String orderUuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid == null ? null : orderUuid.trim();
    }
}