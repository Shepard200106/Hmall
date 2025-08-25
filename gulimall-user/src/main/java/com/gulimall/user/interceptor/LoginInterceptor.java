package com.gulimall.user.interceptor;

import com.gulimall.common.utils.JwtUtils;
import com.gulimall.common.utils.UserContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器：解析 token，将用户信息注入 ThreadLocal
 */
public class LoginInterceptor implements HandlerInterceptor {
    // 文件：gulimall-user/src/main/java/com/gulimall/user/interceptor/LoginInterceptor.java
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 1. 从网关传递的请求头中获取用户ID
        String userIdStr = request.getHeader("X-User-Id");
        if (userIdStr != null) {
            try {
                Long userId = Long.parseLong(userIdStr);
                UserContext.setUser_ID_HOLDER(userId); // 存入ThreadLocal
                return true; // 放行
            } catch (NumberFormatException e) {
                // 忽略格式错误（非登录用户）
            }
        }

        // 2. 若没有X-User-Id，说明可能是内部调用，再尝试解析token（兼容旧逻辑）
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            return false;
        }

        String token = authHeader.replace("Bearer ", "");
        try {
            Long userId = JwtUtils.getUserId(token);
            UserContext.setUser_ID_HOLDER(userId);
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
