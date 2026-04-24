package com.hrbu.cachedemo.controller;

import com.hrbu.cachedemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/order")
    public String order() throws InterruptedException {
        Long start = System.currentTimeMillis();
        String address = orderService.getAddress();
        System.out.println(address);
        Long end = System.currentTimeMillis();
        return (end - start)+"ms";
    }
}
