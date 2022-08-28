package com.xupt.ttms.service.impl;/*
 * @author:ck
 * @param:
 * @date:2022/7/6
 * @description:
 */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.ttms.mapper.MovieMapper;
import com.xupt.ttms.pojo.Movie;
import com.xupt.ttms.pojo.MovieExample;
import com.xupt.ttms.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private SimpleDateFormat bf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MovieMapper movieMapper;

    /*static {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        movieMapper = sqlSession.getMapper(MovieMapper.class);
    }*/

    /**
     * 添加电影
     *
     * @param movie
     * @return
     */
    public int insert(Movie movie) {
        return movieMapper.insert(movie);
    }

    /**
     * 根据电影名模糊查询
     *
     * @param name
     * @return
     */
    public PageInfo<Movie> getMovie(String name, String startDate, String endDate,String status, int pageNum, int PageSize) {
        Page<Movie> Movies = PageHelper.startPage(pageNum, PageSize);
        List<Movie> movies = movieMapper.selectMovies(name,startDate,endDate,status);
        PageInfo<Movie> pageInfo = new PageInfo(movies, 5);
        return pageInfo;
    }

    /**
     * 使用Mybatis的分页插件分页查询
     * 根据当前页码和总页面数据返回分页对应的数据
     *
     * @param pageNum  当前页码
     * @param PageSize 页面总个数
     * @return pageNum：当前页的页码
     * pageSize：每页显示的条数
     * size：当前页显示的真实条数
     * total：总记录数
     * pages：总页数
     * prePage：上一页的页码
     * nextPage：下一页的页码
     * isFirstPage/isLastPage：是否为第一页/最后一页
     * hasPreviousPage/hasNextPage：是否存在上一页/下一页
     * navigatePages：导航分页的页码数
     * navigatepageNums：导航分页的页码，[1,2,3,4,5]
     * list中的数据等同于直接输出的page数据
     */
    public PageInfo<Movie> getAllMovies(int pageNum, int PageSize) {
        Page<Movie> Movies = PageHelper.startPage(pageNum, PageSize);
        List<Movie> movies = movieMapper.selectAllMovies();
        PageInfo<Movie> pageInfo = new PageInfo(movies, 5);//5指的是导航分页的总页码数
        return pageInfo;
    }

    /**
     * 根据电影类型查询电影
     */
    public PageInfo<Movie> getMoviesByType(String type, int pageNum, int PageSize) {
        Page<Movie> Movies = PageHelper.startPage(pageNum, PageSize);
        MovieExample example = new MovieExample();
        example.createCriteria().andMTypeEqualTo(type);
        List<Movie> movies = movieMapper.selectByExample(example);
        PageInfo<Movie> pageInfo = new PageInfo(movies, 5);//5指的是导航分页的总页码数
        return pageInfo;
    }


    /**
     * 根据id修改电影信息
     *
     * @param id
     * @param movie
     * @return
     */
    public int updateMovie(Integer id, Movie movie) {
        MovieExample movieExample = new MovieExample();
        movieExample.createCriteria().andIdEqualTo(id);
        return movieMapper.updateByExampleSelective(movie, movieExample);
    }

    /**
     * 根据id删除电影
     */
    public int deleteMovie(int id) {
        return movieMapper.deleteByPrimaryKey(id);
    }

    public Movie getMovieById(int id) {
        return movieMapper.getMovieById(id);
    }

    /**
     * 批量删除演出厅
     */
    public int deleteMovie(String ids) {
        return movieMapper.deleteById(ids);
    }


    public PageInfo<Movie> getMovieReleased(int pageNum, int PageSize) {
        Page<Movie> Movies = PageHelper.startPage(pageNum, PageSize);
        MovieExample example = new MovieExample();
        example.createCriteria().andStatusEqualTo("上映中");
        List<Movie> movies = movieMapper.selectByExample(example);
        PageInfo<Movie> pageInfo = new PageInfo(movies, 5);//5指的是导航分页的总页码数
        return pageInfo;
    }
}
