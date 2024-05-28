package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 用户注册
 */
public final class RegisterApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "system/user/register";
    }

    /**
     * 参数提交类型
     */
    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

    /** 手机号 */
    private String phonenumber;
    /** 年龄 */
    private Integer age;
    /** 性别 */
    private String sex;
    /** 职业 */
    private String profession;
    /** 密码 */
    private String password;

    private String userName;

    public RegisterApi setPhone(String phone) {
        this.phonenumber = phone;
        return this;
    }

    public RegisterApi setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public RegisterApi setAge(Integer age) {
        this.age = age;
        return this;
    }

    public RegisterApi setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    public RegisterApi setPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterApi setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public final static class Bean {

    }
}