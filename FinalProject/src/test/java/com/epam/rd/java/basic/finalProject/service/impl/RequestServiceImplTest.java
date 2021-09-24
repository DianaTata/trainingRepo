package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.RequestDao;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.entity.Request;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.exception.RequestException;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceImplTest {

    public static final int INT = 1;

    @InjectMocks
    @Spy
    private RequestServiceImpl requestService;


    @Mock
    private CountDao countDao;

    @Mock
    private Count count;

    @Mock
    private Request request;

    @Mock
    private RequestDao requestDao;

    @Mock
    private RequestDTO requestDTO;

    @Mock
    private Mapper<RequestDTO, Request> requestMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetRequestByCountIdAndStatus() throws RequestException {
        when(requestDao.findRequestByCountIdAndStatus(INT, RequestStatus.INPROGRESS.getName())).thenReturn(request);
        when(requestMapper.mapToDTO(request)).thenReturn(requestDTO);

        RequestDTO actual = requestService.getRequestByCountIdAndStatus(INT, RequestStatus.INPROGRESS.getName());

        Assert.assertEquals(requestDTO, actual);
    }

    @Test
    public void shouldCreateRequest() throws CountException, RequestException {
        when(countDao.findCountById(INT)).thenReturn(count);
        when(requestDao.insertRequest(count)).thenReturn(request);
        when(requestMapper.mapToDTO(request)).thenReturn(requestDTO);

        RequestDTO actual = requestService.createRequest(INT);

        Assert.assertEquals(requestDTO, actual);
    }

    @Test
    public void shouldPerformRequest() throws RequestException {
        when(requestDao.performRequest(INT, CountStatus.OPENED.getName(), RequestStatus.INPROGRESS.getName())).thenReturn(true);

        boolean actual = requestService.performRequest(INT, CountStatus.OPENED.getName(), RequestStatus.INPROGRESS.getName());

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserRequestsCount() throws RequestException {
        when(requestDao.getUserRequestsNumber(RequestStatus.INPROGRESS.getName())).thenReturn(INT);

        int actual = requestService.getUserRequestsCount(RequestStatus.INPROGRESS.getName());

        Assert.assertEquals(INT, actual);
    }
}