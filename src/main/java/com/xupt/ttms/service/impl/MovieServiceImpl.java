package com.xupt.ttms.service.impl;/*
 * @author:ck
 * @param:
 * @date:2022/7/6
 * @description:
 */
/*
  使用Mybatis的分页插件分页查询
  根据当前页码和总页面数据返回分页对应的数据

  @param pageNum  当前页码
 * @param PageSize 页面总个数
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

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.ttms.config.exception.CkException;
import com.xupt.ttms.mapper.*;
import com.xupt.ttms.pojo.Movie;
import com.xupt.ttms.pojo.MovieType;
import com.xupt.ttms.query.PageQuery;
import com.xupt.ttms.service.MovieService;
import com.xupt.ttms.util.AmountUtils;
import com.xupt.ttms.util.RedisUtils;
import com.xupt.ttms.vo.MovieVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.xupt.ttms.config.enums.RedisKeyEnum.MOVIEPY;

@Service
@Transactional
@Slf4j
public class MovieServiceImpl extends ServiceImpl<MovieMapper,Movie> implements MovieService {
    @Resource
    private MovieMapper movieMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private MovieTypeMapper movieTypeMapper;
    @Resource
    private MovieTypeRelationMapper movieTypeRelationMapper;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 获取当前正在上映的电影数量。
     *
     * @param status 电影状态（例如已上映、即将上映）
     * @return 当前正在上映的电影数量
     */
    @Override
    public Long getLength(Integer status) {
        LambdaQueryWrapper<Movie> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Movie::getStatus, status);
        return movieMapper.selectCount(queryWrapper);
    }

    /**
     * 根据状态、类型和分页查询参数获取电影列表。
     *
     * @param status     电影状态（例如已上映、即将上映）
     * @param typeId     电影类型ID
     * @param pageQuery  分页查询参数
     * @return 电影的分页信息
     */
    @Override
    public PageInfo<MovieVo> getMovieByType(Integer status, Integer typeId, PageQuery pageQuery) {
        String key = "movie_" + status + "_" + typeId + "_" + pageQuery.getPage() + "_" + pageQuery.getLimit();
        String str = stringRedisTemplate.opsForValue().get(key);
        if (str != null) {
            return JSON.parseObject(str, PageInfo.class);
        }
        List<MovieVo> movieVoList = new ArrayList<>();
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
        List<Movie> movies;
        // 当typeId为0时，查询所有电影
        if (typeId == 0) {
            LambdaQueryWrapper<Movie> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(Movie::getmActor, Movie::getStatus, Movie::getmDate, Movie::getmDirector, Movie::getmImage, Movie::getmIntroduction, Movie::getmLength, Movie::getmName, Movie::getId);
            movies = movieMapper.selectList(queryWrapper.eq(Movie::getStatus, status));
            return getMovieVoPageInfo(key, movieVoList, movies);
        } else {
            movies = movieMapper.getMovedata(typeId, status);
            return getMovieVoPageInfo(key, movieVoList, movies);
        }
    }

    /**
     * 获取所有电影类型。
     *
     * @return 电影类型列表
     */
    @Override
    public List<MovieType> getAllMovieType() {
        if (stringRedisTemplate.opsForValue().get(MOVIEPY.getKey()) != null) {
            return JSON.parseArray(stringRedisTemplate.opsForValue().get(MOVIEPY.getKey()), MovieType.class);
        }
        List<MovieType> movieTypes = movieTypeMapper.selectList(null);
        if (movieTypes.isEmpty()) {
            throw new CkException(2001, "电影类型为空");
        }
        redisUtils.set(MOVIEPY, JSONObject.toJSON(movieTypes));
        return movieTypes;
    }

    /**
     * 获取电影的每日票房统计。
     *
     * @return 电影的每日票房统计列表
     */
    @Override
    public List<Map<Object, Object>> dailyBoxOfficeStats() {
        List<Map<Object, Object>> list = orderMapper.dailyBoxOfficeStats();
        if (list.isEmpty()){
            throw new CkException(2001,"今日暂未票房");
        }
        Map<Integer, List<BigDecimal>> classifiedData = new HashMap<>();
        // 遍历原始数据列表
        for (Map<Object, Object> data : list) {
            // 获取电影ID
            Integer mId = (Integer) data.get("m_id");
            // 获取电影票房
            BigDecimal amount = (BigDecimal) data.get("amount");
            // 如果数据中没有该电影ID，则新建一个列表
            if (!classifiedData.containsKey(mId)) {
                classifiedData.put(mId, new ArrayList<>());
            }
            // 将该电影的票房添加到对应的列表中
            classifiedData.get(mId).add(amount);
        }
        List<Map<Object, Object>> result = new ArrayList<>();
        // 遍历分类后的数据列表
        for (Map.Entry<Integer, List<BigDecimal>> entry : classifiedData.entrySet()) {
            // 获取电影ID
            Integer mId = entry.getKey();
            // 获取该电影的票房列表
            List<BigDecimal> amounts = entry.getValue();
            Map<String, Object> amountMap = AmountUtils.calculateTotalAmount(amounts, 2);
            // 将总票房添加到数据列表中
            Map<Object, Object> data = new HashMap<>();
            data.put("m_id", mId);
            data.put("m_name",getById(mId).getmName());
            data.put("amount", amountMap);
            result.add(data);
        }
        // 对 result 列表按照 totalAmount 键从大到小进行排序
        // 对 result 列表按照 totalAmount 键从大到小进行排序
        result.sort((data1, data2) -> {
            BigDecimal totalAmount1 = new BigDecimal((String) ((Map<?, ?>) data1.get("amount")).get("totalAmount"));
            BigDecimal totalAmount2 = new BigDecimal((String) ((Map<?, ?>) data2.get("amount")).get("totalAmount"));
            return totalAmount2.compareTo(totalAmount1);
        });
//        log.info("result: {}", result);
        Integer mId = (Integer) result.get(0).get("m_id");
        String url = movieMapper.selectById(mId).getmImage();
        result.get(0).put("url", url);
        return result.subList(0, Math.min(result.size(), 5));
    }
    //获取首页轮播图
    @Override
    public List<String> getSwiperList() {
        //获取最近上映的电影
        LambdaQueryWrapper<Movie> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Movie::getStatus, 1).orderByDesc(Movie::getmDate);
        List<Movie> movies = movieMapper.selectList(queryWrapper);
        return movies.stream().map(Movie::getmImage).limit(5).collect(Collectors.toList());
    }

    //获取首页要显示的电影
    @Override
    public List<MovieVo> getIndexMovie(Integer status) {
        List<MovieVo> movieVoList = new ArrayList<>();
        LambdaQueryWrapper<Movie> queryWrapper = new LambdaQueryWrapper<>();
        //按照时间倒序获取前八个电影
        queryWrapper.eq(Movie::getStatus, status).orderByDesc(Movie::getmDate);
        List<Movie> movies = movieMapper.selectList(queryWrapper);
        movies.stream().limit(8).forEach(movie -> {
            MovieVo movieVo = new MovieVo();
            BeanUtil.copyProperties(movie, movieVo, "createTime", "updateTime");
            movieVo.setmScore(commentMapper.getScoreByMId(movie.getId()) == null ? 0 : commentMapper.getScoreByMId(movie.getId()));
            movieVoList.add(movieVo);
        });
        return movieVoList;
    }

    /**
     * 根据电影ID获取电影信息。
     *
     * @param id 电影ID
     * @return 电影信息
     */
    @Override
    public MovieVo getMovieById(int id) {
        Movie movie = movieMapper.selectById(id);
        if (StringUtils.isEmpty(movie)) {
            throw new CkException(2001, "电影不存在");
        }
        MovieVo movieVo = new MovieVo();
        List<MovieType> movieTypes = movieMapper.getTypesById(movie.getId());
        movie.setmType(movieTypes);
        List<BigDecimal> list = orderMapper.getAmountByMid(id);
        movie.setmBoxOffice(AmountUtils.calculateTotalAmount(list,3)); // 计算电影的总票房
        Double score = commentMapper.getScoreByMId(movie.getId());
        movie.setmScore( score == null ? 0:score); // 获取电影的平均分
        BeanUtil.copyProperties(movie, movieVo, "createTime", "updateTime", "isDeleted", "param");
        return movieVo;
    }
    /**
     * 封装电影信息的分页对象。
     *
     * @param key          Redis缓存的键
     * @param movieVoList  电影信息列表
     * @param movies       电影实体列表
     * @return 电影信息的分页对象
     */
    @NotNull
    private PageInfo<MovieVo> getMovieVoPageInfo(String key, List<MovieVo> movieVoList, @NotNull List<Movie> movies) {
        if (movies.isEmpty()) {
            throw new CkException(2001, "电影为空");
        }
        PageInfo<Movie> pageInfo = new PageInfo<>(movies);
        //
        movies.forEach(movie -> {
            List<MovieType> movieTypes = movieMapper.getTypesById(movie.getId());
            movie.setmType(movieTypes);
            MovieVo movieVo = new MovieVo();
            BeanUtil.copyProperties(movie, movieVo, "createTime", "updateTime", "isDeleted", "param");
            movieVo.setmScore(commentMapper.getScoreByMId(movie.getId()) == null ? 0 : commentMapper.getScoreByMId(movie.getId()));
            movieVoList.add(movieVo);
        });
        PageInfo<MovieVo> pageInfoVo = new PageInfo<>(movieVoList);
        BeanUtil.copyProperties(pageInfo, pageInfoVo, "list");
        log.info("正在上映的电影数量为：{}，所有电影信息为：{}", movieVoList.size(), movieVoList);
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(pageInfoVo), 30, TimeUnit.MINUTES);
        return pageInfoVo;
    }
}
