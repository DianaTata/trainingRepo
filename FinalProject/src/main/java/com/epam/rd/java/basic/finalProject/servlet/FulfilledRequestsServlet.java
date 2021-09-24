package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
import com.epam.rd.java.basic.finalProject.service.RequestService;
import com.epam.rd.java.basic.finalProject.service.impl.RequestServiceImpl;
import com.mysql.jdbc.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/doneRequests")
public class FulfilledRequestsServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FulfilledRequestsServlet.class);

    private RequestService requestService;
    private PaginationMapper paginationMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        requestService = (RequestService) getServletContext().getAttribute("requestService");
        paginationMapper = (PaginationMapper) getServletContext().getAttribute("paginationMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaginationDTO paginationDTO = paginationMapper.fillPaginationDTO(req);
        int currPage = paginationDTO.getCurrentPage();
        int requestsCount = requestService.getUserRequestsCount(RequestStatus.DONE.getName());
        int numberOfPages = 1;
        if (requestsCount > 0) {
            numberOfPages = (int) Math.ceil(requestsCount * 1.0 / paginationDTO.getAmountOfItems());
        }
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("requestsCount", requestsCount);
        req.setAttribute("paginationDTO", paginationDTO);
        req.setAttribute("currPage", currPage);
        List<RequestDTO> requestDTO = requestService.getRequests(RequestStatus.DONE.getName(), paginationDTO);
        req.setAttribute("doneRequests", requestDTO);
        req.getRequestDispatcher("doneRequests.jsp").forward(req, resp);
        LOGGER.debug("Forward to done requests");
    }
}
