package com.mall4j.cloud.search.config;

import cn.hutool.core.util.StrUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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

    @Value("${elastic.username}")
    private String username;

    @Value("${elastic.password}")
    private String password;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(address, port, "http"));
        // 异步httpclient配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            // 设置es密码
            if (StrUtil.isNotBlank(password)) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }
}
