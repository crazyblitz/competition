package com.ley.innovation.contest.configuration;

import com.ley.innovation.contest.utils.SwaggerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 configuration
 **/
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket docket() {
        return new SwaggerUtils().initDocket();
    }
}
