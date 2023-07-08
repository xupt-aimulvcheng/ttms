package com.xupt.ttms.controller;

import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.pojo.Video;
import com.xupt.ttms.service.VideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
public class VideoController {
    @Resource
    private VideoService videoService;

    @PostMapping("/video/getVideoByMId/{mId}")
    public Result getVideoByMId(@PathVariable("mId") Long mId, @RequestParam(value = "id", required = false) Long id) {
        List<Video> videos = videoService.getVideoByMId(mId, id);
        return Result.success(videos);
    }

    @PostMapping("/video/watchVideo/{id}")
    public Result watchVideo(@PathVariable("id") Integer id) {
        Video video = videoService.watchVideo(id);
        return Result.success(video);
    }
}
