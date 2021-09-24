package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Date;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CardMapperTest {

    public static final int ID = 1;
    public static final String NUMBER = "11";
    public static final int CVV = 111;
    public static final Date DATE = Date.valueOf("2020-02-02");

    @InjectMocks
    @Spy
    private CardMapper cardMapper;

    @Mock
    private CardDTO cardDtoMock;

    @Mock
    private Card cardMock;

    @Mock
    private UserDTO userDTO;

    @Mock
    private User user;

    @Mock
    private UserMapper userMapper;

    private Card card;
    private CardDTO cardDto;

    @Before
    public void setUp() {
        card = new Card();
        card.setId(ID);
        card.setCardNumber(NUMBER);
        card.setCvv(CVV);
        card.setExpiredDate(DATE);
        card.setAmount(BigDecimal.ONE);
        card.setUser(user);
        cardDto = new CardDTO();
        cardDto.setId(ID);
        cardDto.setCardNumber(NUMBER);
        cardDto.setCvv(CVV);
        cardDto.setExpiredDate(DATE);
        cardDto.setAmount(BigDecimal.ONE);
        cardDto.setUserDTO(userDTO);
    }

    @Test
    public void shouldMapToEntity() {
        when(cardDtoMock.getId()).thenReturn(ID);
        when(cardDtoMock.getCardNumber()).thenReturn(NUMBER);
        when(cardDtoMock.getCvv()).thenReturn(CVV);
        when(cardDtoMock.getExpiredDate()).thenReturn(DATE);
        when(cardDtoMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(cardDtoMock.getUserDTO()).thenReturn(userDTO);
        when(userMapper.mapToEntity(userDTO)).thenReturn(user);

        Card actual = cardMapper.mapToEntity(cardDtoMock);

        Assert.assertEquals(card, actual);
    }

    @Test
    public void shouldMapToDTO() {
        when(cardMock.getId()).thenReturn(ID);
        when(cardMock.getCardNumber()).thenReturn(NUMBER);
        when(cardMock.getCvv()).thenReturn(CVV);
        when(cardMock.getExpiredDate()).thenReturn(DATE);
        when(cardMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(cardMock.getUser()).thenReturn(user);
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);

        CardDTO actual = cardMapper.mapToDTO(cardMock);

        Assert.assertEquals(cardDto, actual);
    }
}