package com.xupt.ttms.controller;

import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SeatServlet {
    @Autowired
    private SeatService seatService;
    @RequestMapping(value = "/seat/getSeats", method = RequestMethod.POST)
    @ResponseBody
    public List<Seat> getSeats(){
        return seatService.getSeatList(19);
    }
}
