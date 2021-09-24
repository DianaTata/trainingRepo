package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/updateUser")
public class UpdateUserInfoServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateUserInfoServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        boolean result = userService.updateUserNameAndSurname(userId, name, surname);
        if (result){
            UserDTO userById = userService.getUserById(userId);
            req.getSession().setAttribute("user", userById);
            resp.sendRedirect(req.getContextPath() + "/myAccount");
            LOGGER.info("Redirecting to " + req.getContextPath() + "/myAccount");
            LOGGER.info("updateUser User#doPost Finished");
        }else {
            resp.sendRedirect(req.getContextPath() + "/error");
            LOGGER.info("updateUser User#doPost Finished");
        }
    }
}
