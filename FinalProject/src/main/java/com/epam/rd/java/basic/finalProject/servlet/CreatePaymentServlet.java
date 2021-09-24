package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
import com.epam.rd.java.basic.finalProject.service.impl.CountServiceImpl;
import com.epam.rd.java.basic.finalProject.service.impl.PaymentServiceImpl;
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

@WebServlet("/createPayment")
public class CreatePaymentServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreatePaymentServlet.class);

    private CountService countService;
    private PaymentService paymentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        countService = (CountService) getServletContext().getAttribute("countService");
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Get payment result");
        if (Objects.nonNull(req.getSession().getAttribute("message"))) {
        String message = (String) req.getSession().getAttribute("message");
        req.getSession().removeAttribute("message");
        req.setAttribute("message", message);
        req.getRequestDispatcher("paymentResult.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.debug("starting create payment");
        CountDTO count = countService.getCountByNumber(Integer.parseInt(req.getParameter("toCount")),
                CountStatus.OPENED.getName());
        if(Objects.nonNull(count)) {
            boolean result = paymentService.insertPayment(Integer.parseInt(req.getParameter("fromCount")),
                    Integer.parseInt(req.getParameter("toCount")),
                    BigDecimal.valueOf(Integer.parseInt(req.getParameter("amount"))));
            fillMessage(req, result);
            resp.sendRedirect(req.getContextPath() + "/createPayment");
            LOGGER.debug("finish create payment");
        } else {
            req.getSession().setAttribute("message", "wrong.count");
            resp.sendRedirect(req.getContextPath() + "/createPayment");
            LOGGER.debug("finish create payment");
        }
    }

    private void fillMessage(HttpServletRequest request, boolean result) {
        if (result) {
            request.getSession().setAttribute("message", "your.payment.was.created");
        } else {
            request.getSession().setAttribute("message", "your.payment.wasnt.created");
        }
    }

}
