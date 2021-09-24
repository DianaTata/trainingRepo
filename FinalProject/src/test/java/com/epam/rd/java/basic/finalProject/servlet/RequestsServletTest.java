package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.RequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestsServletTest {


    @InjectMocks
    private RequestsServlet requestsServlet;

    @Mock
    private RequestService requestService;

    @Mock
    private PaginationMapper paginationMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Mock
    private RequestDTO requestDTO;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void shouldInit() {
        when(servletContext.getAttribute("requestService")).thenReturn(requestService);
        when(servletContext.getAttribute("paginationMapper")).thenReturn(paginationMapper);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(paginationMapper.fillPaginationDTO(req)).thenReturn(paginationDTO);
        when(paginationDTO.getCurrentPage()).thenReturn(1);
        when(requestService.getUserRequestsCount(RequestStatus.INPROGRESS.getName())).thenReturn(1);
        when(paginationDTO.getAmountOfItems()).thenReturn(1);
        when(requestService.getRequests(RequestStatus.INPROGRESS.getName(), paginationDTO)).thenReturn(Collections.singletonList(requestDTO));
        when(req.getRequestDispatcher("requests.jsp")).thenReturn(requestDispatcher);

        requestsServlet.doGet(req, resp);

        verify(req).setAttribute("numberOfPages", 1);
        verify(req).setAttribute("requestsCount", 1);
        verify(req).setAttribute("paginationDTO", paginationDTO);
        verify(req).setAttribute("currPage", 1);
        verify(req).setAttribute("request", Collections.singletonList(requestDTO));
        verify(requestDispatcher).forward(req, resp);
    }
}