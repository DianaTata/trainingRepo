package com.epam.rd.java.basic.finalProject.dto;

import com.epam.rd.java.basic.finalProject.entity.RequestStatus;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class RequestDTO implements Serializable {
    private static final long serialVersionUID = 129348931L;

    private int id;
    private int requestNumber;
    private UserDTO user;
    private Date requestDate;
    private RequestStatus statusName;
    private CountDTO count;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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

    public CountDTO getCount() {
        return count;
    }

    public void setCount(CountDTO count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDTO that = (RequestDTO) o;
        return id == that.id && requestNumber == that.requestNumber && Objects.equals(user, that.user) && Objects.equals(requestDate, that.requestDate) && statusName == that.statusName && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestNumber, user, requestDate, statusName, count);
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "requestNumber=" + requestNumber +
                ", requestDate=" + requestDate +
                ", statusName=" + statusName +
                '}';
    }
}
