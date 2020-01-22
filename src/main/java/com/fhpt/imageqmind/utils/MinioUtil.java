package com.fhpt.imageqmind.utils;

import com.fhpt.imageqmind.config.MinIOProperties;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

/**
 * minIO工具类
 * @author Marty
 */
public class MinioUtil {

    private static MinioClient minioClient;

    static {
        MinIOProperties minIOProperties = (MinIOProperties)SpringContextUtil.getBean("minIOProperties");
        try {
            minioClient = new MinioClient(minIOProperties.getEndpoint(), minIOProperties.getAccessKey(), minIOProperties.getSecretKey());
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
        }
    }



}
