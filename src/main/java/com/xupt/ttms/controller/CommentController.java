package com.xupt.ttms.controller;

import com.xupt.ttms.pojo.Comment;
import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("addComment")
    public Result addComment(@RequestBody Comment comment) {
        return Result.success(commentService.addComment(comment));
    }

    @PostMapping("beLike/{commentId}")
    public Result beLike( @PathVariable String commentId) {
        return Result.success(commentService.likeComment(Integer.valueOf(commentId)));
    }
    @PostMapping("getCommentByMId/{mId}")
    public Result getCommentByMId(@PathVariable String mId){
        return Result.success(commentService.getCommentByMId(mId));
    }

    @PostMapping("list/{mid}")
    public Result getAllCommentBymId(@PathVariable String mid) {
        return Result.success(commentService.getAllCommentBymId(mid));
    }
    @GetMapping("getScoreByMId/{mId}")
    public Result getScoreByMId(@PathVariable Long mId){
        return Result.success(commentService.getScoreByMId(mId));
    }
    @GetMapping("getScoreLen/{mId}")
    public Result getScoreLen(@PathVariable Integer mId){
        return Result.success(commentService.getScoreLen(mId));
    }
}
