package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.Date;

/**
 * 旅游攻略list
 * @author flight
 */
public final class TravelGuideListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "system/guide/list";
    }

    private int pageNumber = 1;
    private int pageSize = 10;

    public int getPageNumber() {
        return pageNumber;
    }

    public TravelGuideListApi setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public TravelGuideListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {
        /** 旅游攻略的唯一标识符 */
        private Long id;

        /** 旅游攻略的标题 */
        private String title;

        /** 旅游攻略的内容 */
        private String content;

        /**
         * 攻略缩略图
         */
        private String imageUrl;

        /** 标识发布攻略的用户 */
        private Long userId;

        /** 标识相关景点 */
        private Long scenicSpotId;

        /** 攻略的创建时间 */
        private Date createdAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getScenicSpotId() {
            return scenicSpotId;
        }

        public void setScenicSpotId(Long scenicSpotId) {
            this.scenicSpotId = scenicSpotId;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}