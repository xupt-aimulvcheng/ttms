package com.xupt.ttms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupt.ttms.pojo.Seat;

import java.util.List;

/**
* @author 
* @description 针对表【seat】的数据库操作Mapper
* @createDate 2023-05-13 17:48:03
* @Entity com.xupt.ttms.pojo.Seat
*/
@org.apache.ibatis.annotations.Mapper
public interface SeatMapper extends BaseMapper<Seat> {

    List<Seat> selectSeatByOrderId(Long id);
}




