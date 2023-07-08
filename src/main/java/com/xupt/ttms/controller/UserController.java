package com.xupt.ttms.controller;

import com.xupt.ttms.pojo.Result;
import com.xupt.ttms.pojo.User;
import com.xupt.ttms.service.UserService;
import com.xupt.ttms.util.ThreadUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping( "/register")
    public Result register(@RequestBody User user) {
        int i = userService.register(user);
        return Result.success(i>0?"注册成功":"注册失败");
    }
    @PostMapping("/checkUserName/{username}")
    public Result checkUsername(@PathVariable String username){
        return Result.success(userService.checkUsername(username));
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return Result.success(userService.login(user));
    }
    @PostMapping("/getUser")
    public Result getUser(){
        return Result.success(userService.getUser());
    }
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user,HttpServletRequest request){
        return Result.success(userService.updateUser(user,request.getHeader("token"))>0?"修改成功":"修改失败");
    }
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return Result.success(userService.upload(file,request.getHeader("token"))>0?"上传成功":"上传失败");
    }
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        stringRedisTemplate.delete(request.getHeader("token"));
        ThreadUtils.remove();
        return Result.success("退出成功");
    }
    //发送验证码
    @PostMapping("/sendCode")
    public Result sendCode(@RequestParam("email") String email){
        userService.sendCode(email);
        return Result.success();
    }
}
