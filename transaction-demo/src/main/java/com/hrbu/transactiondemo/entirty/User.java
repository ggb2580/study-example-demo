package com.hrbu.transactiondemo.entirty;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;
    private String password;
    private String role;
    private Integer status;
}
