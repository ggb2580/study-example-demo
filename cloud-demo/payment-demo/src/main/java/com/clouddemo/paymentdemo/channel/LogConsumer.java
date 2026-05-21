package com.clouddemo.paymentdemo.channel;


import com.clouddemo.paymentdemo.config.RabbitBusinessConfig;
import com.clouddemo.paymentdemo.entity.OrderMsg;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author Say my name
 */
@Component
public class LogConsumer {
    @RabbitListener(queues = RabbitBusinessConfig.ORDER_QUEUE_LOG)
    public void consume(OrderMsg msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            System.out.println("【日志服务】记录用户下单日志：用户"+msg.getUserId()+" 订单"+msg.getOrderId());
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag,false,true);
        }
    }
}
