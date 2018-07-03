package com.vastio.bean.base;

import java.util.List;

/**
 * define 接口请求成功后的统一返回数据结构
 *
 * @author xlch
 * @Date 2018-03-02 10:20
 */
public class ApiResponse<T> {

    private int code;
    private int total;
    private List<T> results;
    private String message = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
