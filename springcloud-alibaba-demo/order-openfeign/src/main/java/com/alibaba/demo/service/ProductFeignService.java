package com.alibaba.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Say my name
 */
@FeignClient(name = "product-service",path = "/product")
public interface ProductFeignService {
    @RequestMapping("/{id}")
    public String get(@PathVariable("id") Integer id);
}
