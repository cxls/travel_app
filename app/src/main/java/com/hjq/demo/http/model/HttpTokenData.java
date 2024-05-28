package com.hjq.demo.http.model;

/**
 *
 * @author flight
 */
public class HttpTokenData {

    /** 返回码 */
    private int code;
    /** 提示语 */
    private String msg;
    /** 数据 */
    private String token;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public String getToken() {
        return token;
    }

    /**
     * 是否请求成功
     */
    public boolean isRequestSucceed() {
        return code == 200;
    }

    /**
     * 是否 Token 失效
     */
    public boolean isTokenFailure() {
        return code == 1001;
    }
}