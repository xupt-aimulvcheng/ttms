package com.xupt.ttms.util;/*
 * @author:ck
 * @param:
 * @date:2022/7/10
 * @description:
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class JSONUtil extends HttpServlet {
    public static String toString(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuffer stringBuffer=new StringBuffer("");
        BufferedReader bufferedReader = req.getReader();
        String str=null;
        while ((str=bufferedReader.readLine())!=null){
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }
    public static Map deleteAction(String json){
        Map map=(Map) JSON.parse(json);
        map.remove("action");
        return map;
    }
}
