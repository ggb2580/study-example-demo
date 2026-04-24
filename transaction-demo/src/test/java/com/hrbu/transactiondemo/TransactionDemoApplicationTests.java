package com.hrbu.transactiondemo;

import com.hrbu.transactiondemo.service.UserServiceA;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionDemoApplicationTests {
    @Autowired
    private UserServiceA userServiceA;
    @Test
    public void test01(){
        userServiceA.test01();
    }

}
