package com.gulimall.common.exception;

import lombok.Getter;

/**
 * 自定义业务异常类
 * 用于手动抛出业务异常，并设置自定义错误码和提示信息
 */
@Getter
public class BizException extends RuntimeException {
    // 提供获取 code 的方法
    // 错误码（配合返回结构使用）
    private final Integer code;

    // 构造方法，接收错误码和错误信息
    public BizException(Integer code, String message) {
        super(message); // 调用父类构造，设置异常信息
        this.code = code;
    }

}
