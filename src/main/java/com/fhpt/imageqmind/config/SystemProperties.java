package com.fhpt.imageqmind.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统相关配置
 * @author Marty
 */
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "system")
@Data
public class SystemProperties {
    private String trainingAddress;
    private String dataSetDir;
    private String modelDir;
    private int syncNum;
}
