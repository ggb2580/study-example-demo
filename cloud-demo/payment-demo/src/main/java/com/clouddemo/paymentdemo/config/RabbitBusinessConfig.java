package com.clouddemo.paymentdemo.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Say my name
 */
@Configuration
public class RabbitBusinessConfig {
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    //交换机
    public static final String ORDER_TOPIC_EXCHANGE = "order_topic_exchange";
    //队列
    public static final String ORDER_QUEUE_SMS = "order_queue_sms";
    public static final String ORDER_QUEUE_LOG = "order_queue_log";
    public static final String ORDER_QUEUE_STOCK = "order_queue_stock";

    //路由键
    public static final String ROUTING_KEY_ORDER_SMS = "order.sms";
    public static final String ROUTING_KEY_ORDER_LOG = "order.log";
    public static final String ROUTING_KEY_ORDER_STOCK = "order.stock";

    //声明交换机
    @Bean
    public TopicExchange orderTopicExchange(){
        return ExchangeBuilder.topicExchange(ORDER_TOPIC_EXCHANGE)
                // 持久化
                .durable(true)
                .build();
    }


    //声明三个队列
    @Bean
    public Queue smsQueue(){
        return QueueBuilder.durable(ORDER_QUEUE_SMS).build();
    }

    @Bean
    public Queue logQueue(){
        return QueueBuilder.durable(ORDER_QUEUE_LOG).build();
    }

    @Bean
    public Queue stockQueue(){
        return QueueBuilder.durable(ORDER_QUEUE_STOCK).build();
    }

    //绑定关系
    @Bean
    public Binding smsBinding(Queue smsQueue, TopicExchange orderTopicExchange){
        return BindingBuilder.bind(smsQueue).to(orderTopicExchange).with(ROUTING_KEY_ORDER_SMS);
    }

    @Bean
    public Binding logBinding(Queue logQueue,TopicExchange orderTopicExchange){
        return BindingBuilder.bind(logQueue).to(orderTopicExchange).with(ROUTING_KEY_ORDER_LOG);
    }

    @Bean
    public Binding stockBinding(Queue stockQueue,TopicExchange orderTopicExchange){
        return BindingBuilder.bind(stockQueue).to(orderTopicExchange).with(ROUTING_KEY_ORDER_STOCK);
    }



}
