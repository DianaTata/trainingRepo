package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.entity.Role;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.RequestService;
import com.epam.rd.java.basic.finalProject.service.UserService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersServletTest {

    @InjectMocks
    private UsersServlet usersServlet;

    @Mock
    private UserService userService;

    @Mock
    private PaginationMapper paginationMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Mock
    private UserDTO userDTO;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Before
    public void shouldInit() {
        when(servletContext.getAttribute("userService")).thenReturn(userService);
        when(servletContext.getAttribute("paginationMapper")).thenReturn(paginationMapper);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        when(paginationMapper.fillPaginationDTO(req)).thenReturn(paginationDTO);
        when(paginationDTO.getCurrentPage()).thenReturn(1);
        when(userService.getUsersCount(Role.CLIENT.getName())).thenReturn(1);
        when(paginationDTO.getAmountOfItems()).thenReturn(1);
        when(userService.getUsers(Role.CLIENT.getName(), paginationDTO)).thenReturn(Collections.singletonList(userDTO));
        when(req.getRequestDispatcher("users.jsp")).thenReturn(requestDispatcher);

        usersServlet.doGet(req, resp);

        verify(req).setAttribute("numberOfPages", 1);
        verify(req).setAttribute("cardsNumber", 1);
        verify(req).setAttribute("paginationDTO", paginationDTO);
        verify(req).setAttribute("currPage", 1);
        verify(req).setAttribute("users", Collections.singletonList(userDTO));
        verify(requestDispatcher).forward(req,resp);
    }
}