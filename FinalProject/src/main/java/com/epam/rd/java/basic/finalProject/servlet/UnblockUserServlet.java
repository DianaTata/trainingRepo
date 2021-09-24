package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.UserStatus;
import com.epam.rd.java.basic.finalProject.service.UserService;
import com.epam.rd.java.basic.finalProject.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/unblockUser")
public class UnblockUserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UnblockUserServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getSession().getAttribute("unblockResult"))) {
            boolean block = (boolean) req.getSession().getAttribute("unblockResult");
            req.getSession().removeAttribute("unblockResult");
            if (block) {
                req.setAttribute("message", "user.was.unblocked");
            } else {
                req.setAttribute("message", "user.wasnt.unblocked");
            }
            req.getRequestDispatcher("unblockUserResult.jsp").forward(req, resp);
            LOGGER.info("unblockUserServlet#doGet Finished");
        } else {
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        boolean result = userService.performBlockUser(Integer.parseInt(userId), UserStatus.UNLOCKED.getName());
        req.getSession().setAttribute("unblockResult", result);
        resp.sendRedirect(req.getContextPath() + "/unblockUser");
        LOGGER.info("Redirecting to " + req.getContextPath() + "/unblockUser");
        LOGGER.info("Unblock User#doPost Finished");

    }
}
