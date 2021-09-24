package com.epam.rd.java.basic.finalProject.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ErrorServletTest {
    @InjectMocks
    private ErrorServlet errorServlet;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getRequestDispatcher("error.jsp")).thenReturn(requestDispatcher);

        errorServlet.doGet(req, resp);

        verify(requestDispatcher).forward(req, resp);
    }
}