package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.service.CardService;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.impl.CardServiceImpl;
import com.epam.rd.java.basic.finalProject.service.impl.CountServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@WebServlet("/addCard")
public class ManageCardServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ManageCardServlet.class);

    private CardService cardService;
    private CountService countService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cardService = (CardService) getServletContext().getAttribute("cardService");
        countService = (CountService) getServletContext().getAttribute("countService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getSession().getAttribute("message"))) {
        String refill = (String) req.getSession().getAttribute("message");
        req.getSession().removeAttribute("message");
        req.setAttribute("message", refill);
        req.getRequestDispatcher("cardResult.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CardDTO cardDTO  = cardService.getCardByNumber(request.getParameter("cardNumber"));
        if (Objects.isNull(cardDTO)) {
            CardDTO card = fillCardDTO(request);
            countService.linkCountToCard(card);
            request.getSession().setAttribute("message", "your.card.was.added");
            response.sendRedirect(request.getContextPath() + "/addCard");
            LOGGER.info("Redirecting to " + request.getContextPath() + "/addCard");
            LOGGER.info("ManageCard#doPost Finished");
        }else {
            request.getSession().setAttribute("message", "this.card.already.exists");
            response.sendRedirect(request.getContextPath() + "/addCard");
            LOGGER.info("Redirecting to " + request.getContextPath() + "/addCard");
            LOGGER.info("ManageCard#doPost Finished");
        }

    }

    private CardDTO fillCardDTO(HttpServletRequest request) {
        CardDTO card = new CardDTO();
        card.setCardNumber(request.getParameter("cardNumber"));
        card.setCvv(Integer.parseInt(request.getParameter("cvv")));
        card.setExpiredDate(Date.valueOf(request.getParameter("expiredDate")));
        card.setAmount(BigDecimal.valueOf(Integer.parseInt(request.getParameter("amount"))));
        card.setUserDTO((UserDTO) request.getSession().getAttribute("user"));
        return card;
    }
}
