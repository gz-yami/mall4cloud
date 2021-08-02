package com.mall4j.cloud.biz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FrozenWatermelon
 * @date 2020/9/10
 */
@RefreshScope
@Configuration
public class OssConfig {

    @Value("${biz.oss.endpoint}")
    private String endpoint;
    @Value("${biz.oss.bucket}")
    private String bucket;
    @Value("${biz.oss.access-key-id}")
    private String accessKeyId;
    @Value("${biz.oss.access-key-secret}")
    private String accessKeySecret;
    @Value("${biz.oss.type}")
    private Integer ossType;

    /**
     * 最大上传长度单位m，默认20M
     */
    @Value("${biz.oss.maxLength:20}")
    private Integer maxLength;

    public String getAccessId() {
        return accessKeyId;
    }

    public String getBucket() {
        return bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getOssType() {
        return ossType;
    }

    public void setOssType(Integer ossType) {
        this.ossType = ossType;
    }
}
