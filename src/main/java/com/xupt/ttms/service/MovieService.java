package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xupt.ttms.pojo.Movie;
import com.xupt.ttms.pojo.MovieType;
import com.xupt.ttms.query.PageQuery;
import com.xupt.ttms.vo.MovieVo;

import java.util.List;
import java.util.Map;

public interface MovieService extends IService<Movie> {

    List<MovieVo> getIndexMovie(Integer status);

    MovieVo getMovieById(int parseInt);

    Long getLength(Integer status);


    PageInfo<MovieVo> getMovieByType(Integer Status, Integer typeID, PageQuery pageQuery);

    List<MovieType> getAllMovieType();

    List<Map<Object, Object>> dailyBoxOfficeStats();

    List<String> getSwiperList();
}
