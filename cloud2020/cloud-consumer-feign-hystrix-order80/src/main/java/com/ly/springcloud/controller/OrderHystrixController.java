package com.ly.springcloud.controller;

import com.ly.springcloud.service.PaymentHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * FileName:OrderHystrixController.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@RestController
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/failure")
    public String failure() {
        return paymentHystrixService.failure();
    }
    @GetMapping("/consumer/payment/hystrix/ok")
    public String ok() {
        return paymentHystrixService.ok();
    }
}
