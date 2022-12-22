package com.ly.springcloud.controller;

import com.ly.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    /**
     *  当前controller方法，必须会运行3s，大于设置的2s峰值，就会回调fallbackMethod方法
     *      此之谓服务降级（降级到出错提示）
     *      注解@HystrixCommand 定义服务降级，会隔离tomcat的线程池单独使用一个额外的线程
     */
    @HystrixCommand(
            //定义失败的回调函数
            fallbackMethod = "failureHandler",
            //定义业务线程的属性(如最大运行时间,超过此时间就会触发业务降级回调failureHandler)
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    )
    @GetMapping("/payment/hystrix/failure")
    public String failure() throws InterruptedException {
        log.info("线程：{} 正常业务逻辑controller：",Thread.currentThread().getName());
        return paymentService.failure();
    }

    public String failureHandler() {
        log.info("线程：{} 服务降级逻辑controller：",Thread.currentThread().getName());
        return "＞﹏＜";
    }

    @HystrixCommand(fallbackMethod = "failureHandler")
    @GetMapping("/payment/hystrix/exception")
    public String exception() {
        log.info("线程：{} 正常业务逻辑controller：",Thread.currentThread().getName());
        return paymentService.exception();
    }
}
