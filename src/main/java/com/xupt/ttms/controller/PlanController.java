package com.xupt.ttms.controller;
/*
 * @author:ck
 * @param:
 * @date:2022/8/1
 * @description:
 */

import com.xupt.ttms.pojo.Plan;
import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.service.PlanService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/plan")
public class PlanController {
    @Resource
    private PlanService planService;

    @PostMapping("/getPlanByMIdAndTime/{MId}")
    @Cacheable(value = "plan_",keyGenerator = "planKeyGenerator")
    public Result getPlanByMIdAndTime(@PathVariable("MId") String MId,@RequestParam(value = "Time",required = false) Integer Time){
        List<Plan> plans = planService.getPlanByMIdAndTime(MId,Time);
        return Result.success(plans);
    }
    @PostMapping("/getPlanByPId/{PId}")
    @Cacheable(value = "plan_",keyGenerator = "planKeyGenerator")
    public Result getPlanByPId(@PathVariable("PId") Long PId){
        Plan plan = planService.getPlanById(PId);
        return Result.success(plan);
    }
}
