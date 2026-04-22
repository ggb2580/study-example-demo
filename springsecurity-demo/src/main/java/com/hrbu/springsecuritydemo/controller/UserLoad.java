package com.hrbu.springsecuritydemo.controller;

import com.hrbu.springsecuritydemo.entity.User;
import com.hrbu.springsecuritydemo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoad implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserLoad.class);
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.login(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        log.info("===============");
        log.info("用户信息："+user.getUserName()+"密码："+user.getPassword()+"角色："+user.getRole());
        //返回给SpringSecurity 自动校验密码
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
