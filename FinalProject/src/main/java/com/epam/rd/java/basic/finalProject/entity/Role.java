package com.epam.rd.java.basic.finalProject.entity;

public enum Role {
    ADMIN("admin"), CLIENT("client");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
