package com.fhpt.imageqmind.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Jpa配置
 * @author Marty
 */
@Configuration
@EnableJpaRepositories("com.fhpt.imageqmind.repository")
public class JpaConfiguration {

}
