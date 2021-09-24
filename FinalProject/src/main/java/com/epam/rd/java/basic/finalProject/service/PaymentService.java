package com.epam.rd.java.basic.finalProject.service;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.exception.PaymentException;

import java.math.BigDecimal;
import java.util.List;

/**
 * class to work with payment operations
 */
public interface PaymentService {

    /**
     * method to retrieve payment by payment id
     *
     * @param id - payment id
     * @return PaymentDTO
     */
    PaymentDTO getPaymentById(int id) throws PaymentException;

    /**
     * method make payment
     *
     * @param paymentDTO - payment
     * @return result operation make payment
     */
    boolean makePayment(PaymentDTO paymentDTO) throws PaymentException;

    /**
     * method to retrieve list payments by user id
     *
     * @param userId        - user id
     * @param paginationDTO - pagination
     * @return list payments
     */
    List<PaymentDTO> getUserPayments(int userId, PaginationDTO paginationDTO) throws PaymentException;

    /**
     * method to insert payment
     *
     * @param fromCount - from Count
     * @param toCount   - to Count
     * @param amount    - amount
     * @return result operation insert payment
     */
    boolean insertPayment(int fromCount, int toCount, BigDecimal amount) throws PaymentException;

    /**
     * method to decline payment
     *
     * @param paymentId - payment id
     * @return result operation decline payment
     */
    boolean declinePayment(int paymentId) throws PaymentException;

    /**
     * method to retrieve payments by count
     *
     * @param userId - user id
     * @return amount payments by count
     */
    int getUserPaymentsCount(int userId) throws PaymentException;
}
