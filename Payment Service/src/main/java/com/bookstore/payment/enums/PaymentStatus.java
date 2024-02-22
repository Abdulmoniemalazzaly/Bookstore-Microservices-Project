package com.bookstore.payment.enums;

public enum PaymentStatus {
    NOT_CONFIRMED("NOT_CONFIRMED"),CANCELED("CANCELED"),CONFIRMED("CONFIRMED"),REJECTED("REJECTED");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
