package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;

import lombok.Data;

/**
 * 获取验证码图片
 * @author flight
 */
public final class CaptchaImageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "captchaImage";
    }

    public final static class Bean {
        private String img;
        private String uuid;
    }
}