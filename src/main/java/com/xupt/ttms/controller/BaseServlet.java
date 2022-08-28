package com.xupt.ttms.controller;

import com.alibaba.fastjson.JSON;
import com.xupt.ttms.pojo.Action;
import com.xupt.ttms.util.JSONUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Method;

public  class BaseServlet<T> extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String action = null;
        if (req.getParameter("action") != null) {
            action = req.getParameter("action");
        }
        else {
            String jsonStr = JSONUtil.toString(req, resp);
            Action action1 = JSON.parseObject(jsonStr, Action.class);
            action=action1.getAction();
        }
        try {
            // 获取action业务鉴别字符串，获取相应的业务 方法反射对象
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
//            System.out.println(method);
            // 调用目标业务 方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}