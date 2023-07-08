package com.xupt.ttms.vo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
/**
 * 返回到前端数据
 */
public class UserVo implements Serializable {
    //q:爱慕旅程转化为ascii码
    //a:97
    private static final long serialVersionUID = 7417973183690191529L;
    private Integer id;
    private String username;
    private String nickname;
    private String img;
    private String sex;
    private String phone;
    private String city;
    private String introduction;
    private boolean bought; //用户是否买票

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
