package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
import com.epam.rd.java.basic.finalProject.service.SendEmailService;
import com.epam.rd.java.basic.finalProject.service.impl.SendEmailServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sendPdf")
public class SendPdfServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SendPdfServlet.class);

    private PaymentService paymentService;
    private SendEmailService sendEmailService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
        sendEmailService = (SendEmailService) getServletContext().getAttribute("sendEmailService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("SendPDF doGet started");
        String paymentId = req.getParameter("paymentId");
        PaymentDTO paymentById = paymentService.getPaymentById(Integer.parseInt(paymentId));
        boolean result = sendEmailService.sendEmailWithPdf(paymentById);
        fillMessage(req, paymentById, result);
        req.getRequestDispatcher("sendEmailResult.jsp").forward(req, resp);
        LOGGER.debug("SendPDF doGet finished");
    }

    private void fillMessage(HttpServletRequest req, PaymentDTO paymentById, boolean result) {
        if (result) {
            req.setAttribute("message", "report.was.send.to");
            req.setAttribute("mail", paymentById.getUser().getEmail());
        } else {
            req.setAttribute("message", "sending.faild");
        }
    }
}
