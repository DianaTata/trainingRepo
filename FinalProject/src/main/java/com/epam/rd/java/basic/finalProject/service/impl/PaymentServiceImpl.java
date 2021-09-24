package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.PaymentDao;
import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.entity.Payment;
import com.epam.rd.java.basic.finalProject.exception.PaymentException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import com.epam.rd.java.basic.finalProject.service.CountService;
import com.epam.rd.java.basic.finalProject.service.PaymentService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentServiceImpl.class);
    private static final int UPPER_BOUND = 899999;
    private static final int LOWER_BOUND = 100000;

    private PaymentDao paymentDao;
    private CountService countService;
    private Mapper<PaymentDTO, Payment> paymentMapper;

    public PaymentServiceImpl(PaymentDao paymentDao, CountService countService, Mapper<PaymentDTO, Payment> paymentMapper) {
        this.paymentDao = paymentDao;
        this.countService = countService;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDTO getPaymentById(int id) throws PaymentException {
        Payment paymentById = paymentDao.findPaymentById(id);
        LOGGER.warn("started getPaymentById");
        if (Objects.nonNull(paymentById)) {
            return paymentMapper.mapToDTO(paymentById);
        }
        return null;
    }

    @Override
    public boolean makePayment(PaymentDTO paymentDTO) throws PaymentException {
        LOGGER.warn("started makePayment");
        return paymentDao.makePayment(paymentMapper.mapToEntity(paymentDTO));


    }

    @Override
    public List<PaymentDTO> getUserPayments(int userId, PaginationDTO paginationDTO) throws PaymentException {
        LOGGER.warn("started getUserPayments");
        List<Payment> userPayments = paymentDao.getUserPayments(userId, paginationDTO);
        return userPayments.stream()
                .map(paymentMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean insertPayment(int fromCountNumber, int toCountNumber, BigDecimal amount) throws PaymentException {
        LOGGER.error("started insert Payment");
        CountDTO fromCount = countService.getCountByNumber(fromCountNumber, CountStatus.OPENED.getName());
        CountDTO toCount = countService.getCountByNumber(toCountNumber, CountStatus.OPENED.getName());
        PaymentDTO paymentDTO = createPayment(fromCount, toCount, amount);
        return paymentDao.insertPayment(paymentMapper.mapToEntity(paymentDTO));

    }

    @Override
    public boolean declinePayment(int paymentId) throws PaymentException {
        LOGGER.warn("started decline payment");
        return paymentDao.deletePayment(paymentId);
    }

    @Override
    public int getUserPaymentsCount(int userId) throws PaymentException {
        LOGGER.warn("started get user payments");
        return paymentDao.getUserPaymentsNumber(userId);
    }

    private PaymentDTO createPayment(CountDTO fromCount, CountDTO toCount, BigDecimal amount) {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentNumber(new SecureRandom().nextInt(UPPER_BOUND) + LOWER_BOUND);
        payment.setPaymentDate(Date.valueOf(LocalDate.now()));
        payment.setAmount(amount);
        payment.setFromCount(fromCount);
        payment.setToCount(toCount);
        payment.setUser(fromCount.getUser());
        return payment;
    }
}
