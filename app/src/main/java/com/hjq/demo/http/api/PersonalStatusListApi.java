package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.Date;

/**
 * 个人动态接口
 * @author flightz-
 */
public final class PersonalStatusListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "system/status/list";
    }

    private int pageNumber = 1;
    private int pageSize = 10;

    public int getPageNumber() {
        return pageNumber;
    }

    public PersonalStatusListApi setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PersonalStatusListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {
        private Long id;

        /** 用户ID */
        private Long userId;

        /** 动态内容 */
        private String content;

        /** 发布地址 */
        private String region;

        /** 图片的URL或路径，多张用英文,分隔 */
        private String imageUrls;

        /** 动态的创建时间 */
        private String createdAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(String imageUrls) {
            this.imageUrls = imageUrls;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}