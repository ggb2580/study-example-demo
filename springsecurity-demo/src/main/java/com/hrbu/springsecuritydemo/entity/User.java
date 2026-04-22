package com.hrbu.springsecuritydemo.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class User {

    private Long id;
    private String userName;
    private String password;
    private String role;
}
