package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xupt.ttms.pojo.Video;

import java.util.List;

/**
* @author 
* @description 针对表【video】的数据库操作Service
* @createDate 2023-05-13 17:38:58
*/
public interface VideoService extends IService<Video> {
     List<Video> getVideoByMId(Long mId, Long id);

     Video watchVideo(Integer id);

     Video getVideoBymId(Long Id);
}
