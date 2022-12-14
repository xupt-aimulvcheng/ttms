package com.xupt.ttms.util;/*
 * @author:ck
 * @param:
 * @date:2022/7/8
 * @description:
 */

import com.github.pagehelper.PageInfo;
import com.xupt.ttms.pojo.Result;

import java.util.List;
import java.util.UUID;

public class ToResult<T> {
    public static Result getResult(PageInfo info){
        List list = info.getList();
        long total = info.getTotal();
        return Result.success(list,total);
    }

    public static String getUUID() {
        String replaceUUID = UUID.randomUUID().toString().replace("-", "");
        return replaceUUID;
    }
}
