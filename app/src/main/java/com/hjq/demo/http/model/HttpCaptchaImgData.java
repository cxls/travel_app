package com.hjq.demo.http.model;

/**
 *
 * @author flight
 */
public class HttpCaptchaImgData {

    /** 返回码 */
    private int code;
    /** 提示语 */
    private String msg;
    /** 数据 */
    private Boolean captchaEnabled;

    private String img;

    private String uuid;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }


    public Boolean getCaptchaEnabled() {
        return captchaEnabled;
    }

    public String getImg() {
        return img;
    }

    public String getUuid() {
        return uuid;
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