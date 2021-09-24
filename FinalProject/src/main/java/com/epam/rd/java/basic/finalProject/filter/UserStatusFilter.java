package com.epam.rd.java.basic.finalProject.filter;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.UserStatus;
import com.epam.rd.java.basic.finalProject.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

public class UserStatusFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(UserStatusFilter.class);
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        UserDTO userDTO;
        if (Objects.nonNull(req.getSession().getAttribute("user"))) {
            userDTO = (UserDTO) req.getSession().getAttribute("user");
            userDTO = userService.getUserById(userDTO.getId());
            if (UserStatus.LOCKED.getName().equals(userDTO.getStatusName().getName())) {
                req.getSession().removeAttribute("user");
                req.getRequestDispatcher("/login").forward(request, response);
            } else {
                LOGGER.warn("UserStatusFilter chain UNLOCKED");
                chain.doFilter(request, response);
            }
        } else if (path.contains("login") || path.contains("register")) {
            LOGGER.warn("UserStatusFilter chain NULL");
            chain.doFilter(request, response);
        } else {
            LOGGER.warn("UserStatusFilter go to login");
            req.getRequestDispatcher("/login").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
