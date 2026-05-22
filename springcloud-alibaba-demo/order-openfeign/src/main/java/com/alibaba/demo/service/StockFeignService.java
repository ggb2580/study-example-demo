package com.alibaba.demo.service;

/**
 * @author Say my name
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "stock-service",path = "/stock")
public interface StockFeignService {
    @RequestMapping("/reduce")
    public String reduce();
}
