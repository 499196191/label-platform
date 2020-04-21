package com.fhpt.imageqmind.utils;

import com.fhpt.imageqmind.config.MinIOProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * minIO工具类
 * @author Marty
 */
@Slf4j
public class MinioUtil {

    private static MinioClient minioClient;
    private static MinIOProperties minIOProperties;

    static {
        minIOProperties = (MinIOProperties) SpringContextUtil.getBean("minIOProperties");
        try {
            minioClient = new MinioClient(minIOProperties.getEndpoint(), minIOProperties.getAccessKey(), minIOProperties.getSecretKey());
            boolean found = minioClient.bucketExists(minIOProperties.getBucketName());
            if (found) {
                log.info("检查名称为{}的bucket已经存在，无需创建", minIOProperties.getBucketName());
            } else {
                minioClient.makeBucket(minIOProperties.getBucketName());
                log.info("检查名称为[{}]的bucket不存在，创建成功", minIOProperties.getBucketName());
            }
        } catch (Exception e) {
            log.error("minio服务异常");
            e.printStackTrace();
        }
    }

    public static String putExcel(MultipartFile file){
        StringBuilder objectName = new StringBuilder();
        try {
            String[] temp = file.getOriginalFilename().split("\\.");
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            objectName.append(temp[0]);
            objectName.append(now);
            objectName.append(".");
            objectName.append(temp[1]);
            minioClient.putObject(minIOProperties.getBucketName(), objectName.toString(), file.getInputStream(), file.getInputStream().available(), "application/vnd.ms-excel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectName.toString();
    }

    public static boolean deleteObject(String objectName){
        try {
            minioClient.removeObject(minIOProperties.getBucketName(), objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static InputStream getObject(String objectName){
        try {
            return minioClient.getObject(minIOProperties.getBucketName(), objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
