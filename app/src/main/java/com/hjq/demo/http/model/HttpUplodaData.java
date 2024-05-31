package com.hjq.demo.http.model;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 统一接口数据结构
 */
public class HttpUplodaData {

    /** 返回码 */
    private int code;
    /** 提示语 */
    private String msg;
    /** 数据 */
    private String fileName;
    private String newFileName;
    private String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
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