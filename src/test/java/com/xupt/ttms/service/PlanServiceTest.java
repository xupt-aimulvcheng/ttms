package com.xupt.ttms.service;

import com.xupt.ttms.service.impl.PlanServiceImpl;
import org.junit.Test;

import java.util.List;

public class PlanServiceTest {
    PlanService planService = new PlanServiceImpl();
    @Test
    public void getAllTime() {
        List<String> times = planService.getAllTime("2022-01-01 00:00:00", "2022-01-01 00:00:03");
        for (int i = 0; i < times.size(); i++) {
            System.out.println(times.get(i).toString());
        }
    }
    @Test
    public void belongCalendar() {
        System.out.println(planService.getEndTime("12","2022-05-30 10:19:00"));
        System.out.println(planService.belongCalendar("2022-08-26 14:59:04","12","409演出厅"));
    }

}
