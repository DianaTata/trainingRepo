package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardDaoImplTest {

    public static final String TEST_CARD_NUMBER = "123456789012";
    public static final int TEST_CVV = 123;
    public static final Date EXPIRED_DATE = Date.valueOf(LocalDate.now());
    public static final int TEST_CARD_ID = 1;
    public static final int TEST_USER_ID = 1;
    public static final int USER_ID_CREATED = 20;

    @InjectMocks
    @Spy
    private CardDaoImpl cardDao;

    @Mock
    private Card card;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private User user;


    @Test
    public void shouldInsertCard() throws SQLException, CardException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(card.getCardNumber()).thenReturn(TEST_CARD_NUMBER);
        when(card.getCvv()).thenReturn(TEST_CVV);
        when(card.getAmount()).thenReturn(BigDecimal.ONE);
        when(card.getExpiredDate()).thenReturn(EXPIRED_DATE);
        when(card.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(TEST_USER_ID);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(TEST_CARD_ID);

        Card actual = cardDao.insertCard(this.card, connection);

        verify(preparedStatement).setString(1, TEST_CARD_NUMBER);
        verify(preparedStatement).setInt(2, TEST_CVV);
        verify(preparedStatement).setDate(3, EXPIRED_DATE);
        verify(preparedStatement).setBigDecimal(4, BigDecimal.ONE);
        verify(preparedStatement).setInt(5, TEST_USER_ID);
        verify(card).setId(TEST_CARD_ID);
        Assert.assertEquals(card, actual);
    }

    @Test
    public void shouldUpdateCardAmount() throws SQLException, CardException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);;
        when(card.getAmount()).thenReturn(BigDecimal.ONE);
        when(card.getId()).thenReturn(TEST_CARD_ID);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        boolean actual = cardDao.updateCardAmount(BigDecimal.ONE,connection, TEST_CARD_ID);
        verify(preparedStatement).setBigDecimal(1, BigDecimal.ONE);
        verify(preparedStatement).setInt(2, TEST_CARD_ID);;
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldFindCardByNumber() throws SQLException, CardException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        Card expected = cardDao.findCardById(58);
        Card actual = cardDao.findCardByNumber("111111111111");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFindCardById() throws SQLException, CardException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        Card expected = cardDao.findCardByNumber("111111111111");
        Card actual = cardDao.findCardById(58);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetUserCards() throws CardException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setAmountOfItems(5);
        paginationDTO.setOffset(0);
        paginationDTO.setSortBy("cardNumber asc");
        List<Card> actual = cardDao.getUserCards(20, paginationDTO);

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void shouldGetUserCardsNumber() throws CardException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        int actual = cardDao.getUserCardsNumber(USER_ID_CREATED);

        Assert.assertTrue(actual > 0);
    }

}