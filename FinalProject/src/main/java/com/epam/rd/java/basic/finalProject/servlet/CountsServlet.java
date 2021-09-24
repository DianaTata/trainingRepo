package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.CountService;
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

@WebServlet("/counts")
public class CountsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CountsServlet.class);
    public static final String DEFAULT_SORT = "countNumber asc";

    private CountService countService;
    private PaginationMapper paginationMapper;
    private Map<String, String> sortMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sortMap = (HashMap) getServletContext().getAttribute("sortCounts");
        countService = (CountService) getServletContext().getAttribute("countService");
        paginationMapper = (PaginationMapper) getServletContext().getAttribute("paginationMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sort = req.getParameter("sortBy");
        int userId = Integer.parseInt(req.getParameter("userId"));
        String countSort = sortMap.getOrDefault(sort, DEFAULT_SORT);
        PaginationDTO paginationDTO = paginationMapper.fillPaginationDTO(req);
        paginationDTO.setSortBy(countSort);
        int currPage = paginationDTO.getCurrentPage();
        int countsNumber = countService.getUserCountsNumber(userId);
        int numberOfPages = 1;
        if (countsNumber > 0) {
            numberOfPages = (int) Math.ceil(countsNumber * 1.0 / paginationDTO.getAmountOfItems());
        }
        List<CountDTO> countList = countService.getUserCounts(userId, paginationDTO);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("countsNumber", countsNumber);
        req.setAttribute("paginationDTO", paginationDTO);
        req.setAttribute("currPage", currPage);
        req.setAttribute("sort", sort);
        req.setAttribute("counts", countList);
        req.getRequestDispatcher("userCounts.jsp").forward(req, resp);
        LOGGER.debug("Forward to user counts");
    }

}
