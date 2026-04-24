package com.hrbu.cachedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheDemoApplicationTests {
    @Value("${custom.message}")
    private String message;
    @Test
    void contextLoads() {
        System.out.println(message);
    }

}
