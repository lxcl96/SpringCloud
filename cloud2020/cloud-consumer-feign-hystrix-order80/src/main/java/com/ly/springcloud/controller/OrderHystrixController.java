package com.ly.springcloud.controller;

import com.ly.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
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
//该注解放在服务端或客户端都可以，因为服务降级同时支持两端
@DefaultProperties(defaultFallback = "globalFallBack")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;
    //指定fallback
    public String failureFallbackMethod() {
        log.info("客户端 [{}] 服务降级回调。",Thread.currentThread().getName());
        return "超时，请稍后再试！ ";
    }
    //默认全局fallback，必须是无参数
    public String globalFallBack() {
        log.info("【global fallback】 客户端 [{}] 出现异常",Thread.currentThread().getName());
        return "【global fallback】 失败，请稍后再试！ ";
    }

    @HystrixCommand( //指定服务降级方法，不会调用全局的fallback了
            fallbackMethod = "failureFallbackMethod",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    )
    @GetMapping("/consumer/payment/hystrix/failure")
    public String failure() {
        log.info("客户端 [{}] controller业务。",Thread.currentThread().getName());
        return paymentHystrixService.failure();
    }

    //加上@HystrixCommand 表示该方法会进行服务降级，不指定降级方法会自动使用@DefaultProperties定义的方法
    @HystrixCommand
    @GetMapping("/consumer/payment/hystrix/global/failure")
    public String globalFailure() {
        int i = 10 / 0;
        return paymentHystrixService.ok();
    }

    //正常业务，不需要进行服务降级的
    @GetMapping("/consumer/payment/hystrix/ok")
    public String ok() {
        return paymentHystrixService.ok();
    }
}