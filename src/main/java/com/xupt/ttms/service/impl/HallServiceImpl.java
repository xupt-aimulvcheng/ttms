package com.xupt.ttms.service.impl;/*
 * @author:ck
 * @param:
 * @date:2022/7/6
 * @description:
 */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.ttms.mapper.HallMapper;
import com.xupt.ttms.pojo.Hall;
import com.xupt.ttms.pojo.HallExample;
import com.xupt.ttms.pojo.Movie;
import com.xupt.ttms.pojo.Seat;
import com.xupt.ttms.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HallServiceImpl implements HallService {
    @Autowired
    private HallMapper hallMapper;

/*    static {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        hallMapper = sqlSession.getMapper(HallMapper.class);
    }*/

    public int insert(Hall hall) {
        int j = hallMapper.insert(hall);
        Integer id = hall.getId();
        List<Seat> seats = getSeatList(id);
        int i = hallMapper.insertSeat(seats);
        return i+j;
    }

    /**
     * 根据id动态获取座位集合
     *
     * @param id
     * @return
     */
    public List<Seat> getSeatList(Integer id) {
        Hall hall = hallMapper.selectByPrimaryKey(id);
        int row = hall.getHrow();
        int column = hall.getHcol();
        List<Seat> list = new ArrayList<>();
        for (int i = 1; i <=row ; i++) {
            for (int j = 1; j <= column ; j++) {
                Seat seat = new Seat(null,id,i,j);
                list.add(seat);
            }
        }
        return list;
    }

    public PageInfo<Hall> getAllHall(int pageNum, int PageSize) {
        Page<Movie> Movies = PageHelper.startPage(pageNum, PageSize);
        List<Hall> movies = hallMapper.selectByExample(null);
        PageInfo<Hall> pageInfo = new PageInfo(movies, 5);//5指的是导航分页的总页码数
        return pageInfo;
    }

    /**
     * 根据演出厅名模糊查询
     *
     * @param name
     * @return
     */
    public PageInfo<Hall> getHallByName(String name, int pageNum, int PageSize) {
        Page<Movie> Movies = PageHelper.startPage(pageNum, PageSize);
        HallExample movieExample = new HallExample();
        movieExample.createCriteria().andHNameLike("%" + name + "%");
        List<Hall> movies = hallMapper.selectByExample(movieExample);
        PageInfo<Hall> pageInfo = new PageInfo(movies, 5);
        return pageInfo;
    }

    /**
     * 根据演出厅编号查询演出厅
     */

    public Hall getHallById(int id) {
        return hallMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改演出厅
     */
    public int updateHall(Hall hall) {
        HallExample hallExample = new HallExample();
        hallExample.createCriteria().andIdEqualTo(hall.getId());
        return hallMapper.updateByExampleSelective(hall, hallExample);
    }

    /**
     * 批量删除演出厅
     */
    public int deleteHall(String ids) {
        return hallMapper.deleteById(ids);
    }
}
