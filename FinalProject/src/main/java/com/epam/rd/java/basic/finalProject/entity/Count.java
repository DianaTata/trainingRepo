package com.epam.rd.java.basic.finalProject.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Count {
    private int id;
    private int countNumber;
    private String countName;
    private BigDecimal amount;
    private User user;
    private Card card;
    private CountStatus statusName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(int countNumber) {
        this.countNumber = countNumber;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public CountStatus getStatusName() {
        return statusName;
    }

    public void setStatusName(CountStatus statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Count count = (Count) o;
        return id == count.id
                && countNumber == count.countNumber
                && Objects.equals(countName, count.countName)
                && Objects.equals(amount, count.amount)
                && Objects.equals(user, count.user)
                && Objects.equals(card, count.card)
                && statusName == count.statusName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countNumber, countName, amount, user, card, statusName);
    }

    @Override
    public String toString() {
        return "Count{" +
                "countNumber=" + countNumber +
                ", countName='" + countName + '\'' +
                ", amount=" + amount +
                ", user=" + user +
                ", card=" + card +
                ", statusName=" + statusName +
                '}';
    }
}
