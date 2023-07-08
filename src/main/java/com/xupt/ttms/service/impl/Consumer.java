package com.xupt.ttms.service.impl;/*
 * @author ck
 * @date 2022/12/30
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbitmq.client.Channel;
import com.xupt.ttms.mapper.OrderMapper;
import com.xupt.ttms.pojo.Order;
import com.xupt.ttms.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xupt.ttms.config.RabbitMQConfig.DELAY_QUEUE_NAME;

@Component
@Slf4j
public class Consumer {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderService orderService;
    @RabbitListener(queues = DELAY_QUEUE_NAME)
    public void receive(Message message, Channel channel) {
        String orderNo = new String(message.getBody());
        log.info("接收到的订单号为：{}", orderNo);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo,orderNo);
        Order order = orderMapper.selectOne(queryWrapper);
        log.info("订单信息为：{}", order);
        if(order!= null && order.getoStatus() == 0) {
            orderService.updateCloseOverTimeOrder(order.getId());
        }
    }
}
