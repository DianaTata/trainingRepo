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

@WebServlet("/blockUser")
public class BlockUserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BlockUserServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getSession().getAttribute("blockResult"))) {
            boolean block = (boolean) req.getSession().getAttribute("blockResult");
            req.getSession().removeAttribute("blockResult");
            if (block) {
                req.setAttribute("message", "block.user.message.success");
            } else {
                req.setAttribute("message", "block.user.message.fail");
            }
            req.getRequestDispatcher("blockUserResult.jsp").forward(req, resp);
            LOGGER.info("blockUserServlet#doGet Finished");
        } else {
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        boolean result = userService.performBlockUser(Integer.parseInt(userId), UserStatus.LOCKED.getName());
        req.getSession().setAttribute("blockResult", result);
        resp.sendRedirect(req.getContextPath() + "/blockUser");
        LOGGER.info("Redirecting to " + req.getContextPath() + "/blockUser");
        LOGGER.info("Block User#doPost Finished");

    }
}
