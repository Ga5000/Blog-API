package com.ga5000.api.blog.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfig {

    @Value(value = "${minio.url}")
    private String url;

    @Value(value = "${minio.accessKey}")
    private String accessKey;

    @Value(value = "${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}