package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
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
import java.util.Objects;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(WithdrawServlet.class);

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
            String message = (String) req.getSession().getAttribute("message");
            req.getSession().removeAttribute("message");
            req.setAttribute("message", message);
            req.getRequestDispatcher("withdrawResult.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardId = req.getParameter("cardId");
        String countId = req.getParameter("countId");
        String amount = req.getParameter("amount");
        CountDTO countById = countService.getCountById(Integer.parseInt(countId));
        if (Objects.nonNull(countById) && CountStatus.OPENED.equals(countById.getStatusName())) {
            boolean result = cardService.withdrawMoney(Integer.parseInt(cardId), Integer.parseInt(countId),
                    BigDecimal.valueOf(Integer.parseInt(amount)));
            fillMessage(req, result);
            resp.sendRedirect(req.getContextPath() + "/withdraw");
            LOGGER.info("Redirecting to " + req.getContextPath() + "/withdraw");
            LOGGER.info("WithdrawServlet#doPost Finished");
        } else {
            req.getSession().setAttribute("message", "withdraw.blocked.count");
            resp.sendRedirect(req.getContextPath() + "/withdraw");
            LOGGER.info("Redirecting to " + req.getContextPath() + "/withdraw");
            LOGGER.info("WithdrawServlet#doPost Finished");
        }
    }

    private void fillMessage(HttpServletRequest req, boolean result) {
        if (result) {
            req.getSession().setAttribute("message", "withdraw.success");
        } else {
            req.getSession().setAttribute("message", "withdraw.failed");
        }
    }
}
