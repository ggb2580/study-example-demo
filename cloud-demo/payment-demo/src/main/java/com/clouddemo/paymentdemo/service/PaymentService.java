package com.clouddemo.paymentdemo.service;

import com.clouddemo.paymentdemo.entity.Payment;

/**
 * @author Say my name
 */
public interface PaymentService {
    int addPayment(Payment payment);
    Payment getPaymentById(int id);
}
