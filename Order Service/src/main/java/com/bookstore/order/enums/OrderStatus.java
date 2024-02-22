package com.bookstore.order.enums;

public enum OrderStatus {
    PLACED("PLACED"),CANCELED("CANCELED"),IN_ACTION("IN_ACTION"),DELIVERED("DELIVERED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
