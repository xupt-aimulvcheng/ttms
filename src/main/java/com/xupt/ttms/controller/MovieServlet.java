package com.xupt.ttms.controller;/*
 * @author:ck
 * @param:
 * @date:2022/7/31
 * @description:
 -Dfile.encoding=UTF-8
 */

import com.github.pagehelper.PageInfo;
import com.xupt.ttms.pojo.Movie;
import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.service.MovieService;
import com.xupt.ttms.util.ToResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MovieServlet {
    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/movie/updateMovie", method = RequestMethod.POST)
    @ResponseBody
    public String updateMovie(@RequestBody Movie movie) {
        int result = movieService.updateMovie(movie.getId(), movie);
        return (result >= 1 ? "修改成功" : "修改失败");
    }

    @RequestMapping(value = "/movie/getMovieById/{id}", method = RequestMethod.GET)
    public String getMovieById(@PathVariable("id") Integer id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "manager/page/movie/edit";
    }

    @RequestMapping(value = "/movie/getMovie", method = RequestMethod.GET)
    @ResponseBody
    public Result getMovie(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize, @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, @RequestParam(value = "status", required = false) String status) {
        PageInfo<Movie> movies = movieService.getMovie(name, startDate, endDate, status, pageNum, pageSize);
        Result result = ToResult.getResult(movies);
        return result;
    }

    @RequestMapping(value = "/movie/getMovieReleased", method = RequestMethod.GET)
    @ResponseBody
    public Result getMovieReleased(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize, @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) {
        PageInfo<Movie> movies = movieService.getMovie(name, startDate, endDate, "上映中", pageNum, pageSize);
        Result result = ToResult.getResult(movies);
        return result;
    }

    @RequestMapping(value = "/movie/addMovie", method = RequestMethod.POST)
    public String addMovie(MultipartFile photo, HttpSession session,String mName, String mType, Integer mLength, Double mPrice, String mDate, String mDirector, String mActor, Double mBoxOffice, Double mScore, String mIntroduction, String mImage, String status, HttpServletRequest servlet) throws IOException {
        String fileName = photo.getOriginalFilename();
        //处理文件重名问题
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + hzName;
        //获取服务器中photo目录的路径
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("static/images/movies");
        File file = new File(photoPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        //实现上传功能
        photo.transferTo(new File(finalPath));
        Movie movie = new Movie(mName,mType,mLength,mPrice,mDate,mDirector,mActor,mBoxOffice,mScore,mIntroduction,finalPath,status);
        System.out.println(movie);
        int insert = movieService.insert(movie);
        return (insert >= 1 ? "manager/page/movie/index" : "manager/page/movie/add");
    }

    @RequestMapping(value = "/movie/upfile", method = RequestMethod.POST)
    public String upFile(MultipartFile photo, HttpSession session, HttpServletRequest servlet) throws IOException {

        return "redirect:/movie/addMovie";
    }

    @RequestMapping(value = "/movie/deleteMovie/{ids}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteMovie(@PathVariable("ids") String ids) {
        int delete = movieService.deleteMovie(ids);
        return (delete >= 1 ? "删除成功" : "删除失败");
    }
}
