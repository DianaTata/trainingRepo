package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Request;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
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

import java.sql.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestMapperTest {

    public static final int ID = 1;
    public static final int NUMBER = 11;
    public static final Date DATE = Date.valueOf("2020-02-02");

    @InjectMocks
    @Spy
    private RequestMapper requestMapper;

    @Mock
    private CountMapper countMapper;

    @Mock
    private CountDTO countDTO;

    @Mock
    private Count count;

    @Mock
    private RequestDTO requestDTOMock;

    @Mock
    private Request requestMock;

    @Mock
    private User user;

    @Mock
    private UserDTO userDTO;

    @Mock
    private UserMapper userMapper;

    private Request request;
    private RequestDTO requestDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = new Request();
        request.setId(ID);
        request.setRequestNumber(NUMBER);
        request.setUser(user);
        request.setCount(count);
        request.setRequestDate(DATE);
        request.setStatusName(RequestStatus.INPROGRESS);
        requestDTO = new RequestDTO();
        requestDTO.setId(ID);
        requestDTO.setRequestNumber(NUMBER);
        requestDTO.setUser(userDTO);
        requestDTO.setCount(countDTO);
        requestDTO.setRequestDate(DATE);
        requestDTO.setStatusName(RequestStatus.INPROGRESS);
    }

    @Test
    public void shouldMapToEntity() {
        when(requestDTOMock.getId()).thenReturn(ID);
        when(requestDTOMock.getRequestNumber()).thenReturn(NUMBER);
        when(requestDTOMock.getUser()).thenReturn(userDTO);
        when(requestDTOMock.getCount()).thenReturn(countDTO);
        when(requestDTOMock.getRequestDate()).thenReturn(DATE);
        when(requestDTOMock.getStatusName()).thenReturn(RequestStatus.INPROGRESS);
        when(userMapper.mapToEntity(userDTO)).thenReturn(user);
        when(countMapper.mapToEntity(countDTO)).thenReturn(count);

        Request actual = requestMapper.mapToEntity(requestDTOMock);

        Assert.assertEquals(request, actual);
    }

    @Test
    public void shouldMapToDTO() {
        when(requestMock.getId()).thenReturn(ID);
        when(requestMock.getRequestNumber()).thenReturn(NUMBER);
        when(requestMock.getUser()).thenReturn(user);
        when(requestMock.getCount()).thenReturn(count);
        when(requestMock.getRequestDate()).thenReturn(DATE);
        when(requestMock.getStatusName()).thenReturn(RequestStatus.INPROGRESS);
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);
        when(countMapper.mapToDTO(count)).thenReturn(countDTO);

        RequestDTO actual = requestMapper.mapToDTO(requestMock);

        Assert.assertEquals(requestDTO, actual);
    }
}