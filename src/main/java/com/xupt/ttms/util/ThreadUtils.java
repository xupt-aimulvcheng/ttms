package com.xupt.ttms.util;


import com.xupt.ttms.pojo.User;

public class ThreadUtils<T> {
    private static ThreadLocal<User> user = new ThreadLocal<>();

    private ThreadUtils() {
    }

    public static Long getUserId() {
        User baseUserDTO = getUser();
        return baseUserDTO.getId();
    }

    public static User getUser() {
        User baseUser = user.get();
        if (null == baseUser) {
            throw new RuntimeException("登录失效，请重新登录！");
        }
        return baseUser;
    }



    public static void put(User baseUser) {
        user.set(baseUser);
    }

    public static void remove() {
        user.remove();
    }
}
