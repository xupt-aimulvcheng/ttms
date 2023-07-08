package com.xupt.ttms.interceptor;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xupt.ttms.pojo.User;
import com.xupt.ttms.util.ThreadUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;
    public LoginInterceptor(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }
    /**
     * 目标方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StrUtil.isBlankIfStr(token)){
            return false;
        }
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(token);
        if (userMap.isEmpty()){
            return false;
        }
        User user = BeanUtil.fillBeanWithMap(userMap,new User(),false);
        ThreadUtils.put(user);
        stringRedisTemplate.expire(token,120l, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadUtils.remove();
    }
}
