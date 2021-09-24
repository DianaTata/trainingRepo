package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.exception.UserException;
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
import java.security.SecureRandom;
import java.util.Objects;

@WebServlet("/register")
public class RegistrationUserServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegistrationUserServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getSession().getAttribute("errorMessage"))) {
            String errorMessage = (String) req.getSession().getAttribute("errorMessage");
            req.getSession().removeAttribute("errorMessage");
            req.setAttribute("errorMessage", errorMessage);
        }
        String random = String.valueOf(new SecureRandom().nextInt(8999) + 1000);
        req.setAttribute("random", random);
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userDto = fillUserDTO(request);
        String captcha = request.getParameter("captcha");
        String random = request.getParameter("random");
        try {
            if (captcha.equals(random)) {
                UserDTO result = userService.insertUser(userDto);
                UserDTO actual = userService.getUserById(result.getId());
                if (Objects.nonNull(actual)) {
                    request.getSession().setAttribute("user", actual);
                    response.sendRedirect(request.getContextPath() + "/account");
                    LOGGER.info("Redirecting to " + request.getContextPath() + "/account");
                    LOGGER.info("Registration#doPost Finished");
                } else {
                    request.getSession().setAttribute("errorMessage", "not.find.user");
                    response.sendRedirect(request.getContextPath() + "/register");
                    LOGGER.info("Redirecting to " + request.getContextPath() + "/register");
                    LOGGER.info("Registration#doPost Finished");
                }
            } else {
                request.getSession().setAttribute("errorMessage", "wrong.captcha");
                response.sendRedirect(request.getContextPath() + "/register");
                LOGGER.info("Redirecting to " + request.getContextPath() + "/register");
                LOGGER.info("Registration#doPost Finished");
            }
        } catch (UserException exception) {
            request.getSession().setAttribute("errorMessage", "email.used ");
            response.sendRedirect(request.getContextPath() + "/register");
            LOGGER.info("Redirecting to " + request.getContextPath() + "/register");
            LOGGER.info("Registration#doPost Finished");
        }
    }

    private UserDTO fillUserDTO(HttpServletRequest request) {
        UserDTO userDto = new UserDTO();
        String encrypt = PasswordUtils.getEncryptedString(30);
        String securePassword = PasswordUtils.generateSecurePassword(request.getParameter("password"), encrypt);
        userDto.setName(request.getParameter("name"));
        userDto.setSurname(request.getParameter("surname"));
        userDto.setPassword(securePassword);
        userDto.setEncrypt(encrypt);
        userDto.setEmail(request.getParameter("email"));
        return userDto;
    }
}
