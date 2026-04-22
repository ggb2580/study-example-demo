package com.hrbu.springsecuritydemo;

import com.hrbu.springsecuritydemo.entity.User;
import com.hrbu.springsecuritydemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringsecurityDemoApplicationTests {
@Autowired
private UserMapper userMapper;
    @Test
    void contextLoads() {
        User root = userMapper.login("root");
        System.out.println(root.getRole());
    }
    @Test
    void generatePassword(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 生成 123456 正确的加密密码！
        String pwd = encoder.encode("123456");
        System.out.println(pwd);
    }

}
