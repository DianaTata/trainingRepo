package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Payment;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;

public class PaymentMapper implements Mapper<PaymentDTO, Payment> {
    private Mapper<UserDTO, User> userMapper;
    private Mapper<CountDTO, Count> countMapper;

    public PaymentMapper(Mapper<UserDTO, User> userMapper, Mapper<CountDTO, Count> countMapper) {
        this.userMapper = userMapper;
        this.countMapper = countMapper;
    }

    @Override
    public Payment mapToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setPaymentNumber(paymentDTO.getPaymentNumber());
        payment.setUser(userMapper.mapToEntity(paymentDTO.getUser()));
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setFromCount(countMapper.mapToEntity(paymentDTO.getFromCount()));
        payment.setToCount(countMapper.mapToEntity(paymentDTO.getToCount()));
        payment.setStatusName(paymentDTO.getStatusName());
        return payment;
    }

    @Override
    public PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setPaymentNumber(payment.getPaymentNumber());
        paymentDTO.setUser(userMapper.mapToDTO(payment.getUser()));
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setFromCount(countMapper.mapToDTO(payment.getFromCount()));
        paymentDTO.setToCount(countMapper.mapToDTO(payment.getToCount()));
        paymentDTO.setStatusName(payment.getStatusName());
        return paymentDTO;
    }
}
