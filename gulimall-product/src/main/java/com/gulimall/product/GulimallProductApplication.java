package com.gulimall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication(scanBasePackages = "com.gulimall")
@ComponentScan(basePackages = {
        "com.gulimall.product",  // 业务模块自身的包
        "com.gulimall.common"    // 公共模块的包（包含 RedisConfig）
})
public class GulimallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }
}
