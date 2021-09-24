package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceImplTest {

    public static final int INT = 1;
    public static final int EXPECTED = 0;
    public static final String STRING = "1";
    @InjectMocks
    @Spy
    private CardServiceImpl cardService;

    @Mock
    private CardDao cardDao;

    @Mock
    private CardDTO cardDTO;

    @Mock
    private Card card;

    @Mock
    private CountDao countDao;

    @Mock
    private Mapper<CardDTO, Card> cardMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Test
    public void shouldGetCardByNumber() throws CardException {
        when(cardDao.findCardByNumber(STRING)).thenReturn(card);
        when(cardMapper.mapToDTO(card)).thenReturn(cardDTO);

        CardDTO actual = cardService.getCardByNumber(STRING);

        Assert.assertEquals(cardDTO, actual);
    }

    @Test
    public void shouldNotGetCardByNumber() throws CardException {
        when(cardDao.findCardByNumber(STRING)).thenReturn(null);

        CardDTO actual = cardService.getCardByNumber(STRING);

        Assert.assertNull(actual);
    }

    @Test
    public void shouldNotGetCardById() throws CardException {
        when(cardDao.findCardById(INT)).thenReturn(null);

        CardDTO actual = cardService.getCardById(INT);

        Assert.assertNull(actual);
    }

    @Test
    public void shouldGetCardById() throws CardException {
        when(cardDao.findCardById(INT)).thenReturn(card);
        when(cardMapper.mapToDTO(card)).thenReturn(cardDTO);

        CardDTO actual = cardService.getCardById(INT);

        Assert.assertEquals(cardDTO, actual);
    }

    @Test
    public void shouldGetUserCards() throws CardException {
        when(cardDao.getUserCards(INT, paginationDTO)).thenReturn(Collections.singletonList(card));
        when(cardMapper.mapToDTO(card)).thenReturn(cardDTO);

        List<CardDTO> actual = cardService.getUserCards(1, paginationDTO);

        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(cardDTO, actual.stream().findFirst().get());
    }

    @Test
    public void shouldGetEmptyListWhenNoUserCards() throws CardException {
        when(cardDao.getUserCards(INT, paginationDTO)).thenReturn(Collections.emptyList());
        when(cardMapper.mapToDTO(card)).thenReturn(cardDTO);

        List<CardDTO> actual = cardService.getUserCards(INT, paginationDTO);

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldWithdrawMoney() throws CountException {
        when(countDao.withdrawMoney(INT, INT, BigDecimal.ONE)).thenReturn(true);

        boolean actual = cardService.withdrawMoney(INT, INT, BigDecimal.ONE);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldNotWithdrawMoney() throws CountException {
        when(countDao.withdrawMoney(INT, INT, BigDecimal.ONE)).thenReturn(false);

        boolean actual = cardService.withdrawMoney(INT, INT, BigDecimal.ONE);

        Assert.assertFalse(actual);
    }

    @Test
    public void shouldGetUserCardsCount() throws CardException {
        when(cardDao.getUserCardsNumber(INT)).thenReturn(INT);

        int actual = cardService.getUserCardsCount(INT);

        Assert.assertEquals(INT, actual);
    }

    @Test(expected = CardException.class)
    public void shouldGetUserCardsCountException() {
        when(cardDao.getUserCardsNumber(INT)).thenThrow(new CardException());
        cardService.getUserCardsCount(INT);
    }
}