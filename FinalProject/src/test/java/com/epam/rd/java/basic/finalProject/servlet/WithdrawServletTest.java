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
import java.math.BigDecimal;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WithdrawServletTest {

    @InjectMocks
    private WithdrawServlet withdrawServlet;

    @Mock
    private CardService cardService;

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
    private CountDTO countById;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void shouldInit(){
        when(servletContext.getAttribute("cardService")).thenReturn(cardService);
        when(servletContext.getAttribute("countService")).thenReturn(countService);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("message")).thenReturn(any());
        when(session.getAttribute("message")).thenReturn("message");
        when(req.getRequestDispatcher("withdrawResult.jsp")).thenReturn(requestDispatcher);

        withdrawServlet.doGet(req, resp);

        verify(session).removeAttribute(any());
        verify(req).setAttribute("message", "message");
        verify(requestDispatcher).forward(req, resp);
    }

    @Test
    public void shouldDoPast() throws ServletException, IOException {
        when(req.getParameter("cardId")).thenReturn("1");
        when(req.getParameter("countId")).thenReturn("1");
        when(req.getParameter("amount")).thenReturn("1");
        when(countById.getStatusName()).thenReturn(CountStatus.OPENED);
        when(countService.getCountById(Integer.parseInt("1"))).thenReturn(countById);
        when(cardService.withdrawMoney(1,1, BigDecimal.ONE)).thenReturn(true);
        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("test");

        withdrawServlet.doPost(req, resp);

        verify(resp).sendRedirect("test" + "/withdraw");
        verify(session).setAttribute("message", "withdraw.success");
    }

}