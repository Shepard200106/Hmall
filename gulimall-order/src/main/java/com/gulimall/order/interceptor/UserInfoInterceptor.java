package com.gulimall.order.interceptor;

import com.gulimall.common.utils.UserContext;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商品服务的用户信息拦截器：从请求头获取用户ID，存入ThreadLocal
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    // 请求处理前执行：获取用户ID并存储
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        // 从网关传递的请求头中获取用户ID（X-User-Id是网关统一设置的）
        String userIdStr = request.getHeader("X-User-Id");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            try {
                // 转换为Long类型，存入UserContext（ThreadLocal）
                Long userId = Long.parseLong(userIdStr);
                UserContext.setUser_ID_HOLDER(userId);
            } catch (NumberFormatException e) {
                // 如果格式错误，忽略（可能是非登录用户，userId为null）
            }
        }
        // 无论是否获取到用户ID，都放行请求
        return true;
    }

    // 请求处理完成后执行：清除ThreadLocal，防止内存泄漏
    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            Exception ex
    ) {
        UserContext.clear();
    }
}