package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xupt.ttms.pojo.Order;
import com.xupt.ttms.pojo.Seat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* @author 
* @description 针对表【t_order(order)】的数据库操作Service
* @createDate 2023-05-13 17:38:23
*/
public interface OrderService extends IService<Order> {
     Order getNoBoughtOrderByOrderNo(String orderNo);

     Order selectByPrimaryKey(Long id);

     int deleteOrder(long id);

     PageInfo<Order> getOrders(int pageNum, int pageSize);

     Order insertOrder(BigDecimal amount, List<Seat> list, String pId);

     Integer payOrder(Order order1);



     int refund(Long id);

    void updateCloseOverTimeOrder(Long id);

     Order selectById(Long id);

    Map<String,Object> getOrderAmount();
}
