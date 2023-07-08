package com.xupt.ttms.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @TableName user
 */
@TableName(value ="user")
public class User implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private String sex;

    /**
     *
     */
    private Double balance;

    /**
     *
     */
    private String phone;
    private String email;

    /**
     *
     */
    private String img;

    /**
     * 是否逻辑删除
     */
    @TableLogic
    private Integer isDeleted;

    /**
     *
     */
    private String nickname;

    /**
     *
     */
    private String introduction;

    /**
     *
     */
    private String city;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     */
    public String getSex() {
        return sex;
    }

    /**
     *
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     *
     */
    public Double getBalance() {
        return balance;
    }

    /**
     *
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     */
    public String getImg() {
        return img;
    }

    /**
     *
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 是否逻辑删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否逻辑删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     *
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     *
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     *
     */
    public String getCity() {
        return city;
    }

    /**
     *
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", balance=" + balance +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", img='" + img + '\'' +
                ", isDeleted=" + isDeleted +
                ", nickname='" + nickname + '\'' +
                ", introduction='" + introduction + '\'' +
                ", city='" + city + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(sex, user.sex)) return false;
        if (!Objects.equals(balance, user.balance)) return false;
        if (!Objects.equals(phone, user.phone)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(img, user.img)) return false;
        if (!Objects.equals(isDeleted, user.isDeleted)) return false;
        if (!Objects.equals(nickname, user.nickname)) return false;
        if (!Objects.equals(introduction, user.introduction)) return false;
        if (!Objects.equals(city, user.city)) return false;
        return Objects.equals(createTime, user.createTime);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
