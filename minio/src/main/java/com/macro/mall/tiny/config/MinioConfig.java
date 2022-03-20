package com.macro.mall.tiny.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Classname MinioConfig
 * @Description TODO
 * @Date 2022/3/20 18:26
 * @Created by white
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {

    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioClient  minioClient(){
        return     MinioClient.builder()
                        .endpoint(minioProperties.getEndpoint())
                        .credentials(minioProperties.getAccesskey(), minioProperties.getSecretkey())
                        .build();
    }
}
