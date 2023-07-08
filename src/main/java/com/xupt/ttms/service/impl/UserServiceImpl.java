package com.xupt.ttms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xupt.ttms.config.enums.MailContentTypeEnum;
import com.xupt.ttms.config.enums.RedisKeyEnum;
import com.xupt.ttms.config.exception.CkException;
import com.xupt.ttms.handler.CodeHandler;
import com.xupt.ttms.mapper.UserMapper;
import com.xupt.ttms.pojo.User;
import com.xupt.ttms.service.UserService;
import com.xupt.ttms.util.*;
import com.xupt.ttms.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-05-13 17:51:10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private MailSenderUtil mailSender;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int register(User user) {
        if (!RegexUtils.isValidEmail(user.getEmail())) {
            throw new CkException(20001, "邮箱格式不正确");
        }
        if (!RegexUtils.isValidPassword(user.getPassword())) {
            throw new CkException(20001, "密码格式不正确");
        }
        if (!RegexUtils.isValidUsername(user.getUsername())) {
            throw new CkException(20001, "用户名格式不正确");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User user1 = usermapper.selectOne(wrapper);
        if (user1 != null) {
            throw new CkException(20001, "用户名已存在");
        }
        String MD5PassWord = MD5.encrypt(user.getPassword());
        user.setPassword(MD5PassWord);
        return usermapper.insert(user);
    }

    @Override
    public Map<String, Object> login(User user) {
        Map<String, Object> map = new HashMap<>();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User user1 = usermapper.selectOne(wrapper);
        if (user1 == null) {
            map.put("code", 400);
            map.put("msg", "用户名不存在");
            return map;
        }
        String MD5PassWord = MD5.encrypt(user.getPassword());
        if (!user1.getPassword().equals(MD5PassWord)) {
            map.put("code", 400);
            map.put("msg", "密码错误");
            return map;
        } else {
            String token = UUID.randomUUID().toString(true);
            UserVo userInfo = BeanUtil.copyProperties(user1, UserVo.class);
            Map<String, Object> map1 = BeanMapUtils.beanToMap(userInfo);
            map1.remove("bought");
            stringRedisTemplate.opsForHash().putAll(token, map1);
            stringRedisTemplate.expire(RedisKeyEnum.USER.getKey() + token, 30l, TimeUnit.MINUTES);
            map.put("code", 200);
            map.put("msg", "登录成功");
            map.put("token", token);
            return map;
        }
    }

    @Override
    public UserVo getUser() {
        User user = ThreadUtils.getUser();
        return BeanUtil.copyProperties(user, UserVo.class);
    }

    @Override
    public int updateUser(User user, String token) {
        UserVo userInfo = BeanUtil.copyProperties(user, UserVo.class);
        Map<String, Object> map = BeanMapUtils.beanToMap(userInfo);
        map.remove("bought");
        map.remove("img");
        stringRedisTemplate.opsForHash().putAll(token, map);
        int result;
        result = usermapper.updateById(user);
        return result;
    }

    @Override
    public int upload(MultipartFile file, String token) {
        String url = OOSUtil.upload(file, "user/");
        //将居住地放入数组中
        stringRedisTemplate.opsForHash().put(token, "img", url);
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", ThreadUtils.getUserId()).set("img", url);
        return usermapper.update(null, wrapper);
    }

    @Override
    public void sendCode(String email) {
        CodeHandler.handle(() -> {
            sendCodeInternal(email);
        });
    }

    private void sendCodeInternal(String email) {
        String code = checkCodeSendStatus(email, RedisKeyEnum.CODE, 2);
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.CODE.getKey() + email, code, 2, TimeUnit.MINUTES);
        try {
            mailSender.content("<html><body style=\"text-align: center;\"><h1>尊敬的开心影院用户，您的验证码是：<strong style=\"font-size: 31px; color: red;\">" + code + "</strong></h1><h1>验证码将在 2 分钟后过期，请尽快使用。</h1></body></html>")
                    .title("【开心影院】身份验证")
                    .contentType(MailContentTypeEnum.HTML)
                    .targets(List.of(email))
                    .send();
        } catch (Exception e) {
            throw new CkException(20001, "验证码已发送，请勿重复发送");
        }
    }

    @Override
    public String checkUsername(String username) {
        if (!RegexUtils.isValidUsername(username)) {
            throw new CkException(20001, "用户名格式不正确");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = usermapper.selectOne(wrapper);
        if (user != null) {
            return "用户名已存在";
        }
        return "用户名可用";
    }

    private String checkCodeSendStatus(String email, RedisKeyEnum redisKeyEnum, int timeout) {
        if (!RegexUtils.isValidEmail(email)) {
            throw new CkException(20001, "邮箱格式不正确");
        }
        if (stringRedisTemplate.opsForValue().get(redisKeyEnum.getKey() + email) != null) {
            throw new CkException(20001, "验证码已发送，请勿重复发送");
        }
        if (stringRedisTemplate.opsForValue().get(RedisKeyEnum.EMAIL.getKey() + email) == null) {
            // 1分钟内没有发送过验证码
            stringRedisTemplate.opsForValue().set(RedisKeyEnum.EMAIL.getKey() + email, "1", 1, TimeUnit.MINUTES);
        } else {
            stringRedisTemplate.opsForValue().decrement(RedisKeyEnum.EMAIL.getKey() + email);
            stringRedisTemplate.opsForValue().decrement(redisKeyEnum.getKey() + email); //每2分钟只能发送一次
        }
        Random random = new Random();
        String code = String.valueOf(random.nextInt(900000) + 100000);
        stringRedisTemplate.opsForValue().set(redisKeyEnum.getKey() + email, code, timeout, TimeUnit.MINUTES);
        return code;
    }

}




