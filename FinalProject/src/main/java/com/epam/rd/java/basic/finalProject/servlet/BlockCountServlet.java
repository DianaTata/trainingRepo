package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.service.CardService;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.impl.CountServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/blockCount")
public class BlockCountServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BlockCountServlet.class);

    private CountService countService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        countService = (CountService) getServletContext().getAttribute("countService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getSession().getAttribute("blockResult"))) {
            boolean block = (boolean) req.getSession().getAttribute("blockResult");
            req.getSession().removeAttribute("blockResult");
            if (block) {
                req.setAttribute("message", "block.count.message.success");
            } else {
                req.setAttribute("message", "block.count.message.fail");
            }
            req.getRequestDispatcher("blockCountResult.jsp").forward(req, resp);
            LOGGER.info("blockCountServlet#doGet Finished");
        } else {
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String countId = req.getParameter("countId");
        boolean result = countService.performBlockCount(Integer.parseInt(countId), CountStatus.BLOCKED.getName());
        req.getSession().setAttribute("blockResult", result);
        resp.sendRedirect(req.getContextPath() + "/blockCount");
        LOGGER.info("Redirecting to " + req.getContextPath() + "/blockCount");
        LOGGER.info("Block Count#doPost Finished");
    }
}