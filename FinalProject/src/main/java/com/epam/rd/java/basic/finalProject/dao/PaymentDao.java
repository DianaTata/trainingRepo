package com.epam.rd.java.basic.finalProject.dao;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Payment;
import com.epam.rd.java.basic.finalProject.exception.PaymentException;

import java.sql.Connection;
import java.util.List;

public interface PaymentDao {

    boolean insertPayment(Payment payment) throws PaymentException;

    boolean makePayment(Payment payment) throws PaymentException;

    Payment findPaymentById(int id) throws PaymentException;

    List<Payment> getUserPayments(int userId, PaginationDTO paginationDTO) throws PaymentException;

    boolean updatePaymentStatus(int paymentId, String status, Connection connection) throws PaymentException;

    boolean deletePayment(int paymentId) throws PaymentException;

    int getUserPaymentsNumber(int userId) throws PaymentException;
}
