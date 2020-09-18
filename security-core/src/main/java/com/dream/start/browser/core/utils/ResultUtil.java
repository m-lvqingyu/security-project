package com.dream.start.browser.core.utils;

import lombok.Data;

/**
 * @description: <pre>
 * </pre>
 * @author: Lvqingyu
 * @create: 2020/09/18 14:29
 */
@Data
public class ResultUtil<T> {

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private T data;


    public ResultUtil(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    public static <T> ResultUtil<T> success(T data) {
        return new ResultUtil<T>(0, "success", data);
    }

    public static <T> ResultUtil<T> failure(String message) {
        return new ResultUtil<T>(-1, message, null);
    }

    public static <T> ResultUtil<T> failure(String message, T data) {
        return new ResultUtil<T>(-1, message, data);
    }

}
