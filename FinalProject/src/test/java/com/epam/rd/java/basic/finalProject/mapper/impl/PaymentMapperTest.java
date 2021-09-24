package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Payment;
import com.epam.rd.java.basic.finalProject.entity.PaymentStatus;
import com.epam.rd.java.basic.finalProject.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentMapperTest {

    public static final int ID = 1;
    public static final int NUMBER = 111111;
    public static final Date DATE = Date.valueOf("2020-02-02");

    @InjectMocks
    @Spy
    private PaymentMapper paymentMapper;

    @Mock
    private CountMapper countMapper;

    @Mock
    private CountDTO countDTO;

    @Mock
    private Count count;

    @Mock
    private PaymentDTO paymentDTOMock;

    @Mock
    private Payment paymentMock;

    @Mock
    private User user;

    @Mock
    private UserDTO userDTO;

    @Mock
    private UserMapper userMapper;

    private Payment payment;
    private PaymentDTO paymentDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        payment = new Payment();
        payment.setId(ID);
        payment.setPaymentNumber(NUMBER);
        payment.setUser(user);
        payment.setPaymentDate(DATE);
        payment.setAmount(BigDecimal.ONE);
        payment.setFromCount(count);
        payment.setToCount(count);
        payment.setStatusName(PaymentStatus.PREPARED);
        paymentDTO = new PaymentDTO();
        paymentDTO.setId(ID);
        paymentDTO.setPaymentNumber(NUMBER);
        paymentDTO.setUser(userDTO);
        paymentDTO.setPaymentDate(DATE);
        paymentDTO.setAmount(BigDecimal.ONE);
        paymentDTO.setFromCount(countDTO);
        paymentDTO.setToCount(countDTO);
        paymentDTO.setStatusName(PaymentStatus.PREPARED);
    }

    @Test
    public void shouldMapToEntity() {
        when(paymentDTOMock.getId()).thenReturn(ID);
        when(paymentDTOMock.getPaymentNumber()).thenReturn(NUMBER);
        when(paymentDTOMock.getUser()).thenReturn(userDTO);
        when(paymentDTOMock.getPaymentDate()).thenReturn(DATE);
        when(paymentDTOMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(paymentDTOMock.getFromCount()).thenReturn(countDTO);
        when(paymentDTOMock.getToCount()).thenReturn(countDTO);
        when(paymentDTOMock.getStatusName()).thenReturn(PaymentStatus.PREPARED);
        when(userMapper.mapToEntity(userDTO)).thenReturn(user);
        when(countMapper.mapToEntity(countDTO)).thenReturn(count);
        when(countMapper.mapToEntity(countDTO)).thenReturn(count);

        Payment actual = paymentMapper.mapToEntity(paymentDTOMock);

        Assert.assertEquals(payment, actual);
    }

    @Test
    public void shouldMapToDTO() {
        when(paymentMock.getId()).thenReturn(ID);
        when(paymentMock.getPaymentNumber()).thenReturn(NUMBER);
        when(paymentMock.getUser()).thenReturn(user);
        when(paymentMock.getPaymentDate()).thenReturn(DATE);
        when(paymentMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(paymentMock.getFromCount()).thenReturn(count);
        when(paymentMock.getToCount()).thenReturn(count);
        when(paymentMock.getStatusName()).thenReturn(PaymentStatus.PREPARED);
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);

        PaymentDTO actual = paymentMapper.mapToDTO(paymentMock);

        Assert.assertEquals(paymentDTO, actual);
    }
}