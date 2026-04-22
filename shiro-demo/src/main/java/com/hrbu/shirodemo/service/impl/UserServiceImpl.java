package com.hrbu.shirodemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrbu.shirodemo.entity.User;
import com.hrbu.shirodemo.mapper.UserMapper;
import com.hrbu.shirodemo.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
