package com.hrbu.rabbitmqdemo.channel;

import com.hrbu.rabbitmqdemo.config.RabbitMQConfig;
import com.hrbu.rabbitmqdemo.entity.OrderMsg;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class StockConsumer {
    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE_STOCK)
    public void consume(OrderMsg msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            System.out.println("【库存服务】扣减商品"+msg.getGoodsId()+" 库存，数量："+msg.getNum());
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag,false,true);
        }
    }
}