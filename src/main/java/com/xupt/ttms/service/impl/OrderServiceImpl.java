package com.xupt.ttms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.ttms.config.exception.CkException;
import com.xupt.ttms.mapper.OrderMapper;
import com.xupt.ttms.mapper.SeatMapper;
import com.xupt.ttms.mapper.TicketMapper;
import com.xupt.ttms.pojo.Order;
import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.pojo.Ticket;
import com.xupt.ttms.service.OrderService;
import com.xupt.ttms.util.AmountUtils;
import com.xupt.ttms.util.BeanMapUtils;
import com.xupt.ttms.util.StringUtils;
import com.xupt.ttms.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.xupt.ttms.config.enums.RedisKeyEnum.ORDER;

/**
* @author 
* @description 针对表【t_order(order)】的数据库操作Service实现
* @createDate 2023-05-13 17:38:23
*/
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{
    @Autowired
    private SeatMapper seatMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Producer producer;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Order getNoBoughtOrderByOrderNo(String orderNo) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo,orderNo).eq(Order::getoStatus,0);
        Order order = baseMapper.selectOne(queryWrapper);
        if (order == null){
            throw  new CkException(20001,"订单不存在");
        }
        return order;
    }

    @Override
    public Order selectByPrimaryKey(Long id) {
        Order order = getById(id);
        return getOrder(id, order);
    }


    @Override
    public int deleteOrder(long id) {
        Order order = orderMapper.selectById(id);
        if (order.getoStatus() == -1){
            stringRedisTemplate.delete(ORDER+order.getOrderNo());
        }
        //根据订单id删除订单
        return orderMapper.deleteById(id);
    }

    @Override
    public PageInfo<Order> getOrders(int pageNum, int pageSize ) {
        Page<Order> orders = PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.searchAllByUserId(ThreadUtils.getUserId());
        //List<Order> list = orderList.stream().distinct().toList();
        for (Order order : orderList) {
            if (order.getoStatus() == -1){
                //根据orderNo获取redis中的订单信息
                log.info("超时的订单号为：{}",order.getOrderNo());
                Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("order:" + order.getOrderNo());
                log.info("redis中的订单信息为：{}",entries);
                Order order1 = BeanUtil.mapToBean(entries, Order.class, true);
                order1.setSeats(getSeats((String) entries.get("seats")));
                log.info("从redis中获取的订单信息为：{}",order1);
                //    将order1的所有值赋值给order
                BeanUtil.copyProperties(order1,order);
                log.info("将redis中的订单信息赋值给order后的order为：{}",order);
            }
            else {
                order.setSeats(seatMapper.selectSeatByOrderId(order.getId()));
            }
        }
        //List<Order> order = getOrder(orderList, 0);
        return new PageInfo<>(orderList);
    }

    @Override
    public Order insertOrder(BigDecimal amount, List<Seat> list, String pId) {
        Order order = new Order();
        try {
            Long userId = ThreadUtils.getUserId();
            order.setAmount(amount);
            order.setUserId(userId);
            order.setOrderNo(StringUtils.getOrderNo());
            //获取当前时间
            Date date = new Date();
            order.setGenerateTime(date);
            //获取当前时间的30min后的时间
            Date expireTime = new Date(date.getTime() + 30 * 60 * 1000);
            order.setExpireTime(expireTime);
            //发送过期时间为30min的携带订单号的消息
            producer.send(order.getOrderNo(), 30*60*1000);
            //插入数据
            orderMapper.insert(order);
            ticketMapper.updateOrderId(list, pId, order.getId());
            //根据订单id查询电影id
            Integer mId = orderMapper.getmId(order.getId());
            UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",order.getId());
            wrapper.set("m_id",mId);
            orderMapper.update(null,wrapper);
        } catch (Exception e) {
            throw new CkException(20001,"订单生成失败");
        }
        return order;
    }

    @Override
    public Integer payOrder(Order order) {
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_no",order.getOrderNo())
                .set("status",1)
                .set("purchase_time",new Date());
        int result = orderMapper.update(null, wrapper);
        Long userId = ThreadUtils.getUserId();
        Integer MId = orderMapper.selectById(order.getId()).getmId(); //查询订单对应的电影id
        stringRedisTemplate.opsForSet().add(MId+"bought",userId.toString());
        return result;
    }

    @Override
    public int refund(Long id) {
        return 0;
    }

    @Override
    public void updateCloseOverTimeOrder(Long id) {
        //将订单信息存入redis
        putOrderInRedis(id);
        //将订单状态改为2
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id).set("o_status",2);
        orderMapper.update(null,wrapper);
        //将座位状态改为1,并将订单id置为null
        UpdateWrapper<Ticket> ticketUpdateWrapper = new UpdateWrapper<>();
        ticketUpdateWrapper.eq("order_id",id).set("ticket_status",1).set("order_id",null);
        ticketMapper.update(null,ticketUpdateWrapper);
    }

    @Override
    public Order selectById(Long id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        return getOrder(id, order);
    }

    @Override
    public Map<String, Object> getOrderAmount() {
        List<BigDecimal> orderAmount = orderMapper.getOrderAmount();
        return AmountUtils.calculateTotalAmount(orderAmount,1);
    }

    @NotNull
    private Order getOrder(Long id, Order order) {
        List<Seat> seats = seatMapper.selectSeatByOrderId(id);
        order.setSeats(seats);
        if (order.getoStatus() == -1){
            //根据orderNo获取redis中的订单信息
            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(ORDER + order.getOrderNo());
            log.info("redis中的订单信息为：{}",entries);
            Order order1 = BeanUtil.mapToBean(entries, Order.class, true);
            order1.setSeats(getSeats((String) entries.get("seats")));
            //    将order1的所有值赋值给order
            BeanUtil.copyProperties(order1,order);
            log.info("将redis中的订单信息赋值给order后的order为：{}",order);
        }
        return order;
    }

    //将redis获取的座位信息（String类型）转换为座位对象
    private List<Seat> getSeats(String seat) {
        log.info("redis中的座位信息为：{}",seat);
        //去掉str中的Seat
        String s = seat.replaceAll("Seat", "");
        //将=前的字符串加上双引号
        String s1 = s.replaceAll("id", "\"id\"");
        String s2 = s1.replaceAll("studioId", "\"studioId\"");
        String s3 = s2.replaceAll("row", "\"row\"");
        String s4 = s3.replaceAll("col", "\"col\"");
        String s5 = s4.replaceAll("status", "\"status\"");
        //将=换为:
        String s6 = s5.replaceAll("=", ":");
        //将s6转化为json数组
        List<Seat> seats = JSON.parseArray(s6, Seat.class);
        return seats;
    }
    //将订单信息存入redis
    private void putOrderInRedis(Long dataId) {
        //根据订单id查询订单信息
        Order order = orderMapper.getOrderDetail(dataId);
        List<Seat> seats = seatMapper.selectSeatByOrderId(Long.valueOf(dataId));
        order.setSeats(seats);//将座位信息存入订单中
        order.setoStatus(-1);//将订单状态改为-1
        Map<String, Object> map = BeanMapUtils.beanToMap(order);//将订单转换为map
        log.info("订单超时，订单信息为：{}", map);
        stringRedisTemplate.opsForHash().putAll("order:" + order.getOrderNo(), map);
    }
}
