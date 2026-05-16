package com.hrbu.rabbitmqdemo.channel;


import com.hrbu.rabbitmqdemo.config.RabbitMQConfig;
import com.hrbu.rabbitmqdemo.entity.OrderMsg;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class SmsConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE_MSG)
    public void consume(OrderMsg msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            // 模拟真实发短信
            System.out.println("【短信服务】给手机号 " + msg.getPhone() + " 发送下单成功通知，订单：" + msg.getOrderId());
            // 手动确认消费成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 异常：拒绝消息，可重回队列或死信
            channel.basicNack(tag, false, true);
        }
    }
}