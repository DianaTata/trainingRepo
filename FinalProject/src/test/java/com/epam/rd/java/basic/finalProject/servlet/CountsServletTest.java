package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.CountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountsServletTest {

    @InjectMocks
    private CountsServlet countsServlet;

    @Mock
    private CountService countService;

    @Mock
    private PaginationMapper paginationMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Mock
    private CountDTO countDTO;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private Map<String, String> sortBy;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void shouldInit() {
        when(servletContext.getAttribute("sortCounts")).thenReturn(sortBy);
        when(servletContext.getAttribute("countService")).thenReturn(countService);
        when(servletContext.getAttribute("paginationMapper")).thenReturn(paginationMapper);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(req.getParameter("sortBy")).thenReturn("countNumber asc");
        when(req.getParameter("userId")).thenReturn("1");
        when(sortBy.getOrDefault("countNumber asc", "countNumber asc")).thenReturn("countNumber asc");
        when(paginationMapper.fillPaginationDTO(req)).thenReturn(paginationDTO);
        when(paginationDTO.getCurrentPage()).thenReturn(1);
        when(countService.getUserCountsNumber(1)).thenReturn(1);
        when(paginationDTO.getAmountOfItems()).thenReturn(1);
        when(countService.getUserCounts(1, paginationDTO)).thenReturn(Collections.singletonList(countDTO));
        when(req.getRequestDispatcher("userCounts.jsp")).thenReturn(requestDispatcher);

        countsServlet.doGet(req, resp);

        verify(paginationDTO).setSortBy("countNumber asc");
        verify(req).setAttribute("numberOfPages", 1);
        verify(req).setAttribute("countsNumber", 1);
        verify(req).setAttribute("paginationDTO", paginationDTO);
        verify(req).setAttribute("currPage", 1);
        verify(req).setAttribute("sort", "countNumber asc");
        verify(req).setAttribute("counts", Collections.singletonList(countDTO));
        verify(requestDispatcher).forward(req, resp);
    }

}