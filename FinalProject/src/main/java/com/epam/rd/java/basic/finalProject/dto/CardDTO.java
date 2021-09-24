package com.epam.rd.java.basic.finalProject.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class CardDTO implements Serializable {
    private static final long serialVersionUID = 129348933L;

    private int id;
    private String cardNumber;
    private int cvv;
    private Date expiredDate;
    private BigDecimal amount;
    private UserDTO userDTO;

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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return id == cardDTO.id && cvv == cardDTO.cvv && Objects.equals(cardNumber, cardDTO.cardNumber) && Objects.equals(expiredDate, cardDTO.expiredDate) && Objects.equals(amount, cardDTO.amount) && Objects.equals(userDTO, cardDTO.userDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, cvv, expiredDate, amount, userDTO);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv=" + cvv +
                ", expiredDate=" + expiredDate +
                ", amount=" + amount +
                ", userDTO=" + userDTO +
                '}';
    }
}
