package com.clouddemo.paymentdemo.service.impl;

import com.clouddemo.paymentdemo.entity.Payment;
import com.clouddemo.paymentdemo.mapper.PaymentMapper;
import com.clouddemo.paymentdemo.service.PaymentService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentMapper paymentMapper;
    @Override
    public int addPayment(Payment payment) {
        return paymentMapper.addPayment(payment);
    }

    @Override
    public Payment getPaymentById(int id) {
        return paymentMapper.getPaymentById(id);
    }
}
