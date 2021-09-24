package com.epam.rd.java.basic.finalProject.entity;

public enum PaymentStatus {
    PREPARED("prepared"), SENT("sent");

    private String name;

    PaymentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
