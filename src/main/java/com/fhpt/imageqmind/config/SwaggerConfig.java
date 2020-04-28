package com.fhpt.imageqmind.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * @author Marty
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fhpt.imageqmind.controller"))
                .paths(PathSelectors.any())
                .build()
                .groupName("开发三部");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ImageQMind语义建模中台接口文档")
                .description("一款在线ImageQMind语义建模中台接口文档，通过此工具方便前端开发人员调试=.=")
                .termsOfServiceUrl("http://10.95.130.115:8081")
                .contact("Marty")
                .version("1.0")
                .build();
    }
}
