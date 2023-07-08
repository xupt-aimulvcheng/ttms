package com.xupt.ttms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xupt.ttms.config.exception.CkException;
import com.xupt.ttms.mapper.TicketMapper;
import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.pojo.Ticket;
import com.xupt.ttms.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 
* @description 针对表【ticket】的数据库操作Service实现
* @createDate 2023-05-13 17:38:46
*/
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket>
    implements TicketService{
    private final Object lock = new Object();
    @Override
    public List<Ticket> getTicketsByPId(Long pId) {
        List<Ticket> ticketList = baseMapper.getTicketsByPId(pId);
        if (ticketList == null) {
            throw new CkException(20001,"该演出计划不存在");
        }
        return ticketList;
    }

    @Override
    public int LockTicket(Long pId, List<Seat> seat) {
        synchronized (lock) {
            // 方法体
            return baseMapper.update(seat, pId, 2);
        }
    }

    @Override
    public int UnLockTicket(Long pId, List<Seat> seat) {
        synchronized (lock) {
            // 方法体
            return baseMapper.update(seat, pId, 1);
        }
    }
}
