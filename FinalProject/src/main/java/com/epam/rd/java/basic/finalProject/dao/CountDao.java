package com.epam.rd.java.basic.finalProject.dao;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.exception.CountException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public interface CountDao {

    Count insertCount(Count count, Connection connection) throws CountException;

    Count findCountByCardId(int id) throws CountException;

    boolean linkCountToCard(Card card) throws CountException;

    boolean updateCountAmount(BigDecimal amount, Connection connection, int countId) throws CountException;

    boolean refillCount(int cardId, int countId, BigDecimal amount) throws CountException;

    Count findCountById(int countId) throws CountException;

    List<Count> getUserCounts(int userId, PaginationDTO paginationDTO) throws CountException;

    List<Count> getUserCounts(int userId, String status) throws CountException;

    Count findCountByNumber(int number, String statusName) throws CountException;

    boolean updateCountStatus(int countId, String status, Connection connection) throws CountException;

    boolean updateCountStatus(int countId, String status) throws CountException;

    int getUserCountsNumber(int userId) throws CountException;

    boolean withdrawMoney(int cardId, int countId, BigDecimal amount) throws CountException;
}
