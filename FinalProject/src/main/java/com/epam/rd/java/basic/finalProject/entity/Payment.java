package com.epam.rd.java.basic.finalProject.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Payment {
    private int id;
    private int paymentNumber;
    private User user;
    private Date paymentDate;
    private BigDecimal amount;
    private PaymentStatus statusName;
    private Count fromCount;
    private Count toCount;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Count getFromCount() {
        return fromCount;
    }

    public void setFromCount(Count fromCount) {
        this.fromCount = fromCount;
    }

    public Count getToCount() {
        return toCount;
    }

    public void setToCount(Count toCount) {
        this.toCount = toCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id
                && paymentNumber == payment.paymentNumber
                && Objects.equals(user, payment.user)
                && Objects.equals(paymentDate, payment.paymentDate)
                && Objects.equals(amount, payment.amount)
                && statusName == payment.statusName
                && Objects.equals(fromCount, payment.fromCount)
                && Objects.equals(toCount, payment.toCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentNumber, user, paymentDate, amount, statusName, fromCount, toCount);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentNumber=" + paymentNumber +
                ", user=" + user +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", statusName=" + statusName +
                ", fromAccount=" + fromCount +
                ", toAccount=" + toCount +
                '}';
    }
}
