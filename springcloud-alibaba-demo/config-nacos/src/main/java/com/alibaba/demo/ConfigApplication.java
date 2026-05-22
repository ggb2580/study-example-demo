package com.alibaba.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Say my name
 */
@SpringBootApplication
public class ConfigApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigApplication.class,args);
        String username = applicationContext.getEnvironment().getProperty("user.name");
        String age = applicationContext.getEnvironment().getProperty("user.age");
        System.out.println("username:"+username+" userAge:"+age);
    }
}
