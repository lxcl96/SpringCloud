package com.ly.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * FileName:PaymentHystrixService.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@FeignClient(value = "cloud-provider-hystrix-payment",fallback = PaymentFallbackService.class)//注册到eureka的服务名
public interface PaymentHystrixService {
    //下面为对应的服务端的controller接口

    @GetMapping("/payment/hystrix/ok")
    String ok();

    @GetMapping("/payment/hystrix/failure")
    String failure();

    @GetMapping("/payment/hystrix/exception")
    String exception();
}
