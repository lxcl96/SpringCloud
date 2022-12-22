package com.ly.springcloud.controller;

import com.ly.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * FileName:OrderHystrixController.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@Slf4j
@RestController
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    public String failureFallbackMethod() {
        log.info("客户端 [{}] 服务降级回调。",Thread.currentThread().getName());
        return "超时，请稍后再试！ ";
    }

    @HystrixCommand(
            fallbackMethod = "failureFallbackMethod",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    )
    @GetMapping("/consumer/payment/hystrix/failure")
    public String failure() {
        log.info("客户端 [{}] controller业务。",Thread.currentThread().getName());
        return paymentHystrixService.failure();
    }

    @GetMapping("/consumer/payment/hystrix/ok")
    public String ok() {
        return paymentHystrixService.ok();
    }
}
