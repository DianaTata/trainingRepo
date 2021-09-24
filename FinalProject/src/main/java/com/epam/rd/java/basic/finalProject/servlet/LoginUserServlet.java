package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.service.UserService;
import com.epam.rd.java.basic.finalProject.service.impl.UserServiceImpl;
import com.epam.rd.java.basic.finalProject.util.PasswordUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginUserServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginUserServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = (String) req.getSession().getAttribute("errorMessage");
        req.getSession().removeAttribute("errorMessage");
        req.setAttribute("errorMessage", result);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
        LOGGER.debug("Forward login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = userService.getUserByEmail(request.getParameter("email"));

        if (user != null && PasswordUtils.verifyUserPassword(request.getParameter("password"),
                user.getPassword(), user.getEncrypt())) {
            if (user.getStatusName().getName().equals("unlocked")) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/account");
                LOGGER.info("Login to " + request.getContextPath() + "/account");
                LOGGER.info("Login#doPost Finished");
            } else {
                request.getSession().setAttribute("errorMessage", "account.locked");
                response.sendRedirect(request.getContextPath() + "/login");
            }
        } else {
            request.getSession().setAttribute("errorMessage", "wrong.email.pass");
            response.sendRedirect(request.getContextPath() + "/login");
            LOGGER.info("Error login");
        }
    }
}

