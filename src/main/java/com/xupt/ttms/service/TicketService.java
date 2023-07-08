package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.pojo.Ticket;

import java.util.List;

/**
* @author 
* @description 针对表【ticket】的数据库操作Service
* @createDate 2023-05-13 17:38:46
*/
public interface TicketService extends IService<Ticket> {


     List<Ticket> getTicketsByPId(Long pId);

     int LockTicket(Long pId, List<Seat> seat);

    int UnLockTicket(Long pId, List<Seat> seat);
}
