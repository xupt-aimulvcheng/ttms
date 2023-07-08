package com.xupt.ttms.util;/*
 * @author ck
 * @date 2023/4/19
 * @apiNote
 */

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.xupt.ttms.config.enums.RedisKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object value, Long time, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), time, timeUnit);
    }

    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(value));
    }
    public void set(RedisKeyEnum redisKeyEnum, Object value) {
        stringRedisTemplate.opsForValue().set(redisKeyEnum.getKey(), JSONObject.toJSONString(value));
    }
    public void set(RedisKeyEnum redisKeyEnum, Object value, Long time, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(redisKeyEnum.getKey(), JSONObject.toJSONString(value), time, timeUnit);
    }
    //
    public void setWithLogicExpire(String key, Object value, Long time, TimeUnit timeUnit) {
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(timeUnit.toSeconds(time));
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(expireTime);
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(redisData));
    }
    //解决缓存穿透
    //缓存穿透：查询一个一定不存在的数据，这个数据不应该存在于缓存中，但是由于缓存的存在，导致每次都会去缓存中查询，从而导致数据库压力过大
    public <R, ID> R queryWithPasThrough(String key, ID id, Class<R> clazz, Function<ID, R> function, Long time, TimeUnit timeUnit) {
        // 从缓存中获取数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 如果缓存中没有数据
        if (StrUtil.isNotBlank(json)) {
            // 存在直接返回
            return JSONObject.parseObject(json, clazz);
        }
        // 判断是否为空值
        if (json != null) {
            return null;
        }
        // 不存在则从数据库中获取数据
        R apply = function.apply(id);
        // 不存在返回错误
        if (apply == null) {
            stringRedisTemplate.opsForValue().set(key, "", 3, TimeUnit.MINUTES);
            return null;
        }
        this.set(key, apply, time, timeUnit);
        return apply;
    }

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    /**
     * 逻辑过期解决缓存击穿(热点key)
     * @param keyPrefix 键前缀
     * @param id    键
     * @param clazz 值类型
     * @param function  从数据库中获取数据的方法
     * @param time  过期时间
     * @param timeUnit  过期时间单位
     * @return  值
     * @param <R>   值类型
     * @param <ID>  键类型
     */
    //缓存击穿：缓存中的数据过期，重建耗时长，导致大量请求同时访问数据库，从而导致数据库压力过大
    public <R,ID> R queryWithLogicExpire(String keyPrefix, ID id, Class<R> clazz, Function<ID, R> function,Long time, TimeUnit timeUnit) {
        String key = keyPrefix + id;
        // 从缓存中获取数据
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(json)) {
            return null;
        }
        //命中，需要先将数据转换为对象
        RedisData redisData = JSONObject.parseObject(json, RedisData.class);
        R r = JSONUtil.toBean((cn.hutool.json.JSONObject) redisData.getData(), clazz);
        if (redisData.getExpireTime().isAfter(LocalDateTime.now())) {
            return r;
        }
        //过期，需要缓存重建
        String lockKey = key + "_lock";
        boolean isLock = tryLock(lockKey);
        if (isLock) {
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try{
                    R r1 = function.apply(id);
                    this.setWithLogicExpire(key, r1, time, timeUnit);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    releaseLock(lockKey);
                }
            });
        }
        return r;
    }

    private boolean tryLock(String lockKey) {
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(lock);
    }
    private void releaseLock(String lockKey) {
        stringRedisTemplate.delete(lockKey);
    }
}
