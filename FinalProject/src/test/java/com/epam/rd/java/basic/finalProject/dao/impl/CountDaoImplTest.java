package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.apache.commons.lang3.RandomStringUtils;
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
public class CountDaoImplTest {

    public static final int TEST_COUNT_NUMBER = 1234;
    public static final String TEST_COUNT_NAME = "ABSD";
    public static final int TEST_CARD_ID = 1;
    public static final int TEST_COUNT_ID = 1;
    public static final int TEST_USER_ID = 1;
    public static final int TEST_CVV = 123;
    public static final Date EXPIRED_DATE = Date.valueOf(LocalDate.now());
    @InjectMocks
    @Spy
    private CountDaoImpl countDaoImpl;

    @Mock
    private Count count;
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
    public void shouldInsertCount() throws SQLException, CountException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(count.getCountNumber()).thenReturn(TEST_COUNT_NUMBER);
        when(count.getCountName()).thenReturn(TEST_COUNT_NAME);
        when(count.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(TEST_USER_ID);
        when(count.getCard()).thenReturn(card);
        when(card.getId()).thenReturn(TEST_CARD_ID);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(TEST_COUNT_ID);

        Count actual = countDaoImpl.insertCount(count, connection);

        verify(preparedStatement).setInt(1, TEST_COUNT_NUMBER);
        verify(preparedStatement).setString(2, TEST_COUNT_NAME);
        verify(preparedStatement).setInt(3, TEST_USER_ID);
        verify(preparedStatement).setInt(4, TEST_CARD_ID);
        verify(count).setId(TEST_COUNT_ID);
        Assert.assertEquals(count, actual);
    }

    @Test
    public void shouldFinCountByNumber() throws SQLException, CountException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        countDaoImpl = new CountDaoImpl(cardDao, new UserDaoImpl());
        Count expected = countDaoImpl.findCountById(38);
        Count actual = countDaoImpl.findCountByNumber(1111, CountStatus.OPENED.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldUpdateCountStatus() throws CountException {
        boolean actual = countDaoImpl.updateCountStatus(11, CountStatus.OPENED.getName());

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserCountsNumber() throws CountException {
        int actual = countDaoImpl.getUserCountsNumber(20);

        Assert.assertTrue(actual != 0);
    }

    @Test
    public void shouldUpdateCountStatusInTransaction() throws CountException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean actual = countDaoImpl.updateCountStatus(TEST_COUNT_ID, CountStatus.OPENED.getName(), connection);

        verify(preparedStatement).setString(1, CountStatus.OPENED.getName());
        verify(preparedStatement).setInt(2, TEST_COUNT_ID);
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldFinCountById() throws SQLException, CountException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        countDaoImpl = new CountDaoImpl(cardDao, new UserDaoImpl());
        Connection connection = DBManager.getInstance().getConnection();
        Count expected = countDaoImpl.findCountByNumber(1111, CountStatus.OPENED.getName());
        Count actual = countDaoImpl.findCountById(38);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFinCountByCardId() throws SQLException, CountException {
        Connection connection = DBManager.getInstance().getConnection();
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        countDaoImpl = new CountDaoImpl(cardDao, new UserDaoImpl());
        Count expected = countDaoImpl.findCountById(38);
        Count actual = countDaoImpl.findCountByCardId(58);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldLinkCountToCard() throws CountException, UserException {
        UserDao userDao = new UserDaoImpl();
        CardDao cardDao = new CardDaoImpl(userDao);
        countDaoImpl = new CountDaoImpl(cardDao, userDao);
        Card expected = new Card();
        expected.setCardNumber(RandomStringUtils.random(12, false, true).toUpperCase());
        expected.setCvv(TEST_CVV);
        expected.setExpiredDate(EXPIRED_DATE);
        expected.setAmount(BigDecimal.ONE);
        expected.setUser(userDao.findUserById(20));
        boolean actual = countDaoImpl.linkCountToCard(expected);
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldUpdateCountAmount() throws SQLException, CountException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean actual = countDaoImpl.updateCountAmount(BigDecimal.ONE, connection, TEST_COUNT_ID);

        verify(preparedStatement).setBigDecimal(1, BigDecimal.ONE);
        verify(preparedStatement).setInt(2, TEST_COUNT_ID);
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldRefillCount() throws CountException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        countDaoImpl = new CountDaoImpl(cardDao, new UserDaoImpl());
        boolean actual = countDaoImpl.refillCount(58, 38, BigDecimal.valueOf(10));
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserCounts() throws CountException {
        UserDao user = new UserDaoImpl();
        CardDao cardDao = new CardDaoImpl(user);
        countDaoImpl = new CountDaoImpl(cardDao, user);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setAmountOfItems(5);
        paginationDTO.setOffset(0);
        paginationDTO.setSortBy("countNumber asc");
        List<Count> actual = countDaoImpl.getUserCounts(20, paginationDTO);

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void shouldGetUserCountsStatus() throws CountException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        countDaoImpl = new CountDaoImpl(cardDao, new UserDaoImpl());
        List<Count> actual = countDaoImpl.getUserCounts(20, CountStatus.OPENED.getName());

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void shouldWithdrawMoney() throws CountException {
        CardDao cardDao = new CardDaoImpl(new UserDaoImpl());
        countDaoImpl = new CountDaoImpl(cardDao, new UserDaoImpl());
        boolean actual = countDaoImpl.withdrawMoney(58, 38, BigDecimal.valueOf(10));
        Assert.assertTrue(actual);
    }


}