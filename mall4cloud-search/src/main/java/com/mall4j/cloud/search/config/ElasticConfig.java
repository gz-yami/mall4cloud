package com.mall4j.cloud.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FrozenWatermelon
 * @date 2020/9/24
 */
@Configuration
public class ElasticConfig {

    @Value("${elastic.address}")
    private String address;

    @Value("${elastic.port}")
    private Integer port;
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(address, port, "http")));
    }
}
