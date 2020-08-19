package com.kcc.buyer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class PastComment {

    private Integer id;

    private String comment;

    private String currentStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp createTime;

    private Integer orderId;

    public PastComment() {
    }

    public PastComment(Integer orderId) {
        this.orderId = orderId;
    }

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
        this.comment = comment;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PastComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", createTime=" + createTime +
                ", orderId=" + orderId +
                '}';
    }
}
