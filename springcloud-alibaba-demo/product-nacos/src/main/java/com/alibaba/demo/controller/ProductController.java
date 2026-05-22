package com.alibaba.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Say my name
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/{id}")
    public String reduce(@PathVariable("id") Integer id){
        System.out.println("查询商品"+id);
        return "查询商品"+id+"::::"+port;
    }

}
