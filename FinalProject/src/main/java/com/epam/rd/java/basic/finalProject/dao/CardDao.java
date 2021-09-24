package com.epam.rd.java.basic.finalProject.dao;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.exception.CardException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public interface CardDao {

    Card insertCard(Card card, Connection connection) throws CardException;

    Card findCardByNumber(String number) throws CardException;

    Card findCardById(int number) throws CardException;

    List<Card> getUserCards(int userId, PaginationDTO paginationDTO) throws CardException;

    boolean updateCardAmount(BigDecimal amount, Connection connection, int cardId) throws CardException;

    int getUserCardsNumber(int userId) throws CardException;
}
