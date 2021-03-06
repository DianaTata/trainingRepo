package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.service.CardService;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
import com.epam.rd.java.basic.finalProject.service.impl.PaymentServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/approvePayment")
public class PaymentApproveServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PaymentApproveServlet.class);

    private PaymentService paymentService;
    private CountService countService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
        countService = (CountService) getServletContext().getAttribute("countService");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paymentId = req.getParameter("paymentId");
        String fromCount = req.getParameter("fromCount");
        String toCount = req.getParameter("toCount");
        CountDTO fromCountById = countService.getCountById(Integer.parseInt(fromCount));
        CountDTO toCountById = countService.getCountById(Integer.parseInt(toCount));
        PaymentDTO paymentById = paymentService.getPaymentById(Integer.parseInt(paymentId));
        if (Objects.nonNull(paymentById)  && fromCountById.getStatusName().equals(CountStatus.OPENED)
                && toCountById.getStatusName().equals(CountStatus.OPENED)) {
            boolean result = paymentService.makePayment(paymentById);
            fillMessage(req, result);
            resp.sendRedirect(req.getContextPath() + "/approvePayment");
        } else {
            req.getSession().setAttribute("message", "dont.approve");
            resp.sendRedirect(req.getContextPath() + "/approvePayment");
            LOGGER.info("Redirecting to " + req.getContextPath() + "/approvePayment");
            LOGGER.info("Sent payment #doPost Finished");
        }
    }

    private void fillMessage(HttpServletRequest req, boolean result) {
        if (result) {
            req.getSession().setAttribute("message", "your.payment.was.approved");
        } else {
            req.getSession().setAttribute("message", "your.payment.wasnt.approved");
        }
    }
}
