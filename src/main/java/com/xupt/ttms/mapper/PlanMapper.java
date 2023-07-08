package com.xupt.ttms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupt.ttms.pojo.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 
* @description 针对表【plan】的数据库操作Mapper
* @createDate 2023-05-13 17:37:50
* @Entity com.xupt.ttms.pojo.Plan
*/
@org.apache.ibatis.annotations.Mapper
public interface PlanMapper extends BaseMapper<Plan> {

    List<Plan> getPlanByMIdAndTime(String mId, String time, String nextTime);

    Plan getPlanById(@Param("pId") Long pId);
}




