package com.xupt.ttms.service.impl;/*
 * @author ck
 * @date 2022/12/30
 * @apiNote
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xupt.ttms.config.RabbitMQConfig.DELAY_EXCHANGE_NAME;
import static com.xupt.ttms.config.RabbitMQConfig.DELAY_QUEUE_ROUTING_KEY;


@Component
@Slf4j
public class Producer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void send(String message,Integer delayTime){
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME,DELAY_QUEUE_ROUTING_KEY,message, msg -> {
                    msg.getMessageProperties().setDelay(delayTime);
                    log.info("发送的消息为：{},延迟时间为：{}ms",message,delayTime);
                    return msg;
                }
        );
    }
}
