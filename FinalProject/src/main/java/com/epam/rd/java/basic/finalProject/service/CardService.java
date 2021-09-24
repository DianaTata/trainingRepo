package com.epam.rd.java.basic.finalProject.service;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.exception.CountException;

import java.math.BigDecimal;
import java.util.List;

/**
 * class to work with card operations
 */
public interface CardService {

    /**
     * method to retrieve card by number
     *
     * @param number - number of card
     * @return cardDto
     */
    CardDTO getCardByNumber(String number) throws CardException;

    /**
     * method to retrieve card by id
     *
     * @param id - id of card
     * @return cardDto
     */
    CardDTO getCardById(int id) throws CardException;

    /**
     * method to retrieve card by user id
     *
     * @param userId        - id of user
     * @param paginationDTO - paginationDTO
     * @return List CardDto
     */
    List<CardDTO> getUserCards(int userId, PaginationDTO paginationDTO) throws CardException;

    /**
     * method to withdraw the count
     *
     * @param cardId  - id of card
     * @param countId - id of count
     * @param amount  - amount
     * @return - result operation withdraw
     */
    boolean withdrawMoney(int cardId, int countId, BigDecimal amount) throws CountException;

    /**
     * method to retrieve card by user id
     *
     * @param userId - id of user
     * @return amount cards user
     */
    int getUserCardsCount(int userId) throws CardException;
}
