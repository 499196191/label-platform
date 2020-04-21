package com.fhpt.imageqmind.config;

import com.fhpt.imageqmind.filters.LabelFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;



import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import org.springframework.web.filter.CorsFilter;



import java.util.Arrays;

/**
 * SpringBean配置
 * @author Marty
 */
@Configuration
public class SpringBeanConfig {

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new LabelFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return filterRegistrationBean;
//    }

    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        config.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        config.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        config.setMaxAge(10000L);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
