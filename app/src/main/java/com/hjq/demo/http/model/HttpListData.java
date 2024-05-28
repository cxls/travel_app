package com.hjq.demo.http.model;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2020/10/07
 *    desc   : 统一接口列表数据结构
 */
public class HttpListData<T> {
    /** 返回码 */
    private int code;
    /** 提示语 */
    private String msg;
    /** 数据 */
    private List<T> rows;

    /**
     * 当前页
     */
    private int pageNumber;

    /**
     * 页大小
     */
    private int pageSize;

    private boolean isLastPage;


    /**
     * 总条数
     */
    private int total;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public List<T> getRows() {
        return rows;
    }

    public int getTotal() {
        return total;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isLastPage() {
        return isLastPage;
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