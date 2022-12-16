package com.ly.springcloud.controller;

import com.ly.springcloud.entities.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * FileName:TestPaymentController.class
 * Author:ly
 * Date:2022/12/16 0016
 * Description:
 */
@RestController
public class TestPaymentController {
    @Value("${server.port}")
    private Integer port;


    @RequestMapping("/payment/zk")
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
