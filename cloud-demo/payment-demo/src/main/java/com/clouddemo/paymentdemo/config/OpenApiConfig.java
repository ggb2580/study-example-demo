package com.clouddemo.paymentdemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Say my name
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring-Cloud 入门案例 Swagger 教学")
                        .description("我的自定义风格: http://saymyname.com/")
                        .termsOfService("http://UserService.com/")
                        .version("0.0.1"));
    }
}
