package com.shopcache.demo.controller;

import com.shopcache.demo.entity.User;
import com.shopcache.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Say my name
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/query/{id}")
    public User queryById(@PathVariable("id") Integer id) throws InterruptedException {
        return userService.queryById(id);
    }
}
