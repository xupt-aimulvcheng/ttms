package com.xupt.ttms.handler;

import com.xupt.ttms.config.exception.CkException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// 验证码的防抖处理
public class CodeHandler {
    private static final long WAIT_TIME = 60 * 1000L; // 等待时间，单位为毫秒
    private static final int MAX_CAPACITY = 10000; // 线程池最大容量

    private static ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CAPACITY);

    // 记录上一次请求的时间
    private static long lastRequestTime = 0L;

    public static void handle(Runnable task) {
        long currentTime = System.currentTimeMillis();
        // 如果距离上一次请求时间小于等于等待时间，则视为重复请求，不进行处理
        if (currentTime - lastRequestTime <= WAIT_TIME) {
            throw new CkException(20001,"验证码已发送，请勿重复发送");
        }
        lastRequestTime = currentTime;
        threadPool.execute(task); // 提交请求到线程池进行处理
    }
}

