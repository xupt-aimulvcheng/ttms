package com.xupt.ttms.controller;

import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.service.MylikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/MyLike")
public class MyLikeController {
    @Resource
    private MylikeService myLikeService;

    @PostMapping("/GetAllLikeByUserName")
    public Result getAllLikeByUserName() {

        return Result.success();
    }

    @PostMapping("/getLikeByUserNameAndMId")
    public Result getLikeByUserNameAndMId(@RequestParam("mId") Long mId) {
        Map<String,Boolean> myLike = myLikeService.getLikeByUserIdAndMId(mId);
        return Result.success(myLike);
    }

    @PostMapping("/insertByusername/{mId}")
    public Result insertByUsername(@PathVariable("mId") Long mID){
        return Result.success(myLikeService.insertByUserId(mID)>=1?"收藏成功":"收藏失败");
    }

    @PostMapping("/deleteByusername/{mId}")
    public Result deleteByUsername(@PathVariable("mId") Long mID) {
        return Result.success(myLikeService.deleteByUserId(mID) >= 1 ? "取消收藏成功" : "取消收藏失败");
    }
    
}
