package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
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
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentsServletTest {

    @InjectMocks
    private PaymentsServlet paymentsServlet;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PaginationMapper paginationMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Mock
    private PaymentDTO paymentDTO;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private Map<String, String> sortBy;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void shouldInit() {
        when(servletContext.getAttribute("sortPayments")).thenReturn(sortBy);
        when(servletContext.getAttribute("paymentService")).thenReturn(paymentService);
        when(servletContext.getAttribute("paginationMapper")).thenReturn(paginationMapper);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getParameter("sortBy")).thenReturn("paymentNumber asc");
        when(req.getParameter("userId")).thenReturn("1");
        when(sortBy.getOrDefault("paymentNumber asc", "paymentNumber asc")).thenReturn("paymentNumber asc");
        when(paginationMapper.fillPaginationDTO(req)).thenReturn(paginationDTO);
        when(paginationDTO.getCurrentPage()).thenReturn(1);
        when(paymentService.getUserPaymentsCount(1)).thenReturn(1);
        when(paginationDTO.getAmountOfItems()).thenReturn(1);
        when(paymentService.getUserPayments(1, paginationDTO)).thenReturn(Collections.singletonList(paymentDTO));
        when(req.getRequestDispatcher("userPayments.jsp")).thenReturn(requestDispatcher);

        paymentsServlet.doGet(req, resp);

        verify(paginationDTO).setSortBy("paymentNumber asc");
        verify(req).setAttribute("numberOfPages", 1);
        verify(req).setAttribute("paymentsNumber", 1);
        verify(req).setAttribute("paginationDTO", paginationDTO);
        verify(req).setAttribute("currPage", 1);
        verify(req).setAttribute("sort", "paymentNumber asc");
        verify(req).setAttribute("payments", Collections.singletonList(paymentDTO));
        verify(requestDispatcher).forward(req, resp);
    }

}