package com.epam.rd.java.basic.finalProject.filter;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

public class UserVerificationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(UserVerificationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.warn("started UserVerificationFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        String roleName = "";
        UserDTO userDTO;
        int id;
        if (Objects.nonNull(req.getSession().getAttribute("user"))) {
            if (Objects.nonNull(req.getParameter("userId"))) {
                userDTO = (UserDTO) req.getSession().getAttribute("user");
                id = Integer.parseInt(req.getParameter("userId"));
                roleName = userDTO.getRole().getName();
                if ((userDTO.getId() == id && !roleName.equals(Role.ADMIN.getName())) || (userDTO.getId() != id && roleName.equals(Role.ADMIN.getName()))) {
                    chain.doFilter(request, response);
                } else {
                    LOGGER.warn("User: " + userDTO.getEmail() + ", tried to get resource " + path + ", without permissions.");
                    req.getRequestDispatcher("/account").forward(request, response);
                }
            } else {
                LOGGER.warn("UserVerificationFilter go to account ");
                req.getRequestDispatcher("/account").forward(request, response);
            }
        } else {
            LOGGER.warn("UserVerificationFilter go to login ");
            req.getRequestDispatcher("/login").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
