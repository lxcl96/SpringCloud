package com.ly.springcloud.controller;

import com.ly.springcloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * FileName:PaymentController.class
 * Author:ly
 * Date:2022/12/15 0015
 * Description: 一个简单的controller，用于展示服务是否成功注册进入consul
 */
@Slf4j
@RestController
public class PaymentController {
    @Value("${server.port}")
    private Integer port;


    @RequestMapping("/payment/consul")
    public CommonResult paymentZK(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("serverPort",port);
        map.put("serialNo", UUID.randomUUID().toString());
        return new CommonResult(
                200,
                "成功",
                map
        );
    }
}
