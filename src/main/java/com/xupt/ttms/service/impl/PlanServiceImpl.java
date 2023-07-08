package com.xupt.ttms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xupt.ttms.config.exception.CkException;
import com.xupt.ttms.mapper.MovieMapper;
import com.xupt.ttms.mapper.PlanMapper;
import com.xupt.ttms.pojo.MovieType;
import com.xupt.ttms.pojo.Plan;
import com.xupt.ttms.service.PlanService;
import com.xupt.ttms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 
* @description 针对表【plan】的数据库操作Service实现
* @createDate 2023-05-13 17:37:50
*/
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan>
    implements PlanService{
    @Autowired
    private MovieMapper movieMapper;
    @Override
    public List<Plan> getPlanByMIdAndTime(String mId, Integer time) {
        Date date = DateUtil.getDate();
        //如果是当天则需要时分秒，反之只需要知道那一天的日期即可
        String format = time==0?"yyyy-MM-dd HH:mm:ss":"yyyy-MM-dd";
        String Time = DateUtil.formatDate(DateUtil.dealDays(date, time),format);//今天time天后的时间
        String nextTime = DateUtil.formatDate(DateUtil.dealDays(date, time+1),"yyyy-MM-dd");//今天time+1天后的时间，不论是否当天，日期格式都必须为年-月-日的格式
        return baseMapper.getPlanByMIdAndTime(mId,Time,nextTime);
    }

    @Override
    public Plan getPlanById(Long pId) {
        Plan plan = baseMapper.getPlanById(pId);
        if (plan == null) {
            throw new CkException(20001,"该演出计划不存在");
        }
        Long mID = plan.getmId();
        List<MovieType> movieTypeList = movieMapper.getTypesById(mID);
        plan.setmType(movieTypeList);
        return plan;
    }
}




