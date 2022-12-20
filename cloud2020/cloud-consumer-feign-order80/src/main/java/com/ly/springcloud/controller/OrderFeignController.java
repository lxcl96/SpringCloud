package com.ly.springcloud.controller;

import com.ly.springcloud.entities.CommonResult;
import com.ly.springcloud.entities.Payment;
import com.ly.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * FileName：OrderFeignController.java
 * Author：Ly
 * Date：2022/12/20
 * Description：
 */
@Slf4j
@RestController
public class OrderFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment) {
        log.info("consumer 接收到一条新增payment消息 serial={}",payment.getSerial());
        int ret = paymentFeignService.create(payment);

        return new CommonResult<>(200,"成功",ret);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentFeignService.getPaymentById(id);

        return new CommonResult<>(200,"成功",payment);
    }
}
