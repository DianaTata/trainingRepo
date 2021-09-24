package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.service.CardService;
import com.epam.rd.java.basic.finalProject.service.CountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardServletTest {


    @InjectMocks
    private CardServlet cardServlet;

    @Mock
    private CardService cardService;

    @Mock
    private CountService countService;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private CountDTO count;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void shouldInit(){
        when(servletContext.getAttribute("countService")).thenReturn(countService);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getParameter("cardId")).thenReturn("1");
        when(countService.getCountByCardId(Integer.parseInt("1"))).thenReturn(count);
        when(count.getStatusName()).thenReturn(CountStatus.OPENED);
        when(req.getRequestDispatcher("withdraw.jsp")).thenReturn(requestDispatcher);

        cardServlet.doGet(req, resp);

        verify(req).setAttribute("count", count);
        verify(requestDispatcher).forward(req, resp);
    }
}