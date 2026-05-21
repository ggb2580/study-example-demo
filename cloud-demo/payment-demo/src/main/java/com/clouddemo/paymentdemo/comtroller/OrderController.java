package com.clouddemo.paymentdemo.comtroller;

import com.clouddemo.paymentdemo.config.RabbitBusinessConfig;
import com.clouddemo.paymentdemo.entity.OrderMsg;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Say my name
 */
@RestController
public class OrderController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/createOrder")
    public String createOrder(){
        String orderId = UUID.randomUUID().toString().replace("-","");
        System.out.println("主业务：订单创建成功，订单号："+orderId);

        OrderMsg orderMsg = new OrderMsg();
        orderMsg.setOrderId(orderId);
        orderMsg.setUserId(10000L);
        orderMsg.setPhone("13877623928");
        orderMsg.setGoodsId(888L);
        orderMsg.setNum(2);

        rabbitTemplate.convertAndSend(RabbitBusinessConfig.ORDER_TOPIC_EXCHANGE,
                RabbitBusinessConfig.ROUTING_KEY_ORDER_SMS,orderMsg);

        rabbitTemplate.convertAndSend(RabbitBusinessConfig.ORDER_TOPIC_EXCHANGE,
                RabbitBusinessConfig.ORDER_QUEUE_LOG,orderMsg);

        rabbitTemplate.convertAndSend(RabbitBusinessConfig.ORDER_TOPIC_EXCHANGE,
                RabbitBusinessConfig.ORDER_QUEUE_STOCK,orderMsg);

        return "返回订单："+orderId;
    }
}
