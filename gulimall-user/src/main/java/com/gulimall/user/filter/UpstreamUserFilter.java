package com.gulimall.user.filter;

import com.gulimall.common.utils.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(0)
public class UpstreamUserFilter extends OncePerRequestFilter {
    public UpstreamUserFilter(){
        System.out.println("用户服务 - UpstreamUserFilter 已实例化");
    }
    // 白名单：不需要处理的路径（登录/注册等）
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        boolean skip = path.startsWith("/user/login")
                || path.startsWith("/user/register")
                || path.startsWith("/user/send-code");
        System.out.println("用户服务过滤器 - 路径：" + path + "，是否跳过：" + skip);
        return skip;
    }
    // 核心逻辑：从请求头读X-User-Id，设置到UserContext
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        try {
            // 1. 从请求头获取网关传递的X-User-Id
            String userIdStr = request.getHeader("X-User-Id");
            System.out.println("用户服务过滤器 - 收到X-User-Id：" + userIdStr);
            // 2. 非空且格式正确时，设置到UserContext
            if(userIdStr!=null && !userIdStr.isEmpty()){
                try {
                    Long userId = Long.valueOf(userIdStr);
                    UserContext.setUser_ID_HOLDER(userId);
                    System.out.println("用户服务过滤器 - UserContext已设置：" + userId);
                } catch (NumberFormatException e) {
                    System.out.println("用户服务过滤器 - X-User-Id格式错误：" + userIdStr);
                    UserContext.clear();
                }
            } else {
                System.out.println("用户服务过滤器 - X-User-Id为空");
                UserContext.clear();
            }
            // 3. 放行到控制器（比如/me接口）
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 4. 请求结束后清理，防止线程复用导致数据混乱
            UserContext.clear();
            System.out.println("用户服务过滤器 - 清理UserContext");
        }
    }
}
