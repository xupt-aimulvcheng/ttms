package com.xupt.ttms.controller;/*
 * @author:ck
 * @param:
 * @date:2022/7/31
 * @description:
 -Dfile.encoding=UTF-8
 */

import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.query.PageQuery;
import com.xupt.ttms.service.MovieService;
import com.xupt.ttms.vo.MovieVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/movie")
@CrossOrigin
public class MovieController {
    @Resource
    private MovieService movieService;

    @GetMapping("getHotMovieCount")
    public Result getLenReleased(){
        Long len = movieService.getLength(1);
        return Result.success(len);
    }
    @GetMapping("getComingSoonMovieCount")
    public Result getComingSoonMovieCount(){
        Long len = movieService.getLength(3);
        return Result.success(len);
    }
    @GetMapping("getAllMovieType")
    public Result getAllMovieType(){
        return Result.success(movieService.getAllMovieType());
    }
    @GetMapping("GetMovieByType")
    @Cacheable(value ="movie_",keyGenerator = "movieKeyGenerator")
    public Result getMovieByType(@RequestParam("type") Integer typeID,@RequestParam("status") Integer status, PageQuery pageQuery){
        return Result.success(movieService.getMovieByType(status,typeID, pageQuery));
    }

    @PostMapping("getMovieById/{id}")
    public Result getMovieById(@PathVariable("id") Integer id){
        MovieVo movieVo = movieService.getMovieById(id);
        return Result.success(movieVo);
    }

    @PostMapping("dailyBoxOfficeStats")
    public Result dailyBoxOfficeStats(){
        return Result.success(movieService.dailyBoxOfficeStats());
    }
    @PostMapping("/index/getHotMovie")
    public Result getHotMovie(){
        return Result.success(movieService.getIndexMovie(1));
    }
    @PostMapping("/index/getComingSoonMovie")
    public Result getComingSoonMovie(){
        return Result.success(movieService.getIndexMovie(3));
    }
    @GetMapping("getSwiperList")
    public Result getSwiperList(){
        return Result.success(movieService.getSwiperList());
    }
}
