package com.hrbu.rabbitmqdemo.config;

import org.springframework.amqp.core.*;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Say my name
 */
@Configuration
public class RabbitMQConfig {
    //交换机
    public static final String EXCHANGE_ORDER = "exchange_order";

    //队列
    public static final String ORDER_QUEUE_MSG = "queue_msg";
    public static final String ORDER_QUEUE_LOG = "queue_log";
    public static final String ORDER_QUEUE_STOCK = "queue_stock";

    //路由键
    public static final String ORDER_ROUTING_KEY_MSG = "order_msg";
    public static final String ORDER_ROUTING_KEY_LOG = "order_log";
    public static final String ORDER_ROUTING_KEY_STOCK = "order_stock";

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public TopicExchange topicExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_ORDER)
                .durable(true)
                .build();
    }

    @Bean
    public Queue msgQueue(){
        return QueueBuilder.durable(ORDER_QUEUE_MSG)
                .build();
    }

    @Bean
    public Queue logQueue(){
        return QueueBuilder.durable(ORDER_QUEUE_LOG)
                .build();
    }

    @Bean
    public Queue stockQueue(){
        return QueueBuilder.durable(ORDER_QUEUE_STOCK)
                .build();
    }

    @Bean
    public Binding msgBinding(TopicExchange topicExchange,Queue msgQueue){
        return BindingBuilder.bind(msgQueue).to(topicExchange).with(ORDER_ROUTING_KEY_MSG);
    }

    @Bean
    public Binding logBinding(TopicExchange topicExchange,Queue logQueue){
        return BindingBuilder.bind(logQueue).to(topicExchange).with(ORDER_ROUTING_KEY_LOG);
    }

    @Bean
    public Binding stockBinding(TopicExchange topicExchange,Queue stockQueue){
        return BindingBuilder.bind(stockQueue).to(topicExchange).with(ORDER_ROUTING_KEY_STOCK);
    }

}
