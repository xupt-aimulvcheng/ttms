package com.xupt.ttms.mapper;

import com.xupt.ttms.pojo.Movie;
import com.xupt.ttms.pojo.MovieExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovieMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int countByExample(MovieExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int deleteByExample(MovieExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int insert(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int insertSelective(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    List<Movie> selectByExample(MovieExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    Movie selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int updateByExampleSelective(@Param("record") Movie record, @Param("example") MovieExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int updateByExample(@Param("record") Movie record, @Param("example") MovieExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int updateByPrimaryKeySelective(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbggenerated Wed Jul 06 15:34:23 CST 2022
     */
    int updateByPrimaryKey(Movie record);

    int deleteById(@Param("ids") String ids);

    List<Movie> selectAllMovies();

    List<Movie> selectMovies(@Param("m_name") String m_name,@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("status") String status);

    Movie getMovieById(@Param("id") int id);
}