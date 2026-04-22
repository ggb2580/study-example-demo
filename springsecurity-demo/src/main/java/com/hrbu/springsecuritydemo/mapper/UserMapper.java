package com.hrbu.springsecuritydemo.mapper;

import com.hrbu.springsecuritydemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User login(String userName);
}
