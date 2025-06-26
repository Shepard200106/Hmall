package com.gulimall.product.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// 标注这是一个配置类，Spring 启动时会加载里面的 @Bean 方法
@Configuration
public class ElasticSearchConfig {

    // 创建并注册 RestClient 对象，用于底层 HTTP 通信
    @Bean
    public RestClient restClient() {
        // 使用 HttpHost 指定连接的 Elasticsearch 节点地址（这里是本地 localhost:9200，使用 http 协议）
        return RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();// 构建 RestClient 实例
    }

    // 创建并注册 ElasticsearchClient 对象，是 Elasticsearch Java 客户端的核心接口
    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient){
        // 将 RestClient 封装为 ElasticsearchTransport 对象，使用 Jackson 作为 JSON 编解码器
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,// 底层 HTTP 客户端
                new JacksonJsonpMapper()// JSON 解析器（基于 Jackson）
        );
        // 使用传输层创建 Elasticsearch 客户端
        return new ElasticsearchClient(transport);
    }
}
