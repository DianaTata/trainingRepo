package com.epam.rd.java.basic.finalProject.dto;

import com.epam.rd.java.basic.finalProject.entity.CountStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CountDTO implements Serializable {
    private static final long serialVersionUID = 129348932L;

    private int id;
    private int countNumber;
    private String countName;
    private BigDecimal amount;
    private UserDTO user;
    private CardDTO card;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
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
        CountDTO countDTO = (CountDTO) o;
        return id == countDTO.id && countNumber == countDTO.countNumber && Objects.equals(countName, countDTO.countName) && Objects.equals(amount, countDTO.amount) && Objects.equals(user, countDTO.user) && Objects.equals(card, countDTO.card) && statusName == countDTO.statusName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countNumber, countName, amount, user, card, statusName);
    }

    @Override
    public String toString() {
        return "CountDTO{" +
                "countNumber=" + countNumber +
                ", countName='" + countName + '\'' +
                ", amount=" + amount +
                ", user=" + user +
                ", card=" + card +
                ", statusName=" + statusName +
                '}';
    }
}
