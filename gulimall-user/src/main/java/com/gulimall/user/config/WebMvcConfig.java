package com.gulimall.user.config;

import com.gulimall.user.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类：注册拦截器
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        System.out.println("✅ WebMvcConfig 已加载！");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**");
    }
}
