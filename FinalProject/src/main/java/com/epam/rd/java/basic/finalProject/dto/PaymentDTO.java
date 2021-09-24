package com.epam.rd.java.basic.finalProject.dto;

import com.epam.rd.java.basic.finalProject.entity.PaymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class PaymentDTO implements Serializable {
    private static final long serialVersionUID = 129348931L;

    private int id;
    private int paymentNumber;
    private UserDTO user;
    private Date paymentDate;
    private BigDecimal amount;
    private PaymentStatus statusName;
    private CountDTO fromCount;
    private CountDTO toCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(int paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatusName() {
        return statusName;
    }

    public void setStatusName(PaymentStatus statusName) {
        this.statusName = statusName;
    }

    public CountDTO getFromCount() {
        return fromCount;
    }

    public void setFromCount(CountDTO fromCount) {
        this.fromCount = fromCount;
    }

    public CountDTO getToCount() {
        return toCount;
    }

    public void setToCount(CountDTO toCount) {
        this.toCount = toCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDTO that = (PaymentDTO) o;
        return id == that.id && paymentNumber == that.paymentNumber
                && Objects.equals(user, that.user)
                && Objects.equals(paymentDate, that.paymentDate)
                && Objects.equals(amount, that.amount) && statusName == that.statusName
                && Objects.equals(fromCount, that.fromCount)
                && Objects.equals(toCount, that.toCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentNumber, user, paymentDate, amount, statusName, fromCount, toCount);
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "paymentNumber=" + paymentNumber +
                ", user=" + user +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", statusName=" + statusName +
                ", fromCount=" + fromCount +
                ", toCount=" + toCount +
                '}';
    }
}
