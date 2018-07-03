package com.vastio.bean.base;

/**
 * define 接口请求失败后的统一返回数据结构
 *
 * @author xlch
 * @Date 2018-03-02 10:21
 */
public class ApiError {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
