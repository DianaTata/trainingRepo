package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.impl.CountServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cardMy")
public class CardServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CardsServlet.class);

    private CountService countService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        countService = (CountService) getServletContext().getAttribute("countService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardId = req.getParameter("cardId");
        CountDTO count = countService.getCountByCardId(Integer.parseInt(cardId));
        if (CountStatus.OPENED.equals(count.getStatusName())) {
            req.setAttribute("count", count);
            req.getRequestDispatcher("withdraw.jsp").forward(req, resp);
            LOGGER.debug("Forward count");
        } else {
            req.setAttribute("message", "your.count.is.blocked");
            req.getRequestDispatcher("blockCountResult.jsp").forward(req, resp);
            LOGGER.debug("Forward account");
        }

    }
}
