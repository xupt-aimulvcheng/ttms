package com.xupt.ttms.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class DebounceInterceptor extends HandlerInterceptorAdapter {
    private static final String REDIS_KEY_PREFIX = "DEBOUNCE:";
    private static final long TIMEOUT = 1000L;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private StringRedisTemplate stringRedisTemplate;
    public DebounceInterceptor(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String ip = request.getRemoteAddr();
//        String key = ip + REDIS_KEY_PREFIX + request.getRequestURI() + ":" + request.getMethod() + ":";
//        String value = stringRedisTemplate.opsForValue().get(key);
//        log.info("key: {}, value: {}", key, value);
//        if (StringUtils.isNotBlank(value)) {
//            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//            log.info("ip地址为{}的请求次数过多",ip);
//            //设置UTF-8编码
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write("短时间内请求过多。请稍后再试。");
//            //设置响应的code为20001
//            response.setHeader("code", "20001");
//            return false;
//        } else {
//            stringRedisTemplate.opsForValue().set(key, "1", TIMEOUT, TimeUnit.MILLISECONDS);
//            executorService.submit(() -> {
//                // 处理请求
//            });
//            return true;
//        }
        return true;
    }
}
