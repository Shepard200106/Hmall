package com.gulimall.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用响应结果封装类
 * 用于后端接口统一返回格式，便于前端处理和调试
 */
public class R {
    // 响应状态码，0 表示成功，1 表示失败
    private Integer code;

    // 响应消息
    private String message;

    // 返回数据，通常是一个 JSON 对象，用 Map 表示 key-value 数据
    private Map<String, Object> data = new HashMap<>();

    // 快捷方法：返回成功响应
    public static R ok() {
        R r = new R();
        r.setCode(0); // 成功状态码
        r.setMessage("success");
        return r;
    }

    // 快捷方法：返回失败响应
    public static R error() {
        R r = new R();
        r.setCode(1); // 失败状态码
        r.setMessage("error");
        return r;
    }

    // 给 data 添加 key-value，支持链式调用
    public R put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    // Getter 和 Setter 方法
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
}
