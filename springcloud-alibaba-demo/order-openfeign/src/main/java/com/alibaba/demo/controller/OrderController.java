package com.alibaba.demo.controller;

import com.alibaba.demo.service.ProductFeignService;
import com.alibaba.demo.service.StockFeignService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Say my name
 */
@RestController
@RequestMapping("/order")
public class OrderController {
//    @Autowired
//    private RestTemplate restTemplate;
    @Resource
    private StockFeignService stockFeignService;
    @Resource
    private ProductFeignService productFeignService;

    @RequestMapping("/add")
    public String add(){
        System.out.println("下单成功");
//        String message = restTemplate.getForObject("http://stock-service/stock/reduce", String.class);
        String message = stockFeignService.reduce();
        String p = productFeignService.get(2);

        return "下单成功"+message+p;
    }
}
