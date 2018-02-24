package com.s3k3l3v.bookstore.entity;

import java.io.Serializable;

public class Order implements Serializable{

    private static final long serialVersionUID = 56879565974102L;

    private Integer id;

    private Integer bill;

    private Integer userId;

    private Integer statusId;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", bill=" + bill +
                ", userId=" + userId +
                ", statusId=" + statusId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}
