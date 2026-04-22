package com.hrbu.shirodemo;

import com.hrbu.shirodemo.entity.User;
import com.hrbu.shirodemo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroDemoApplicationTests {
    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
        User one = userService.query().eq("user_name", "user").one();
        System.out.println(one);
    }

}
