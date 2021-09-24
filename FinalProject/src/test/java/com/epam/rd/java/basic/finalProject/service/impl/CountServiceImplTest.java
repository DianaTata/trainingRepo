package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.RequestDao;
import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.exception.CountException;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountServiceImplTest {

    public static final int INT = 1;

    @InjectMocks
    @Spy
    private CountServiceImpl countService;


    @Mock
    private CardDTO cardDTO;

    @Mock
    private Card card;

    @Mock
    private CountDao countDao;

    @Mock
    private Count count;

    @Mock
    private CountDTO countDTO;

    @Mock
    private Mapper<CardDTO, Card> cardMapper;

    @Mock
    private Mapper<CountDTO, Count> countMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetCountByCardId() throws CountException {
        when(countDao.findCountByCardId(INT)).thenReturn(count);
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);

        CountDTO actual = countService.getCountByCardId(1);

        Assert.assertEquals(countDTO, actual);
    }

    @Test
    public void shouldLinkCountToCard() throws CountException {
        when(cardMapper.mapToEntity(cardDTO)).thenReturn(card);
        when(countDao.linkCountToCard(card)).thenReturn(true);

        boolean actual = countService.linkCountToCard(cardDTO);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldNotLinkCountToCard() {
        when(cardMapper.mapToEntity(cardDTO)).thenReturn(null);

        boolean actual = countService.linkCountToCard(cardDTO);

        Assert.assertFalse(actual);
    }

    @Test
    public void shouldrefillCount() throws CountException {
        when(countDao.refillCount(INT, INT, BigDecimal.ONE)).thenReturn(true);

        boolean actual = countService.refillCount(INT, INT, BigDecimal.ONE);

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetCountById() throws CountException {
        when(countDao.findCountById(INT)).thenReturn(count);
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);

        CountDTO actual = countService.getCountById(INT);

        Assert.assertEquals(countDTO, actual);
    }

    @Test
    public void shouldGetUserCounts() throws CountException {
        when(countDao.getUserCounts(INT, paginationDTO)).thenReturn(Collections.singletonList(count));
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);

        List<CountDTO> actual = countService.getUserCounts(INT, paginationDTO);

        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(countDTO, actual.stream().findFirst().get());
    }

    @Test
    public void shouldGetUserCountsStatus() throws CountException {
        when(countDao.getUserCounts(1, CountStatus.OPENED.getName())).thenReturn(Collections.singletonList(count));
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);

        List<CountDTO> actual = countService.getUserCounts(INT, CountStatus.OPENED.getName());

        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(countDTO, actual.stream().findFirst().get());
    }

    @Test
    public void shouldGetCountByNumber() throws CountException {
        when(countDao.findCountByNumber(INT, CountStatus.OPENED.getName())).thenReturn(count);
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);

        CountDTO actual = countService.getCountByNumber(INT, CountStatus.OPENED.getName());

        Assert.assertEquals(countDTO, actual);
    }

    @Test
    public void shouldPerformBlockCount() throws CountException {
        when(countDao.updateCountStatus(INT, CountStatus.OPENED.getName())).thenReturn(true);

        boolean actual = countService.performBlockCount(INT, CountStatus.OPENED.getName());

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserCountsNumber() throws CountException {
        when(countDao.getUserCountsNumber(INT)).thenReturn(INT);

        int actual = countService.getUserCountsNumber(INT);

        Assert.assertEquals(INT, actual);
    }
}