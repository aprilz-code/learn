package com.macro.mall.tiny.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Classname MinioProperties
 * @Description TODO
 * @Date 2022/3/20 18:26
 * @Created by white
 */
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accesskey;
    /**
     * 密码
     */
    private String secretkey;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }
}
