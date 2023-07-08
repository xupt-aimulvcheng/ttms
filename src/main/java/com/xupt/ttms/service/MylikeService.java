package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xupt.ttms.pojo.Mylike;

import java.util.Map;

/**
* @author 
* @description 针对表【mylike】的数据库操作Service
* @createDate 2023-05-13 17:36:37
*/
public interface MylikeService extends IService<Mylike> {

    Long getAllLikeByUserId(Long mId);

    Map<String,Boolean> getLikeByUserIdAndMId(Long mId);

    Integer insertByUserId(Long mID);

    Integer deleteByUserId(Long mID);
}
