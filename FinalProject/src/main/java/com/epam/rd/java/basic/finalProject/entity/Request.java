package com.epam.rd.java.basic.finalProject.entity;

import java.sql.Date;
import java.util.Objects;

public class Request {
    private int id;
    private int requestNumber;
    private User user;
    private Date requestDate;
    private RequestStatus statusName;
    private Count count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getStatusName() {
        return statusName;
    }

    public void setStatusName(RequestStatus statusName) {
        this.statusName = statusName;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id && requestNumber == request.requestNumber && Objects.equals(user, request.user) && Objects.equals(requestDate, request.requestDate) && statusName == request.statusName && Objects.equals(count, request.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestNumber, user, requestDate, statusName, count);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestNumber=" + requestNumber +
                ", requestDate=" + requestDate +
                ", statusName=" + statusName +
                '}';
    }
}
