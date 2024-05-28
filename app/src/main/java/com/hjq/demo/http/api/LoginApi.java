package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 用户登录
 */
public final class LoginApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "login";
    }

    /**
     * 参数提交类型
     */
    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

    /** 手机号 */
    private String phone;
    /** 登录密码 */
    private String password;

    private String code;

    private String uuid;

    private String username;

    public LoginApi setPhone(String phone) {
        this.phone = phone;
        this.username = phone;
        return this;
    }

    public LoginApi setPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginApi setCode(String code) {
        this.code = code;
        return this;
    }

    public LoginApi setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public final static class Bean {

        private String token;

        public String getToken() {
            return token;
        }
    }
}