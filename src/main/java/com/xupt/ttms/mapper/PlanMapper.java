package com.xupt.ttms.mapper;

import com.xupt.ttms.pojo.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanMapper {
    int deletePlanByIds(@Param("ids") String ids);

    int updatePlan(Plan plan, @Param("ids") int id);

    int insert(@Param("plan") Plan plan);

    List<Plan> getAllPlansBymID(@Param("mId") String mId);

    int getMovieTimeBymID(@Param("mId") String mId);

    Integer getHallIDByName(@Param("name") String name);

    int getMovieIDByName(@Param("name") String name);

    Double getPriceBymId(@Param("mId") Integer mId);

    List<Plan> getPlanByName(@Param("name") String pName);
}