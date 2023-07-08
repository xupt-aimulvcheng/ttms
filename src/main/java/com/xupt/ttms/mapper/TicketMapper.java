package com.xupt.ttms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.pojo.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 
* @description 针对表【ticket】的数据库操作Mapper
* @createDate 2023-05-13 17:38:46
* @Entity com.xupt.ttms.pojo.Ticket
*/
@org.apache.ibatis.annotations.Mapper
public interface TicketMapper extends BaseMapper<Ticket> {

    void updateOrderId(@Param("seats") List<Seat> list, @Param("pId") String pId, @Param("orderId") Long id);

    List<Ticket> getTicketsByPId(Long pId);

    int update(@Param("seats") List<Seat> seat, @Param("pId") Long pId, @Param("i") int i);
}




