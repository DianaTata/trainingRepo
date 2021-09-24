package com.epam.rd.java.basic.finalProject.servlet;

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

@WebServlet("/sendRequest")
public class RequestServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RequestServlet.class);

    private RequestService requestService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        requestService = (RequestService) getServletContext().getAttribute("requestService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getSession().getAttribute("message"))) {
            String result = (String) req.getSession().getAttribute("message");
            req.getSession().removeAttribute("message");
            req.setAttribute("request", result);
            req.getRequestDispatcher("sendRequestResult.jsp").forward(req, resp);
            LOGGER.debug("Forward request");
        } else {
            req.getRequestDispatcher("account.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String countId = req.getParameter("countId");
        if (Objects.isNull(requestService.getRequestByCountIdAndStatus(Integer.parseInt(countId),
                RequestStatus.INPROGRESS.getName()))) {
            requestService.createRequest(Integer.parseInt(countId));

            req.getSession().setAttribute("message", "your.request.was.send");
        } else {
            req.getSession().setAttribute("message", "request.already.exists");
        }
        resp.sendRedirect(req.getContextPath() + "/sendRequest");
        LOGGER.debug("Forward requestServlet");
    }
}
