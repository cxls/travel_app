package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

/**
 * 添加个人动态接口
 * @author flightz-
 */
public final class PersonalStatusAddApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "system/status";
    }

    /** 动态内容 */
    private String content;

    /** 发布地址 */
    private String region;

    /** 图片的URL或路径，多张用英文,分隔 */
    private String imageUrls;

    public String getContent() {
        return content;
    }

    public PersonalStatusAddApi setContent(String content) {
        this.content = content;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public PersonalStatusAddApi setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public PersonalStatusAddApi setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    /**
     * 参数提交类型
     */
    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }
}