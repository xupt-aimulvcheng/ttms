package com.xupt.ttms.service;

import com.github.pagehelper.PageInfo;
import com.xupt.ttms.pojo.Movie;

public interface MovieService {
    PageInfo<Movie> getAllMovies(int parseInt, int parseInt1);

    Movie getMovieById(int parseInt);

    PageInfo<Movie> getMovie(String name, String startDate, String endDate, String status, int parseInt, int parseInt1);

    PageInfo<Movie> getMovieReleased(int parseInt, int parseInt1);

    int insert(Movie hall);

    int deleteMovie(String ids);

    int updateMovie(Integer id, Movie hall);
}
