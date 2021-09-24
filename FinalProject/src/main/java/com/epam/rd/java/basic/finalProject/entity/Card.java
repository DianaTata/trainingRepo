package com.epam.rd.java.basic.finalProject.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Card {
    private int id;
    private String cardNumber;
    private int cvv;
    private Date expiredDate;
    private BigDecimal amount;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id
                && cvv == card.cvv
                && Objects.equals(user, card.user)
                && Objects.equals(cardNumber, card.cardNumber)
                && Objects.equals(expiredDate, card.expiredDate)
                && Objects.equals(amount, card.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, cvv, expiredDate, amount, user);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv=" + cvv +
                ", expiredDate=" + expiredDate +
                ", amount=" + amount +
                ", user=" + user +
                '}';
    }
}
