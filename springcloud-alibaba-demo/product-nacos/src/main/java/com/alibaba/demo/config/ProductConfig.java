package com.alibaba.demo.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Say my name
 */
@Configuration
public class ProductConfig {
    @Bean
    Request.Options options(){
        return new Request.Options(5000,10000);
    }
}
