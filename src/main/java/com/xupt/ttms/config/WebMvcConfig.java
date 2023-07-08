package com.xupt.ttms.config;

import com.xupt.ttms.interceptor.DebounceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        DebounceInterceptor debounceInterceptor = new DebounceInterceptor(stringRedisTemplate);
        registry.addInterceptor(debounceInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/user/sendCode","/order/getOrders");
    }
}
