package com.xupt.ttms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xupt.ttms.pojo.User;
import com.xupt.ttms.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
* @author 
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-13 17:51:10
*/
public interface UserService extends IService<User> {

    int register(User user);

    Map<String,Object> login(User user);

    UserVo getUser();

    int updateUser(User user, String token);

    int upload(MultipartFile file, String token);

    void sendCode(String user);

    String  checkUsername(String username);
}
