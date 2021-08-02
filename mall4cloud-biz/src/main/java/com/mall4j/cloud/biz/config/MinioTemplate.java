package com.mall4j.cloud.biz.config;

import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ResponseEnum;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author FrozenWatermelon
 */
@Component
public class MinioTemplate implements InitializingBean {

    @Autowired
    private OssConfig ossConfig;

    private MinioClient minioClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.minioClient =  MinioClient.builder().endpoint(ossConfig.getEndpoint())
                .credentials(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret())
                .build();
    }


    /**
     * 删除文件
     *
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    public void removeObject(String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().object(objectName).bucket(ossConfig.getBucket()).build());
    }

    /**
     * 获得上传的URL
     * @param objectName
     */
    public String getPresignedObjectUrl(String objectName){
        try {
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(ossConfig.getBucket()).object(objectName).expiry(10, TimeUnit.MINUTES).method(Method.PUT).build());
            return presignedObjectUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }
    }
}
