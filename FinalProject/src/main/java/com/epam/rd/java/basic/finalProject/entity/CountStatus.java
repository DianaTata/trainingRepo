package com.epam.rd.java.basic.finalProject.entity;

public enum CountStatus {
    OPENED("opened"), BLOCKED("blocked");

    private String name;

    CountStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
