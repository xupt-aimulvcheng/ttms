package com.xupt.ttms.config.enums;/*
 * @author ck
 * @date 2023/5/14
 * @apiNote
 */

public enum RedisKeyEnum {
    USER("user:"),
    CODE("verification_code:"),
    EMAIL("security_code:"),
    MOVIEPY("movie_type:"),//电影类型
    ORDER("order:");
    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
