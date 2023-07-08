package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xupt.ttms.pojo.Comment;

import java.util.List;
import java.util.Map;

/**
* @author 
* @description 针对表【t_comment】的数据库操作Service
* @createDate 2022-10-13 22:01:25
*/
public interface CommentService extends IService<Comment> {
    public Map likeComment(Integer id);

    int addComment(Comment comment);

    List<Comment> getAllCommentBymId(String mid);

    Comment getCommentByMId(String mid);

    Double getScoreByMId(Long mId);

    Integer getScoreLen(Integer mId);
}
