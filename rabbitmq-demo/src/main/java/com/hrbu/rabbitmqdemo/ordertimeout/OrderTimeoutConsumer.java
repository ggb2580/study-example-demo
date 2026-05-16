package com.hrbu.rabbitmqdemo.ordertimeout;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Say my name
 */
@Component
@Slf4j
public class OrderTimeoutConsumer {

    @RabbitListener(queues = OrderTimeoutRabbitConfig.DEAD_QUEUE)
    public void handleOrderTimeout(String orderId, Channel channel, Message message) throws IOException {
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        log.info("收到订单超时消息，订单号：{}",orderId);

        //查询订单当前状态

        //只有“待支付”状态才执行取消

        //恢复库存

        log.info("订单[{}]超时未支付，已自动取消并恢复库存",orderId);

        log.info("订单[{}]状态为{},无需取消",orderId,"已支付");

        try {
            channel.basicAck(deliverTag,false);
        } catch (IOException e) {
            channel.basicNack(deliverTag,false,true);
        }
    }
}
