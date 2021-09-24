package com.epam.rd.java.basic.finalProject.entity;

public enum RequestStatus {
    INPROGRESS("inProgress"), DONE("done");

    private String name;

    RequestStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
