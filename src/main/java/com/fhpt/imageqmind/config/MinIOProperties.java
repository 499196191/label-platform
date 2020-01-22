package com.fhpt.imageqmind.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * minIO属性配置
 * @author Marty
 */
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "minio")
@Data
public class MinIOProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
