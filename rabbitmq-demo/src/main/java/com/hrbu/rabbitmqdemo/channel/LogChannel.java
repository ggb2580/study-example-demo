package com.hrbu.rabbitmqdemo.channel;

import com.hrbu.rabbitmqdemo.config.RabbitMQConfig;
import com.hrbu.rabbitmqdemo.entity.OrderMsg;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Say my name
 */
@Component
public class LogChannel {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE_LOG)
    public void consume(OrderMsg orderMsg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("【日志记录】:订单号 "+orderMsg.getOrderId()+" 用户ID:"+orderMsg.getUserId());
        try {
            channel.basicAck(tag,false);
        } catch (IOException e) {
            channel.basicNack(tag,false,true);
        }

    }}
