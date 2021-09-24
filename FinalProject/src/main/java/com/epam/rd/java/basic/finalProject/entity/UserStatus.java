package com.epam.rd.java.basic.finalProject.entity;

public enum UserStatus {
    UNLOCKED("unlocked"), LOCKED("locked");

    private String name;

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
