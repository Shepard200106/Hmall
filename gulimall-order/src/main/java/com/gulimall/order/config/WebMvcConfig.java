package com.gulimall.order.config;


import com.gulimall.order.interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 商品服务的Web配置：注册拦截器
 */
@Configuration // 告诉Spring这是配置类
public class WebMvcConfig implements WebMvcConfigurer {

    // 注册用户信息拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加我们刚才创建的拦截器，并拦截所有请求
        registry.addInterceptor(new UserInfoInterceptor())
                .addPathPatterns("/**"); // /** 表示拦截所有路径
    }
}
