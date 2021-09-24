package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
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
public class PaymentServletTest {

    @InjectMocks
    private PaymentServlet paymentServlet;

    @Mock
    private PaymentService paymentService;

    @Mock
    private CountService countService;

    @Mock
    private CountDTO countDTO;

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
        when(servletContext.getAttribute("countService")).thenReturn(countService);
        when(servletContext.getAttribute("paymentService")).thenReturn(paymentService);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getParameter("userId")).thenReturn("1");
        when(countService.getUserCounts(Integer.parseInt("1"),
                CountStatus.OPENED.getName())).thenReturn(Collections.singletonList(countDTO));
        when(req.getRequestDispatcher("createPayment.jsp")).thenReturn(requestDispatcher);

        paymentServlet.doGet(req, resp);

        verify(req).setAttribute("counts", Collections.singletonList(countDTO));
        verify(requestDispatcher).forward(req, resp);
    }

}