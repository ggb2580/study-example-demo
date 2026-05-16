package com.hrbu.rabbitmqdemo.controller;


import com.hrbu.rabbitmqdemo.config.RabbitMQConfig;
import com.hrbu.rabbitmqdemo.entity.OrderMsg;
import com.hrbu.rabbitmqdemo.ordertimeout.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Say my name
 */
@RestController
public class OrderController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String  getOrder(){
        String orderId = UUID.randomUUID().toString().replace("-","");
        System.out.println("主业务：订单创建成功，订单号："+orderId);

        OrderMsg orderMsg = new OrderMsg();
        orderMsg.setOrderId(orderId);
        orderMsg.setUserId(10000L);
        orderMsg.setPhone("13877623928");
        orderMsg.setGoodsId(888L);
        orderMsg.setNum(2);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_ORDER,
                RabbitMQConfig.ORDER_ROUTING_KEY_MSG,orderMsg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_ORDER,
                RabbitMQConfig.ORDER_ROUTING_KEY_LOG,orderMsg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_ORDER,
                RabbitMQConfig.ORDER_ROUTING_KEY_STOCK,orderMsg);

        return "订单ID:"+orderId;
    }

    @GetMapping("/orderTimeout")
    public String  orderTimeout(){
        String orderId = UUID.randomUUID().toString().replace("-","");

        OrderMsg orderMsg = new OrderMsg();
        orderMsg.setOrderId(orderId);
        orderMsg.setUserId(10000L);
        orderMsg.setPhone("13877623928");
        orderMsg.setGoodsId(888L);
        orderMsg.setNum(2);

        orderService.createOrder(orderMsg);

        return "订单ID:"+orderId;
    }
}
