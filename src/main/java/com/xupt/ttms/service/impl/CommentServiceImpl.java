package com.xupt.ttms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xupt.ttms.mapper.CommentMapper;
import com.xupt.ttms.mapper.UserMapper;
import com.xupt.ttms.pojo.Comment;
import com.xupt.ttms.pojo.User;
import com.xupt.ttms.service.CommentService;
import com.xupt.ttms.util.ThreadUtils;
import com.xupt.ttms.vo.UserVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 
 * @description 针对表【t_comment】的数据库操作Service实现
 * @createDate 2022-10-13 22:01:25
 */
@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Map likeComment(Integer id) {
        User user = ThreadUtils.getUser();
        String key = "comment:liked" + id;
        Double score = stringRedisTemplate.opsForZSet().score(key, user.getId().toString());
        boolean isSuccess;
        Map<String, Boolean> map = new HashMap<>();
        if (score == null) {
            //  如果未点赞，可以点赞
            //  数据库点赞数+1
            isSuccess = update().setSql("liked=liked+1").eq("id", id).update();
            //保存用户到redis的SortedSet集合
            if (isSuccess) {
                stringRedisTemplate.opsForZSet().add(key, user.getId().toString(), System.currentTimeMillis());
                map.put("beLike", true);
            }
        } else {
            //  如果已点赞，取消点赞
            //  数据库点赞数-1
            isSuccess = update().setSql("liked=liked-1").eq("id", id).update();
            //把用户从redis的set集合移除
            if (isSuccess) {
                stringRedisTemplate.opsForZSet().remove(key, user.getId().toString());
                map.put("beLike", false);
            }
        }
        return map;
    }

    @Override
    public int addComment(Comment comment) {
        Date date = new Date();
        if (comment.getId() == null) { //未进行评论
            comment.setCreateTime(date);
            comment.setUpdateTime(date);
            comment.setUserId(ThreadUtils.getUserId());
            log.debug("传进来的评价为" + comment);
            int affect = 0;
            affect = commentMapper.insert(comment);
            return affect;
        } else {
            comment.setUpdateTime(date);
            int result = commentMapper.updateScoreAndEvaAndUpdateTimeById(comment.getScore(),comment.getEva(),comment.getUpdateTime(),comment.getId());
            return result;
        }
    }

    @Override
    public List<Comment> getAllCommentBymId(String mid) {
        User user1 = ThreadUtils.getUser();
        List<Comment> comments = commentMapper.searchAllByMid(Integer.valueOf(mid));
        for (int i = 0; i < comments.size(); i++) {
            Long commentId = comments.get(i).getId();
            String key = "comment:liked" + commentId;
            Double score = stringRedisTemplate.opsForZSet().score(key, user1.getId().toString());
            comments.get(i).setBeLike(score != null);//判断用户是否点赞
            Long userId = comments.get(i).getUserId();
            User user = userMapper.selectById(userId);
            log.debug("user为" + user);
            UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);
            Boolean bought = stringRedisTemplate.opsForSet().isMember(mid + "bought", userId.toString());//用户是否购买该电影的电影票
            userVo.setBought(bought);
            comments.get(i).setUser(userVo);
        }
        return comments;
    }

    @Override
    public Comment getCommentByMId(String mid) {
        User user = ThreadUtils.getUser();
        Comment comment = null;
        List<Comment> commentList = commentMapper.searchAllByMidAndUserId(Integer.valueOf(mid), user.getId());
        if (commentList.size()>=1) {
            comment = commentList.get(0);
        }
        return comment;
    }

    @Override
    public Double getScoreByMId(Long mId) {
        return commentMapper.getScoreByMId(mId);
    }

    @Override
    public Integer getScoreLen(Integer mId) {
        return commentMapper.getScoreLen(mId);
    }
}
