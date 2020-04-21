package com.fhpt.imageqmind;

import com.fhpt.imageqmind.config.MinIOProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 语义建模中台后台启动类
 * @author Marty
 */
@SpringBootApplication
@EntityScan(basePackages = "com.fhpt.imageqmind.domain")
@EnableConfigurationProperties
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class ImageQMindWebApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ImageQMindWebApplication.class);
        app.run(args);
    }
}
