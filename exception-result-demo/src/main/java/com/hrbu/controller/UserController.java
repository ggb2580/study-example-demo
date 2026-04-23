package com.hrbu.controller;

import com.hrbu.common.Result;
import com.hrbu.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Say my name
 */
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {
    @PostMapping("/create")
    public Result<String> createUser(@RequestBody @Validated User user){
        //校验通过，才执行到这里
        return Result.success("用户创建成功",null);
    }
}
