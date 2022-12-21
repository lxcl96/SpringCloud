package com.ly.springcloud.controller;

import com.ly.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * FileName:PaymentController.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/payment/hystrix/ok")
    public String ok() {
        return paymentService.ok();
    }

    @GetMapping("/payment/hystrix/failure")
    public String failure() throws InterruptedException {
        return paymentService.failure();
    }
}
