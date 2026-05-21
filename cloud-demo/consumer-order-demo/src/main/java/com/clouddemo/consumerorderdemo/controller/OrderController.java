package com.clouddemo.consumerorderdemo.controller;

import com.clouddemo.consumerorderdemo.common.Result;
import com.clouddemo.consumerorderdemo.entity.Payment;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Say my name
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String PAYMENT_URL = "http://localhost:8080/payment";

    @GetMapping("/getOrder/{id}")
    public Result<?> getOrderById(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PAYMENT_URL + "/getPayment/"+id,Result.class);
    }

    @PostMapping("/addOrder")
    public Result<?> addOrder(@RequestBody Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/addPayment",payment,Result.class);
    }
}
