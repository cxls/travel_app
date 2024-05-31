package com.hjq.demo.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

import java.math.BigDecimal;

/**
 * 酒店添加
 * @author flight
 */
public final class HotelAddApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "system/hotel";
    }


    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

    /** 酒店名称 */
    private String name;

    /** 酒店地址 */
    private String region;

    /** 酒店的描述 */
    private String description;

    /** 酒店价格 */
    private BigDecimal price;

    /** 缩略图 */
    private String imageUrl;


    public String getName() {
        return name;
    }

    public HotelAddApi setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public HotelAddApi setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HotelAddApi setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public HotelAddApi setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public HotelAddApi setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}