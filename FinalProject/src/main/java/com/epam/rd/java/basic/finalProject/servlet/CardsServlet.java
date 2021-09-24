package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.CardService;
import com.epam.rd.java.basic.finalProject.service.UserService;
import com.epam.rd.java.basic.finalProject.service.impl.CardServiceImpl;
import com.mysql.jdbc.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/cards")
public class CardsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CardsServlet.class);
    public static final String DEFAULT_SORT = "cardNumber asc";

    private CardService cardService;
    private PaginationMapper paginationMapper;
    private Map<String, String> sortMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sortMap = (HashMap) getServletContext().getAttribute("sortCards");
        cardService = (CardService) getServletContext().getAttribute("cardService");
        paginationMapper = (PaginationMapper) getServletContext().getAttribute("paginationMapper");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sort = request.getParameter("sortBy");
        String userId = request.getParameter("userId");
        String cardSort = sortMap.getOrDefault(sort, DEFAULT_SORT);
        PaginationDTO paginationDTO = paginationMapper.fillPaginationDTO(request);
        paginationDTO.setSortBy(cardSort);
        int currPage = paginationDTO.getCurrentPage();
        int cardsNumber = cardService.getUserCardsCount(Integer.parseInt(userId));
        int numberOfPages = 1;
        if (cardsNumber > 0) {
            numberOfPages = (int) Math.ceil(cardsNumber * 1.0 / paginationDTO.getAmountOfItems());
        }
        List<CardDTO> cards = cardService.getUserCards(Integer.parseInt(userId), paginationDTO);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("cardsNumber", cardsNumber);
        request.setAttribute("paginationDTO", paginationDTO);
        request.setAttribute("currPage", currPage);
        request.setAttribute("sort", sort);
        request.setAttribute("cards", cards);
        request.getRequestDispatcher("userCards.jsp").forward(request, response);
        LOGGER.debug("Forward to user cards");
    }
}
