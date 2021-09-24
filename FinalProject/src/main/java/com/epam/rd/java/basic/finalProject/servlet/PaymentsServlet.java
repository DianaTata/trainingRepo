package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
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

@WebServlet("/payments")
public class PaymentsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PaymentsServlet.class);
    public static final String DEFAULT_SORT = "paymentNumber asc";

    private PaymentService paymentService;
    private PaginationMapper paginationMapper;
    private Map<String, String> sortMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sortMap = (HashMap) getServletContext().getAttribute("sortPayments");
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
        paginationMapper = (PaginationMapper) getServletContext().getAttribute("paginationMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sort = req.getParameter("sortBy");
        String userId = req.getParameter("userId");
        String paymentSort = sortMap.getOrDefault(sort, DEFAULT_SORT);
        PaginationDTO paginationDTO = paginationMapper.fillPaginationDTO(req);
        paginationDTO.setSortBy(paymentSort);
        int currPage = paginationDTO.getCurrentPage();
        int paymentsNumber = paymentService.getUserPaymentsCount(Integer.parseInt(userId));
        int numberOfPages = 1;
        if (paymentsNumber > 0) {
            numberOfPages = (int) Math.ceil(paymentsNumber * 1.0 / paginationDTO.getAmountOfItems());
        }
        List<PaymentDTO> payments = paymentService.getUserPayments(Integer.parseInt(userId), paginationDTO);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("paymentsNumber", paymentsNumber);
        req.setAttribute("paginationDTO", paginationDTO);
        req.setAttribute("currPage", currPage);
        req.setAttribute("sort", sort);
        req.setAttribute("payments", payments);
        req.getRequestDispatcher("userPayments.jsp").forward(req, resp);
        LOGGER.debug("Forward to user payments");
    }
}
