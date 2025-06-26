package com.gulimall.common.exception;

import com.gulimall.common.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 自动捕获项目中未被 try-catch 的异常，统一返回格式
 */
@RestControllerAdvice // 标记该类为全局异常处理类（所有 Controller 的异常都会经过它）
public class GlobalExceptionHandler {

    /**
     * 处理我们自定义的 BizException
     * @param e 捕获到的异常对象
     * @return 统一封装的响应结果 R
     */
    @ExceptionHandler(BizException.class) // 处理指定类型的异常
    public R handleBizException(BizException e) {
        return R.error()
                .put("code", e.getCode())
                .put("message", e.getMessage());
    }

    /**
     * 兜底异常处理（处理其它未捕获的异常）
     * @param e 异常对象
     * @return 返回通用错误提示
     */
    @ExceptionHandler(Exception.class) // 捕获所有其它异常
    public R handleOtherException(Exception e) {
        return R.error()
                .put("message", "系统异常: " + e.getMessage());
    }
}
