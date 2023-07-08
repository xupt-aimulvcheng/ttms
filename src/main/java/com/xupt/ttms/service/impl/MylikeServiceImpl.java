package com.xupt.ttms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xupt.ttms.mapper.MylikeMapper;
import com.xupt.ttms.pojo.Mylike;
import com.xupt.ttms.service.MylikeService;
import com.xupt.ttms.util.ThreadUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 
* @description 针对表【mylike】的数据库操作Service实现
* @createDate 2023-05-13 17:36:37
*/
@Service
public class MylikeServiceImpl extends ServiceImpl<MylikeMapper, Mylike>
    implements MylikeService{

    @Override
    public Long getAllLikeByUserId(Long mId) {
        LambdaQueryWrapper<Mylike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mylike::getmId,mId);
        return baseMapper.selectCount(queryWrapper);

    }

    @Override
    public Map<String,Boolean> getLikeByUserIdAndMId(Long mId) {
        Map<String,Boolean> map = new HashMap<>();
        LambdaQueryWrapper<Mylike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mylike::getUserid,ThreadUtils.getUserId()).eq(Mylike::getmId,mId);
        Mylike mylike = baseMapper.selectOne(queryWrapper);
        map.put("BeLiked",mylike != null);
        return map;
    }

    @Override
    public Integer insertByUserId(Long mID) {
        Long userId = ThreadUtils.getUserId();
        Mylike mylike = new Mylike();
        mylike.setUserid(userId);
        mylike.setmId(mID);
        return baseMapper.insert(mylike);
    }

    @Override
    public Integer deleteByUserId(Long mID) {
        Long userId = ThreadUtils.getUserId();
        LambdaQueryWrapper<Mylike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mylike::getUserid,userId).eq(Mylike::getmId,mID);
        return baseMapper.delete(queryWrapper);
    }
}
