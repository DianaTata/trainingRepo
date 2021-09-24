package com.epam.rd.java.basic.finalProject.filter;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Role;
import com.epam.rd.java.basic.finalProject.entity.UserStatus;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

public class AuthorizationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

    private static String[] adminServlets = null;
    private static String[] userServlets = null;
    private static String[] commonServlets = null;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getRequestURI();
        UserDTO user = null;
        String roleName = "";
        if (stringContains(path, commonServlets)) {
            chain.doFilter(request, response);
        } else {
            LOGGER.warn("AuthorizationFilter no commonServlets");

            try {
                user = (UserDTO) req.getSession().getAttribute("user");
                roleName = user.getRole().getName();
            } catch (NullPointerException e) {
                LOGGER.warn("Unauthorized try to access to resource " + path);
            }
            if (user != null) {
                if (stringContains(path, adminServlets) && roleName.equals(Role.ADMIN.getName())) {
                    chain.doFilter(request, response);
                } else if (stringContains(path, userServlets) && roleName.equals(Role.CLIENT.getName()) &&
                        UserStatus.UNLOCKED.getName().equals(user.getStatusName().getName())) {
                    LOGGER.warn("AuthorizationFilter chain not userServlets");
                    chain.doFilter(request, response);
                } else {
                    LOGGER.warn("User: " + user.getEmail() + ", tried to get resource " + path + ", without permissions.");
                    req.getRequestDispatcher("/account").forward(request, response);
                }
            } else {
                LOGGER.warn("AuthorizationFilter go to login");
                req.getRequestDispatcher("/login").forward(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        adminServlets = fConfig.getInitParameter("admin").split(" ");
        userServlets = fConfig.getInitParameter("user").split(" ");
        commonServlets = fConfig.getInitParameter("common").split(" ");
    }

    public static boolean stringContains(String requestedURI, String[] items) {
        return Arrays.stream(items).anyMatch(requestedURI::contains);
    }
}
