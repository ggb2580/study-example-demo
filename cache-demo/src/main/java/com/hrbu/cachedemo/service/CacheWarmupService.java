package com.hrbu.cachedemo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Say my name
 */
@Service
public class CacheWarmupService {
    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void cacheWarmup() throws InterruptedException {
        System.out.println("缓存预热");
        orderService.getAddress();
    }
}
