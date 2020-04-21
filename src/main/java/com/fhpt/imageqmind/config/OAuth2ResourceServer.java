package com.fhpt.imageqmind.config;

/**
 * OAuth2
 * @author Marty
 */
//@Configuration
//@EnableWebSecurity
//public class OAuth2ResourceServer extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
//        //让Spring security放行所有pre request
//        registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
//        http.authorizeRequests().antMatchers(
//                "/dataSet/**"
//        ).authenticated();
//        http.oauth2ResourceServer().jwt();
//        http.cors();
//    }
//}
