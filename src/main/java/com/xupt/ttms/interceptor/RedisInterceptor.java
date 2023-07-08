package com.xupt.ttms.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RedisInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        Pattern pattern = Pattern.compile("/movie/getMovieById/([0-9]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String id = matcher.group(1);
            stringRedisTemplate.opsForValue().increment("/movieIncrement/" + id);
        }
        return true;
    }
}
