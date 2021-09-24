package com.epam.rd.java.basic.finalProject.listener;

import com.epam.rd.java.basic.finalProject.dao.*;
import com.epam.rd.java.basic.finalProject.dao.impl.*;
import com.epam.rd.java.basic.finalProject.dto.*;
import com.epam.rd.java.basic.finalProject.entity.*;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import com.epam.rd.java.basic.finalProject.mapper.impl.*;
import com.epam.rd.java.basic.finalProject.service.*;
import com.epam.rd.java.basic.finalProject.service.impl.*;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String realPath = ctx.getRealPath("/WEB-INF/log/log4j.properties");
        PropertyConfigurator.configure(realPath);
        initServices(ctx);
        initSortMaps(ctx);
    }

    private void initServices(ServletContext ctx) {
        UserDao userDao = new UserDaoImpl();
        CardDao cardDao = new CardDaoImpl(userDao);
        CountDao countDao = new CountDaoImpl(cardDao, userDao);
        PaymentDao paymentDao = new PaymentDaoImpl(userDao, countDao);
        RequestDao requestDao = new RequestDaoImpl(userDao, countDao);
        Mapper<UserDTO, User> userMapper = new UserMapper();
        Mapper<CardDTO, Card> cardMapper = new CardMapper(userMapper);
        Mapper<CountDTO, Count> countMapper = new CountMapper(cardMapper, userMapper);
        Mapper<PaymentDTO, Payment> paymentMapper = new PaymentMapper(userMapper, countMapper);
        Mapper<RequestDTO, Request> requestMapper = new RequestMapper(userMapper, countMapper);
        PaginationMapper paginationMapper = new PaginationMapper();
        UserService userService = new UserServiceImpl(userDao, userMapper);
        CardService cardService = new CardServiceImpl(cardDao, countDao, cardMapper);
        CountService countService = new CountServiceImpl(countDao, countMapper, cardMapper);
        PaymentService paymentService = new PaymentServiceImpl(paymentDao, countService, paymentMapper);
        RequestService requestService = new RequestServiceImpl(requestDao, requestMapper, countDao);
        SendEmailServiceImpl sendEmailService = new SendEmailServiceImpl();
        ctx.setAttribute("userService", userService);
        ctx.setAttribute("cardService", cardService);
        ctx.setAttribute("countService", countService);
        ctx.setAttribute("paymentService", paymentService);
        ctx.setAttribute("requestService", requestService);
        ctx.setAttribute("sendEmailService", sendEmailService);
        ctx.setAttribute("paginationMapper", paginationMapper);
    }

    private void initSortMaps(ServletContext ctx) {
        Map<String, String> sortPayments = new HashMap<>();
        sortPayments.put("sortByNumberDesc", "paymentNumber desc");
        sortPayments.put("sortByNumberAsc", "paymentNumber asc");
        sortPayments.put("sortByDateDesc", "paymentDate desc");
        sortPayments.put("sortByDateAsc", "paymentDate asc");
        Map<String, String> sortCounts = new HashMap<>();
        sortCounts.put("sortByNumberAsc", "countNumber asc");
        sortCounts.put("sortByNumberDesc", "countNumber desc");
        sortCounts.put("sortByNameDesc", "countName desc");
        sortCounts.put("sortByNameAsc", "countName asc");
        sortCounts.put("sortByAmountDesc", "amount desc");
        sortCounts.put("sortByAmountAsc", "amount asc");
        Map<String, String> sortCards = new HashMap<>();
        sortCards.put("sortByNumberAsc", "cardNumber asc");
        sortCards.put("sortByNumberDesc", "cardNumber desc");
        sortCards.put("sortByAmountDesc", "amount desc");
        sortCards.put("sortByAmountAsc", "amount asc");
        ctx.setAttribute("sortPayments", sortPayments);
        ctx.setAttribute("sortCounts", sortCounts);
        ctx.setAttribute("sortCards", sortCards);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
