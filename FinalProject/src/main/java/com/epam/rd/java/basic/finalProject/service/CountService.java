package com.epam.rd.java.basic.finalProject.service;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.exception.CountException;

import java.math.BigDecimal;
import java.util.List;

/**
 * class to work with count operations
 */
public interface CountService {

    /**
     * method to retrieve count by card id
     *
     * @param id - id of count
     * @return CountDTO
     */
    CountDTO getCountByCardId(int id) throws CountException;

    /**
     * method to retrieve count
     *
     * @param cardDTO - cardDTO
     * @return result operation linkCountToCard
     */
    boolean linkCountToCard(CardDTO cardDTO) throws CountException;

    /**
     * method to refill the count
     *
     * @param cardId   - id of card
     * @param countId- id of count
     * @param amount   - amount
     * @return result operation  refillCount
     */
    boolean refillCount(int cardId, int countId, BigDecimal amount) throws CountException;

    /**
     * method to retrieve count by id
     *
     * @param countId - id of count
     * @return CountDTO
     */
    CountDTO getCountById(int countId) throws CountException;

    /**
     * method to retrieve counts by user id
     *
     * @param userId        - id of user
     * @param paginationDTO - paginationDTO
     * @return List CountDTO
     */
    List<CountDTO> getUserCounts(int userId, PaginationDTO paginationDTO) throws CountException;

    /**
     * method to retrieve counts by user id and count status
     *
     * @param userId - id of user
     * @param status - count status
     * @return List CountDTO
     */
    List<CountDTO> getUserCounts(int userId, String status) throws CountException;

    /**
     * method to retrieve counts by user number
     *
     * @param number     - count number
     * @param statusName - count status
     * @return count by number
     */
    CountDTO getCountByNumber(int number, String statusName) throws CountException;

    /**
     * method to changes count status
     *
     * @param countId - id of count
     * @param status  - count status
     * @return result operation performBlockCount
     */
    boolean performBlockCount(int countId, String status) throws CountException;

    /**
     * method to retrieve count number by user id
     *
     * @param userId - id of user
     * @return amount cards user
     */
    int getUserCountsNumber(int userId) throws CountException;

}
