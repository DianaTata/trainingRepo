package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountMapperTest {

    public static final int ID = 1;
    public static final int NUMBER = 1111;
    public static final String NAME = "ABCD";

    @InjectMocks
    @Spy
    private CountMapper countMapper;

    @Mock
    private CountDTO countDTOMock;

    @Mock
    private Count countMock;

    @Mock
    private Card card;

    @Mock
    private CardDTO cardDTO;

    @Mock
    private User user;

    @Mock
    private UserDTO userDTO;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CardMapper cardMapper;

    private Count count;
    private CountDTO countDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        count = new Count();
        count.setId(ID);
        count.setCountNumber(NUMBER);
        count.setCountName(NAME);
        count.setAmount(BigDecimal.ONE);
        count.setUser(user);
        count.setCard(card);
        count.setStatusName(CountStatus.OPENED);
        countDTO = new CountDTO();
        countDTO.setId(ID);
        countDTO.setCountNumber(NUMBER);
        countDTO.setCountName(NAME);
        countDTO.setAmount(BigDecimal.ONE);
        countDTO.setUser(userDTO);
        countDTO.setCard(cardDTO);
        countDTO.setStatusName(CountStatus.OPENED);
    }

    @Test
    public void shouldMapToEntity() {
        when(countDTOMock.getId()).thenReturn(ID);
        when(countDTOMock.getCountNumber()).thenReturn(NUMBER);
        when(countDTOMock.getCountName()).thenReturn(NAME);
        when(countDTOMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(countDTOMock.getUser()).thenReturn(userDTO);
        when(countDTOMock.getCard()).thenReturn(cardDTO);
        when(countDTOMock.getStatusName()).thenReturn(CountStatus.OPENED);
        when(userMapper.mapToEntity(userDTO)).thenReturn(user);
        when(cardMapper.mapToEntity(cardDTO)).thenReturn(card);

        Count actual = countMapper.mapToEntity(countDTOMock);

        Assert.assertEquals(count, actual);
    }

    @Test
    public void shouldMapToDTO() {
        when(countMock.getId()).thenReturn(ID);
        when(countMock.getCountNumber()).thenReturn(NUMBER);
        when(countMock.getCountName()).thenReturn(NAME);
        when(countMock.getAmount()).thenReturn(BigDecimal.ONE);
        when(countMock.getUser()).thenReturn(user);
        when(countMock.getCard()).thenReturn(card);
        when(countMock.getStatusName()).thenReturn(CountStatus.OPENED);
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);
        when(cardMapper.mapToDTO(card)).thenReturn(cardDTO);

        CountDTO actual = countMapper.mapToDTO(countMock);

        Assert.assertEquals(countDTO, actual);
    }
}