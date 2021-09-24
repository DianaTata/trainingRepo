package com.epam.rd.java.basic.finalProject.servlet;

import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.service.RequestService;
import com.epam.rd.java.basic.finalProject.service.impl.RequestServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/unblockCount")
public class UnblockCountServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RequestServlet.class);

    private RequestService requestService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        requestService = (RequestService) getServletContext().getAttribute("requestService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getSession().getAttribute("unblockResult"))) {
            boolean block = (boolean) req.getSession().getAttribute("unblockResult");
            req.getSession().removeAttribute("unblockResult");
            if (block) {
                req.setAttribute("message", "count.was.unblocked");
            } else {
                req.setAttribute("message", "count.wasnt.unblocked");
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
        boolean result = requestService.performRequest(Integer.parseInt(countId),
                CountStatus.OPENED.getName(), RequestStatus.DONE.getName());
        req.getSession().setAttribute("unblockResult", result);
        resp.sendRedirect(req.getContextPath() + "/unblockCount");
        LOGGER.info("Redirecting to " + req.getContextPath() + "/unblockCount");
        LOGGER.info("Unblock Count#doPost Finished");
    }
}
