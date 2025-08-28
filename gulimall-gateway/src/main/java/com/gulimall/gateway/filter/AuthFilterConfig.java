package com.gulimall.gateway.filter;

import com.gulimall.common.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class AuthFilterConfig {

    public AuthFilterConfig() {
        System.out.println("AuthFilterConfig 已被Spring实例化！");
    }

    // 直接定义为GlobalFilter Bean
    @Bean
    public GlobalFilter authFilter() {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();
            System.out.println("【AuthFilter】拦截到请求路径：" + path); // 重点日志

            // 白名单逻辑
            if (path.contains("/user/login")
                    || path.contains("/user/register")
                    || path.contains("/user/send-code")) {
                System.out.println("【AuthFilter】公开接口，放行：" + path);
                return chain.filter(exchange);
            }

            // 其他鉴权逻辑（保持不变）
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            System.out.println("【AuthFilter】获取到token：" + token);

            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            // 关键步骤1：解析token（去除Bearer 前缀）
            String jwtToken = token.replace("Bearer","");
            try {
                // 关键步骤2：用JwtUtils解析出userId（确保JwtUtils引入正确）
                Long userId = JwtUtils.getUserId(jwtToken);
                System.out.println("【AuthFilter】解析到userId：" + userId);
                // 关键步骤3：将userId设置到X-User-Id请求头，传递给下游服务
                ServerWebExchange newExchange = exchange.mutate()
                        // 使用 Consumer 配置请求头
                        .request(r -> r.header("X-User-Id", userId.toString()))
                        .build();
                return chain.filter(newExchange);
            }catch (Exception e) {
                // token无效或过期
                System.out.println("【AuthFilter】token解析失败：" + e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            // 后续token解析和转发逻辑...
        };
    }

    // 单独定义过滤器顺序（必须与上面的filter Bean配合）
    @Bean
    public Ordered authFilterOrder() {
        return () -> Ordered.HIGHEST_PRECEDENCE; // 最高优先级
    }
}