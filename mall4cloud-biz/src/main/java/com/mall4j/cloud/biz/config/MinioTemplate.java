package com.mall4j.cloud.biz.config;

import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ResponseEnum;
import io.minio.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author FrozenWatermelon
 */
@Component
public class MinioTemplate implements InitializingBean {

    @Autowired
    private OssConfig ossConfig;

    private MinioClient minioClient;

    static  final Logger logger = LoggerFactory.getLogger(MinioTemplate.class);

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

    public void uploadMinio(byte[] bytes, String filePath, String contentType) throws IOException {
        InputStream input = null;
        try {
            input = new ByteArrayInputStream(bytes);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(ossConfig.getBucket())
                            .contentType(contentType)
                            .stream(input, input.available(), -1)
                            .object(filePath)
                            .build()
            );
        } catch (Exception e) {
            logger.error("minio上传文件错误：", e);
        } finally {
            if (Objects.nonNull(input)) {
                input.close();
            }
        }
    }
}
