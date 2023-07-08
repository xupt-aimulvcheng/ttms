package com.xupt.ttms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class RabbitMQConfig {
    //一个延迟交换机一个延迟队列一个key
    public static final String DELAY_QUEUE_NAME = "delay.queue";
    public static final String DELAY_EXCHANGE_NAME = "delay.exchange";
    public static final String DELAY_QUEUE_ROUTING_KEY = "delay.queue.routing.key";
    @Bean("delayExchange")
    public CustomExchange delayExchange() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME,"x-delayed-message",true,false,args);
    }
    @Bean("delayQueue")
    public Queue delayQueue() {
        return new Queue(DELAY_QUEUE_NAME);
    }
    @Bean
    public Binding delayQueueABinding(@Qualifier("delayQueue") Queue delayQueue, @Qualifier("delayExchange") CustomExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_QUEUE_ROUTING_KEY).noargs();
    }
}
