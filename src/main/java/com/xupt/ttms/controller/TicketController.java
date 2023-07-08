package com.xupt.ttms.controller;

import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.pojo.Ticket;
import com.xupt.ttms.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class TicketController {
    @Resource
    private TicketService ticketService;

    @PostMapping("/ticket/getTicketsByPId/{PId}")
    @Cacheable(value = "ticket_",keyGenerator = "ticketKeyGenerator")
    public Result getTicketsByPId(@PathVariable("PId") Long PId){
        List<Ticket> tickets = ticketService.getTicketsByPId(PId);
        return Result.success(tickets);
    }
    @PostMapping("/LockTicket/{pId}")
    public Result LockTicket( @PathVariable("pId") Long pId, @RequestBody List<Seat> seat){
        return Result.success(ticketService.LockTicket(pId,seat)>0?"锁定成功":"锁定失败");
    }

    @PostMapping("/UnLockTicket/{pId}")
    public Result UnLockTicket(@PathVariable("pId") Long pId, @RequestBody List<Seat> seat) {
        return Result.success(ticketService.UnLockTicket(pId, seat) > 0 ? "解除锁定成功" : "抱歉服务器出现问题请刷新页面重试");
    }
}
