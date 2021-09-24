package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Role;
import com.epam.rd.java.basic.finalProject.mapper.impl.PaginationMapper;
import com.epam.rd.java.basic.finalProject.service.UserService;
import com.epam.rd.java.basic.finalProject.service.impl.UserServiceImpl;
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

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UsersServlet.class);

    private UserService userService;
    private PaginationMapper paginationMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
        paginationMapper = (PaginationMapper) getServletContext().getAttribute("paginationMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaginationDTO paginationDTO = paginationMapper.fillPaginationDTO(req);
        int currPage = paginationDTO.getCurrentPage();
        int cardsNumber = userService.getUsersCount(Role.CLIENT.getName());
        int numberOfPages = 1;
        if (cardsNumber > 0) {
            numberOfPages = (int) Math.ceil(cardsNumber * 1.0 / paginationDTO.getAmountOfItems());
        }
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("cardsNumber", cardsNumber);
        req.setAttribute("paginationDTO", paginationDTO);
        req.setAttribute("currPage", currPage);
        List<UserDTO> userList = userService.getUsers(Role.CLIENT.getName(), paginationDTO);
        req.setAttribute("users", userList);
        req.getRequestDispatcher("users.jsp").forward(req, resp);
        LOGGER.debug("Forward to users");
    }
}
