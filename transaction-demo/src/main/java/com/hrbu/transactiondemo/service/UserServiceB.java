package com.hrbu.transactiondemo.service;

import com.hrbu.transactiondemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceB {
    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.NESTED)
    public void test01(){
        int insert = userMapper.insert("insert into user values(12,'GGB','123456','ADMIN',1)");
//        throw new RuntimeException("主动抛出异常");
    }
}
