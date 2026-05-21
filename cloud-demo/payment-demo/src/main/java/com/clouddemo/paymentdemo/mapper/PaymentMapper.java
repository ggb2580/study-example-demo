package com.clouddemo.paymentdemo.mapper;

import com.clouddemo.paymentdemo.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Say my name
 */
@Mapper
public interface PaymentMapper {
    int addPayment(Payment payment);
    Payment getPaymentById(int id);
}
