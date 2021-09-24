package com.epam.rd.java.basic.finalProject.service;

import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.exception.SendEmailException;

/**
 * class to work with send email
 */
public interface SendEmailService {

    /**
     * method to retrieve result send email
     *
     * @param paymentDTO - payment
     * @return result operation sendEmailWithPdf
     */
    boolean sendEmailWithPdf(PaymentDTO paymentDTO) throws SendEmailException;

}
