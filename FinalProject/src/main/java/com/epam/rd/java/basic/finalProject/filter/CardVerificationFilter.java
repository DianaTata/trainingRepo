package com.epam.rd.java.basic.finalProject.filter;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Role;
import com.epam.rd.java.basic.finalProject.service.CardService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

public class CardVerificationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CardVerificationFilter.class);

    CardService cardService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        cardService = (CardService) servletContext.getAttribute("cardService");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.warn("started CardVerificationFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        String roleName = "";
        UserDTO userDTO;
        int cardId;
        if (Objects.nonNull(req.getSession().getAttribute("user"))) {
            if (Objects.nonNull(req.getParameter("cardId"))) {
                userDTO = (UserDTO) req.getSession().getAttribute("user");
                cardId = Integer.parseInt(req.getParameter("cardId"));
                CardDTO cardDTO = cardService.getCardById(cardId);
                roleName = userDTO.getRole().getName();
                if ((cardDTO.getUserDTO().getId() == userDTO.getId() && !roleName.equals(Role.ADMIN.getName()))) {
                    chain.doFilter(request, response);
                } else {
                    LOGGER.warn("User: " + userDTO.getEmail() + ", tried to get resource " + path + ", without permissions.");
                    req.getRequestDispatcher("/account").forward(request, response);
                }
            } else {
                LOGGER.warn("CardVerificationFilter go to account");
                req.getRequestDispatcher("/account").forward(request, response);
            }
        } else {
            LOGGER.warn("CardVerificationFilter go to login");
            req.getRequestDispatcher("/login").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
