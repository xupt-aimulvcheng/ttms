package com.xupt.ttms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupt.ttms.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 
* @description 针对表【t_comment】的数据库操作Mapper
* @createDate 2022-10-13 22:01:25
* @Entity com.xupt.ttms.pojo.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> searchAllByMid(@Param("mid") Integer mid);

    List<Comment> searchAllByMidAndUserId(@Param("mid") Integer mid, @Param("userId") Long userId);

    int updateScoreAndEvaAndUpdateTimeById(@Param("score") Double score, @Param("eva") String eva, @Param("updateTime") Date updateTime, @Param("id") Long id);

    Double getScoreByMId(@Param("MId") Long MId);

    Integer getScoreLen(@Param("mId") Integer mId);
}




