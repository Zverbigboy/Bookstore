package com.s3k3l3v.bookstore.entity;


import java.io.Serializable;
import java.sql.Date;

public class UserOrderBean implements Serializable{
    private static final long serialVersionUID = -5654982557199337483L;

    private long orderId;

    private String userFirstName;

    private String userLastName;

    private int orderBill;

    private String statusName;

    private Date datetimes;

    private int fine;

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public Date getDatetimes() {
        return datetimes;
    }

    public void setDatetimes(Date datetimes) {
        this.datetimes = datetimes;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public int getOrderBill() {
        return orderBill;
    }

    public void setOrderBill(int orderBill) {
        this.orderBill = orderBill;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "UserOrderBean{" +
                "orderId=" + orderId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", orderBill=" + orderBill +
                ", statusName='" + statusName + '\'' +
                ", datetimes=" + datetimes +
                ", fine=" + fine +
                '}';
    }
}
