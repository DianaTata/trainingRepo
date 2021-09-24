package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Payment;
import com.epam.rd.java.basic.finalProject.entity.PaymentStatus;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.exception.PaymentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentDaoImplTest {

    public static final int TEST_CVV = 123;
    public static final Date EXPIRED_DATE = Date.valueOf(LocalDate.now());

    @InjectMocks
    @Spy
    private PaymentDaoImpl paymentDao;

    @Mock
    private UserDao userDao;
    @Mock
    private CountDao countDao;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    private UserDao userDaoImpl = new UserDaoImpl();
    private CardDao cardDao = new CardDaoImpl(userDaoImpl);
    private CountDao countDaoImpl = new CountDaoImpl(cardDao, userDaoImpl);

    @Test
    public void shouldInsertPayment() throws SQLException, PaymentException, CountException {
        Count fromCount = countDaoImpl.findCountById(38);
        Count toCount = countDaoImpl.findCountById(39);
        Payment payment = createPayment(fromCount, toCount, BigDecimal.ONE);

        boolean actual = paymentDao.insertPayment(payment);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldMakePayment() throws SQLException, PaymentException, CountException {
        Count fromCount = countDaoImpl.findCountById(38);
        Count toCount = countDaoImpl.findCountById(39);
        Payment payment = createPayment(fromCount, toCount, BigDecimal.ZERO);

        boolean actual = paymentDao.makePayment(payment);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldFindPaymentById() throws PaymentException {
        Payment actual = paymentDao.findPaymentById(16);

        Assert.assertNotNull(actual);
    }

    @Test
    public void shouldGetNullWhenFindPaymentById() throws PaymentException {
        Payment actual = paymentDao.findPaymentById(-1);

        Assert.assertNull(actual);
    }

    @Test
    public void shouldGetUserCounts() throws PaymentException {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setAmountOfItems(5);
        paginationDTO.setOffset(0);
        paginationDTO.setSortBy("paymentNumber");
        List<Payment> actual = paymentDao.getUserPayments(20, paginationDTO);

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void shouldUpdatePaymentStatus() throws SQLException, PaymentException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean actual = paymentDao.updatePaymentStatus(9, PaymentStatus.PREPARED.getName(), connection);

        verify(preparedStatement).setString(1, PaymentStatus.PREPARED.getName());
        verify(preparedStatement).setInt(2, 9);
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldDeletePayment() throws PaymentException {
        boolean actual = paymentDao.deletePayment(-1);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserPaymentsNumber() throws PaymentException {
        int actual = paymentDao.getUserPaymentsNumber(20);

        Assert.assertTrue(actual != 0);
    }

    private Payment createPayment(Count fromCount, Count toCount, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setPaymentNumber(new SecureRandom().nextInt(899999) + 100000);
        payment.setPaymentDate(Date.valueOf(LocalDate.now()));
        payment.setAmount(amount);
        payment.setFromCount(fromCount);
        payment.setToCount(toCount);
        payment.setUser(fromCount.getUser());
        return payment;
    }

}