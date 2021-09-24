package com.epam.rd.java.basic.finalProject.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/myAccount")
public class MyAccountServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MyAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("myAccount.jsp").forward(request, response);
        LOGGER.info("go to myAccount.jsp");
    }
}
