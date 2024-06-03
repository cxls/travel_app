package com.hjq.demo.sqlite.model;

import org.litepal.crud.LitePalSupport;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体
 * @author flight
 * @date 2024/6/2
 */
public class User extends LitePalSupport {
    private Long userId;

    private String sex;

    private String userType;

    private long age;

    private String profession;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
