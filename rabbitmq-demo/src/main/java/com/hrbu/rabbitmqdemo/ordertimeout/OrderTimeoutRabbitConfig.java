package com.hrbu.rabbitmqdemo.ordertimeout;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Say my name
 */
/*
* 订单超时自动取消
* */
@Configuration
public class OrderTimeoutRabbitConfig {
    //普通交换机（接受生产者发送的消息）
    public static final String NORMAL_EXCHANGE = "order.normal.exchange";
    //普通队列（无消费者，消息在此等待过期）
    public static final String NORMAL_QUEUE = "order.normal.queue";
    //普通路由键
    public static final String NORMAL_ROUTING_KEY = "order.create";

    //死信交换机（接收过期消息）
    public static final String DEAD_EXCHANGE = "order.exchange";
    //死信队列（消费者真正监听的队列）
    public static final String DEAD_QUEUE = "order.dead.queue";
    //死信路由键
    public static final String DEAD_ROUTING_KEY = "order.timeout";

    //声明普通交换机（持久化）
    @Bean
    public DirectExchange normalExchange(){
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE).durable(true).build();
    }

    //声明死信交换机
    @Bean
    public DirectExchange deadExchange(){
        return ExchangeBuilder.directExchange(DEAD_EXCHANGE).durable(true).build();
    }

    //声明普通队列，设置死信参数
    @Bean
    public Queue normalQueue(){
        return QueueBuilder.durable(NORMAL_QUEUE)
                .withArgument("x-dead-letter-exchange",DEAD_EXCHANGE)
                .withArgument("x-dead-letter-routing-key",DEAD_ROUTING_KEY)
                .build();
    }

    //声明死信队列
    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    //绑定普通队列到普通交换机
    @Bean
    public Binding normalBinding(){
        return BindingBuilder.bind(normalQueue())
                .to(normalExchange())
                .with(NORMAL_ROUTING_KEY);
    }

    //绑定死信队列到死信交换机
    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue())
                .to(deadExchange())
                .with(DEAD_ROUTING_KEY);
    }

}
