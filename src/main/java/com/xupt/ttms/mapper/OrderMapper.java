package com.xupt.ttms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupt.ttms.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* @author 
* @description 针对表【t_order(order)】的数据库操作Mapper
* @createDate 2023-05-13 17:38:23
* @Entity com.xupt.ttms.pojo.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<BigDecimal> getAmountByMid(int id);

    Integer getmId(@Param("orderId") Long id);



    Order getOrderDetail(@Param("orderId") Long orderId);

    List<Order> searchAllByUserId(@Param("userId") Long userId);

    Order selectByPrimaryKey(Long id);

    List<Map<Object, Object>> dailyBoxOfficeStats();

    List<BigDecimal> getOrderAmount();
}




