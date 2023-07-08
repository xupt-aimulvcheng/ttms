package com.xupt.ttms.util;/*
 * @author ck
 * @date 2023/4/19
 * @apiNote
 */

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisData {
    private Object data;
    private LocalDateTime expireTime;
}
