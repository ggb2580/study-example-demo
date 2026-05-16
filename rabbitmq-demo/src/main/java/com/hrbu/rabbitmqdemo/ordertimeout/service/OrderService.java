package com.hrbu.rabbitmqdemo.ordertimeout.service;

import com.hrbu.rabbitmqdemo.ordertimeout.OrderTimeoutRabbitConfig;
import com.hrbu.rabbitmqdemo.entity.OrderMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Say my name
 */
@Service
@Slf4j
public class OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public void createOrder(OrderMsg orderMsg){
        //保存订单到数据库 状态为待支付

        //扣减库存

        //发送延迟消息（30分钟后检查并取消账单）
        //消息体：订单ID
        String orderId = orderMsg.getOrderId();
        rabbitTemplate.convertAndSend(
                OrderTimeoutRabbitConfig.NORMAL_EXCHANGE,
                OrderTimeoutRabbitConfig.NORMAL_ROUTING_KEY,
                orderId,
                message -> {
                    //设置消息过期时间为30*60*1000 毫秒 = 30 分钟
                    message.getMessageProperties().setExpiration(String.valueOf(60 * 1000));
                    return message;
                }
        );
        log.info("订单[{}]已创建,将在3分钟后自动检查超时",orderId);

    }
}
