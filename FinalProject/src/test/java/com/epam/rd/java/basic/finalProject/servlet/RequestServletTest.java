package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.exception.RequestException;
import com.epam.rd.java.basic.finalProject.service.CardService;
import com.epam.rd.java.basic.finalProject.service.CountService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestServletTest {

    @InjectMocks
    private RequestServlet requestServlet;

    @Mock
    private RequestService requestService;

    @Mock
    private CountService countService;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDTO requestDTO;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void shouldInit(){
        when(servletContext.getAttribute("requestService")).thenReturn(requestService);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("message")).thenReturn(any());
        when(session.getAttribute("message")).thenReturn("message");
        when(req.getRequestDispatcher("sendRequestResult.jsp")).thenReturn(requestDispatcher);

        requestServlet.doGet(req, resp);

        verify(session).removeAttribute(any());
        verify(req).setAttribute("request", "message");
        verify(requestDispatcher).forward(req, resp);
    }

    @Test
    public void shouldDoPast() throws IOException, ServletException {
        when(req.getParameter("countId")).thenReturn("1");
        when(requestService.getRequestByCountIdAndStatus((1),
                RequestStatus.INPROGRESS.getName())).thenReturn(requestDTO);
        when(requestService.createRequest(1)).thenReturn(requestDTO);
        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("test");

        requestServlet.doPost(req, resp);

        verify(resp).sendRedirect("test" + "/sendRequest");
        verify(session).setAttribute("message", "request.already.exists");
    }
}