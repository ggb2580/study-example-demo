package com.hrbu.transactiondemo.service;

import com.hrbu.transactiondemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceA {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceB userServiceB;

    @Transactional(propagation = Propagation.REQUIRED)
    public void test01(){
        int insert = userMapper.insert("insert into user values(13,'GGB','123456','ADMIN',1)");

        try {
            userServiceB.test01();
        }catch (Exception e){
            e.printStackTrace();
        }
        userMapper.insert("insert into user values(14,'GGB','123456','ADMIN',1)");
        throw new RuntimeException("主动抛出异常");

    }

}
