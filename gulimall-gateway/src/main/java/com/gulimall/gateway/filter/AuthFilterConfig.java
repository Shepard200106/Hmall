package com.gulimall.gateway.filter;

import com.gulimall.common.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

/**
 * 网关鉴权过滤器配置类：统一验证token，解析用户信息
 */
@Configuration // 标识这是一个配置类，Spring会自动加载
public class AuthFilterConfig {

    /**
     * 定义一个全局过滤器，处理所有请求
     */
    @Bean
    public OrderedGatewayFilter authFilter() {
        // 这里用OrderedGatewayFilter包装，是为了设置过滤器的执行顺序
        return new OrderedGatewayFilter((exchange, chain) -> {
            // 1. 获取当前请求的路径（比如：/api/user/me 或 /api/order/submit）
            String path = exchange.getRequest().getPath().value();
            System.out.println("当前请求路径：" + path);

            // 2. 排除不需要鉴权的接口（登录、注册、发送验证码）
            // 这些接口即使没登录也能访问
            if (path.contains("/api/user/login")
                    || path.contains("/api/user/register")
                    || path.contains("/api/user/send-code")) {
                System.out.println("公开接口，直接放行：" + path);
                return chain.filter(exchange); // 直接放行
            }

            // 3. 需要鉴权的接口：获取请求头中的token
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            System.out.println("获取到的token：" + token);

            // 4. 验证token格式（必须以Bearer 开头）
            if (token == null || !token.startsWith("Bearer ")) {
                System.out.println("token不存在或格式错误，返回401");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); // 401：未授权
                return exchange.getResponse().setComplete(); // 结束请求
            }

            // 5. 解析token（去掉Bearer 前缀）
            String jwtToken = token.replace("Bearer ", "");
            try {
                // 调用common模块的JwtUtils解析用户ID（这个工具类你已经有了）
                Long userId = JwtUtils.getUserId(jwtToken);
                System.out.println("解析到的用户ID：" + userId);

                // 6. 把用户ID放入请求头，传递给下游服务（比如order、product服务）
                // 下游服务可以通过X-User-Id头获取用户ID
                exchange.getRequest().mutate()
                        .header("X-User-Id", userId.toString())
                        .build();

                // 7. 放行请求到下游服务
                return chain.filter(exchange);
            } catch (Exception e) {
                // 解析失败（token无效或过期）
                System.out.println("token无效或过期，返回401：" + e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }, Ordered.HIGHEST_PRECEDENCE); // 设置最高优先级，确保先执行鉴权


    }
}