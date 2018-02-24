package com.s3k3l3v.bookstore.entity;

public class OrderMenu {


    private int id;
    private int orderId;
    private int bookId;

    @Override
    public String toString() {
        return "OrderMenu{" +
                "id = " + id +
                ", orderId = " + orderId +
                ", bookId = " + bookId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
