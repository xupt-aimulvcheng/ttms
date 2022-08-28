package com.xupt.ttms.service.impl;/*
 * @author:ck
 * @param:
 * @date:2022/8/1
 * @description:
 */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.ttms.mapper.PlanMapper;
import com.xupt.ttms.pojo.Plan;
import com.xupt.ttms.service.PlanService;
import com.xupt.ttms.util.TypeCasting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanMapper planMapper;

    /**
     * 根据电影时长和起始时间获取终止时长
     *
     * @param mId
     * @param startTime
     * @return
     */
    public String getEndTime(String mId, String startTime) {
        LocalDateTime localDateTime = TypeCasting.formatStringToLocalDateTime(startTime);
        int length = planMapper.getMovieTimeBymID(mId);
        LocalDateTime endTime = localDateTime.plusMinutes(length);
        return TypeCasting.formatLocalDateTimeString(endTime);
    }

    public Integer getHallIDByName(String name) {
        return planMapper.getHallIDByName(name);
    }

    public int getMovieIDByName(String name) {
        return planMapper.getMovieIDByName(name);
    }

    public Double getPriceBymId(Integer mId) {
        return planMapper.getPriceBymId(mId);
    }

    public List<Plan> getPlanByName(String pName) {
        return planMapper.getPlanByName(pName);
    }

    public int insertPlan(Plan plan) {
        return planMapper.insert(plan);
    }

    public int updatePlan(Plan plan) {
        return planMapper.updatePlan(plan, plan.getId());
    }

    public int deletePlanByIds(String ids) {
        return planMapper.deletePlanByIds(ids);
    }

    public PageInfo<Plan> getAllPlansBymID(String mId, int pageNum, int PageSize) {
        Page<Plan> Movies = PageHelper.startPage(pageNum, PageSize);
        List<Plan> plans = planMapper.getAllPlansBymID(mId);
        for (int i = 0; i < plans.size(); i++) {
            Plan plan = plans.get(i);
            plan.setEndDate(getEndTime(mId, plan.getStartDate()));
            plan.setStartDate(TypeCasting.formatStringToString(plan.getStartDate()));
        }
        PageInfo<Plan> planInfo = new PageInfo(plans, 5);
        return planInfo;
    }
}
