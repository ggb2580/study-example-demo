package com.hrbu.cachedemo.service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Say my name
 */
@Service
public class OrderService {
    @Cacheable(value = "address", key = "'default'")
    public String getAddress() throws InterruptedException {
        System.out.println("进入真正的方法里");
        //模拟延迟
        Thread.sleep(120);

        return "深圳龙岗";
    }
}
