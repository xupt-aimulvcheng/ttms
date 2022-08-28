package com.xupt.ttms.controller;

import com.xupt.ttms.pojo.Code;
import com.xupt.ttms.pojo.User;
import com.xupt.ttms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/userServlet")
@Controller
public class UserServlet extends BaseServlet {
    private String message;
    @Autowired
    private UserService userService;
    @Autowired
    private Code code;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Code login(@RequestBody User user2) {
        String username = user2.getUsername();
        String password = user2.getPassword();
        System.out.println(username + " " + password);
        User user = userService.getUserByUsername(username);
        String error = "";
        if (user == null) {
            error = "用户不存在";
            code.setInfo(error);
            return code;
        }
        User user1 = userService.getUserByUsernameAndPwd(username, password);
        if (user1 == null) {
            error = "密码错误";
        }
        if (user1 != null) {
            error = "登陆成功";
        }
        code.setInfo(error);
        return code;
    }

    @RequestMapping(value = "/user/checkUserName", method = RequestMethod.POST)
    @ResponseBody
    public Code checkUserName(@RequestBody String username) {
        String error = "";
        if (userService.getUserByUsername(username) != null) {
            error += "用户名已存在";
        }
        code.setInfo(error);
        return code;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public Code register(@RequestBody User user) {
        int i = userService.register(user);
        if (i == 1) {
            code.setInfo("注册成功");
            return code;
        }
        code.setInfo("");
        return code;
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
    }

    @RequestMapping(value = "/user/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Code forgetPassword(@RequestBody User user) {
        int i = userService.updateUserByUsernameAndPhone(user.getUsername(), user.getPassword(), user.getPhone());
        if (i < 1) {
            code.setInfo("预留手机号输入有误，请重新输入");
            return code;
        } else {
            code.setInfo("修改成功");
            return code;
        }
    }

}