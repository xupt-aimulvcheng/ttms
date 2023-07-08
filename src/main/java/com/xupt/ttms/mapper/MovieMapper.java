package com.xupt.ttms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupt.ttms.pojo.Movie;
import com.xupt.ttms.pojo.MovieType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MovieMapper extends BaseMapper<Movie> {

    List<MovieType> getTypesById(@Param("id") Long id);

    List<Movie> getMovedata(@Param("typeId") Integer typeId, @Param("status") Integer status);
}
