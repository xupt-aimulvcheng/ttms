package com.xupt.ttms.config;

import com.xupt.ttms.interceptor.LoginInterceptor;
import com.xupt.ttms.interceptor.RedisInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class UserConfig implements WebMvcConfigurer {
    @Resource
    private RedisInterceptor redisInterceptor;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(stringRedisTemplate))
                .addPathPatterns("/**")      //拦截请求
                .excludePathPatterns("/","/user/login","/register","/forget-pw","/user/checkUserName/{username}","/user/register","/user/forgetPassword","/main/**","/captcha","/captchaCommon","/alipay/alipay","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/user/sendCode"); //放行请求
//        registry.addInterceptor(redisInterceptor)
//                .addPathPatterns("/**")     //拦截请求
//                .excludePathPatterns("/","/user/login","/register","/forget-pw","/user/checkUserName/{username}","/user/register","/user/forgetPassword","/main/**","/captcha","/captchaCommon","/alipay/alipay","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/user/sendCode"); //放行请求
    }
}
