package com.gulimall.user.interceptor;

import com.gulimall.common.utils.JwtUtils;
import com.gulimall.common.utils.UserContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器：解析 token，将用户信息注入 ThreadLocal
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){


        // 1. 获取请求头中的 token
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){

            response.setStatus(401);
            return false;
        }

        // 2. 提取 token
        String token = authHeader.replace("Bearer ", "");

        try {
            // 3. 解析 userId
            Long userId = JwtUtils.getUserId(token);
            // 4. 存入 ThreadLocal
            UserContext.setUser_ID_HOLDER(userId);
        }catch (Exception e){
            // token 非法或过期
            response.setStatus(401);
            return false;
        }
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler,Exception ex) {
        // 请求完成后清除 ThreadLocal
        UserContext.clear();
    }
}
