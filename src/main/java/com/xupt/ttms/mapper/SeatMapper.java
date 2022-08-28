package com.xupt.ttms.mapper;

import com.xupt.ttms.pojo.Seat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeatMapper {
    public List<Seat> getSeatList(@Param("studioId") Integer studyId);
}
