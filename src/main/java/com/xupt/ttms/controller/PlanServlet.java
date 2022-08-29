package com.xupt.ttms.controller;
/*
 * @author:ck
 * @param:
 * @date:2022/8/1
 * @description:
 */

import com.github.pagehelper.PageInfo;
import com.xupt.ttms.pojo.Plan;
import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.service.PlanService;
import com.xupt.ttms.util.ToResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebServlet;

@Controller
@WebServlet("/planServlet")
public class PlanServlet {
    @Autowired
    private PlanService planService;

    @RequestMapping(value = "/plan/getAllPlan")
    @ResponseBody
    public Result getAllPlan(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize, @RequestParam("id") int id, @RequestBody Plan plan) {
        PageInfo<Plan> plans = planService.getAllPlansBymID(String.valueOf(id), pageNum, pageSize,plan.getStartDate(),plan.getEndDate(),plan.getpName());
        Result result = ToResult.getResult(plans);
        return result;
    }



    @RequestMapping(value = "/plan/addPlan", method = RequestMethod.POST)
    @ResponseBody
    public String addPlan(@RequestBody Plan plan) {
        if (planService.getHallIDByName(plan.gethName()) == null)
            return "抱歉，无该演出厅,请输入正确的数据";
        if (!planService.getPlanByName(plan.getpName()).isEmpty())
            return "抱歉，该演出计划已存在,请输入正确的数据";
        plan.setPrice(planService.getPriceBymId(plan.getmId()));
        plan.setEndDate(planService.getEndTime(String.valueOf(plan.getmId()), plan.getStartDate()));
        plan.sethId(planService.getHallIDByName(plan.gethName()));
        System.out.println(plan);
        return (planService.insertPlan(plan) > 0 ? "添加成功" : "添加失败");
    }
}
