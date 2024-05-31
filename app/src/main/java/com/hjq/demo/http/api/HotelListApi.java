package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 旅游攻略list
 * @author flight
 */
public final class HotelListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "system/hotel/list";
    }

    private int pageNumber = 1;
    private int pageSize = 10;

    public int getPageNumber() {
        return pageNumber;
    }

    public HotelListApi setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public HotelListApi setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public final static class Bean {
        private Long id;

        /** 酒店名称 */
        private String name;

        /** 坐标 */
        private String location;

        /** 酒店地址 */
        private String region;

        /** 酒店的描述 */
        private String description;

        /** 酒店价格 */
        private BigDecimal price;

        /** 缩略图 */
        private String imageUrl;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}