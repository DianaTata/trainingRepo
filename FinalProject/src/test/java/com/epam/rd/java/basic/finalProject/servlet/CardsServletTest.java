package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.CardService;
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
public class CardsServletTest {

    @InjectMocks
    private CardsServlet cardsServlet;

    @Mock
    private CardService cardService;

    @Mock
    private PaginationMapper paginationMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Mock
    private CardDTO cardDTO;

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
        when(servletContext.getAttribute("sortCards")).thenReturn(sortBy);
        when(servletContext.getAttribute("cardService")).thenReturn(cardService);
        when(servletContext.getAttribute("paginationMapper")).thenReturn(paginationMapper);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getParameter("sortBy")).thenReturn("cardNumber asc");
        when(req.getParameter("userId")).thenReturn("1");
        when(sortBy.getOrDefault("cardNumber asc", "cardNumber asc")).thenReturn("cardNumber asc");
        when(paginationMapper.fillPaginationDTO(req)).thenReturn(paginationDTO);
        when(paginationDTO.getCurrentPage()).thenReturn(1);
        when(cardService.getUserCardsCount(1)).thenReturn(1);
        when(paginationDTO.getAmountOfItems()).thenReturn(1);
        when(cardService.getUserCards(1, paginationDTO)).thenReturn(Collections.singletonList(cardDTO));
        when(req.getRequestDispatcher("userCards.jsp")).thenReturn(requestDispatcher);

        cardsServlet.doGet(req, resp);

        verify(paginationDTO).setSortBy("cardNumber asc");
        verify(req).setAttribute("numberOfPages", 1);
        verify(req).setAttribute("cardsNumber", 1);
        verify(req).setAttribute("paginationDTO", paginationDTO);
        verify(req).setAttribute("currPage", 1);
        verify(req).setAttribute("sort", "cardNumber asc");
        verify(req).setAttribute("cards", Collections.singletonList(cardDTO));
        verify(requestDispatcher).forward(req, resp);
    }

}