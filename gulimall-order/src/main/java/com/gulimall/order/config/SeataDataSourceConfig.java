package com.gulimall.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SeataDataSourceConfig {

    // 1. 配置原始数据源（如Druid，根据你的实际数据源修改）
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource") // 读取application.yml中的数据源配置
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    // 2. 用Seata的DataSourceProxy包装原始数据源（核心：生成UndoLog）
    @Primary // 优先使用Seata代理数据源，覆盖默认数据源
    @Bean
    public DataSource dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }
}
