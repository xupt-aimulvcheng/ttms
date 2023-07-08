package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xupt.ttms.pojo.Plan;

import java.util.List;

/**
* @author 
* @description 针对表【plan】的数据库操作Service
* @createDate 2023-05-13 17:37:50
*/
public interface PlanService extends IService<Plan> {

     List<Plan> getPlanByMIdAndTime(String mId, Integer time);

     Plan getPlanById(Long pId);
}
