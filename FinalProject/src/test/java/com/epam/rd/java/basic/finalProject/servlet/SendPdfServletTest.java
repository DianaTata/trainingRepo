package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
import com.epam.rd.java.basic.finalProject.service.SendEmailService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendPdfServletTest {


    @InjectMocks
    private SendPdfServlet sendPdfServlet;

    @Mock
    private PaymentService paymentService;

    @Mock
    private SendEmailService sendEmailService;

    @Mock
    private PaymentDTO paymentDTO;

    @Mock
    private UserDTO user;

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
        when(servletContext.getAttribute("paymentService")).thenReturn(paymentService);
        when(servletContext.getAttribute("sendEmailService")).thenReturn(sendEmailService);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getParameter("paymentId")).thenReturn("1");
        when(paymentService.getPaymentById(Integer.parseInt("1"))).thenReturn(paymentDTO);
        when(sendEmailService.sendEmailWithPdf(paymentDTO)).thenReturn(true);
        when(paymentDTO.getUser()).thenReturn(user);
        when(user.getEmail()).thenReturn("test");
        when(req.getRequestDispatcher("sendEmailResult.jsp")).thenReturn(requestDispatcher);

        sendPdfServlet.doGet(req, resp);

        verify(req).setAttribute("message", "report.was.send.to");
        verify(req).setAttribute("mail", "test");
        verify(requestDispatcher).forward(req, resp);
    }

}