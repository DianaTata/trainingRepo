package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
import com.epam.rd.java.basic.finalProject.service.impl.CountServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PaymentServlet.class);

    private CountService countService;
    PaymentService paymentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        countService = (CountService) getServletContext().getAttribute("countService");
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        List<CountDTO> countDTOList = countService.getUserCounts(Integer.parseInt(userId), CountStatus.OPENED.getName());
        req.setAttribute("counts", countDTOList);
        req.getRequestDispatcher("createPayment.jsp").forward(req, resp);
        LOGGER.debug("Forward to create payment");
    }
}
