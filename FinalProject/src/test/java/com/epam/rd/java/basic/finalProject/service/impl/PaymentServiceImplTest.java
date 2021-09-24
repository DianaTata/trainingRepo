package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.PaymentDao;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.entity.Payment;
import com.epam.rd.java.basic.finalProject.exception.PaymentException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

    public static final int INT = 1;

    @InjectMocks
    @Spy
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentDao paymentDao;

    @Mock
    private PaymentDTO paymentDTO;

    @Mock
    private Payment payment;

    @Mock
    private Mapper<PaymentDTO, Payment> paymentMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetPaymentById() throws PaymentException {
        when(paymentDao.findPaymentById(INT)).thenReturn(payment);
        when(paymentMapper.mapToDTO(payment)).thenReturn(paymentDTO);

        PaymentDTO actual = paymentService.getPaymentById(INT);

        Assert.assertEquals(paymentDTO, actual);
    }

    @Test
    public void shouldMakePayment() throws PaymentException {
        when(paymentMapper.mapToEntity(paymentDTO)).thenReturn(payment);
        when(paymentDao.makePayment(payment)).thenReturn(true);

        boolean actual = paymentService.makePayment(paymentDTO);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserPayments() throws PaymentException {
        when(paymentDao.getUserPayments(INT, paginationDTO)).thenReturn(Collections.singletonList(payment));
        when(paymentMapper.mapToDTO(payment)).thenReturn(paymentDTO);

        List<PaymentDTO> actual = paymentService.getUserPayments(INT, paginationDTO);

        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(paymentDTO, actual.stream().findFirst().get());
    }

    @Test
    public void shouldDeclinePayment() throws PaymentException {
        when(paymentDao.deletePayment(1)).thenReturn(true);

        boolean actual = paymentService.declinePayment(INT);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserPaymentsCount() throws PaymentException {
        when(paymentDao.getUserPaymentsNumber(INT)).thenReturn(INT);

        int actual = paymentService.getUserPaymentsCount(INT);

        Assert.assertEquals(INT, actual);
    }
}