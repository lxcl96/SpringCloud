package com.ly.springcloud.controller;

import com.ly.springcloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * FileName:OrderZkController.class
 * Author:ly
 * Date:2022/12/15 0015
 * Description:
 */
@Slf4j
@RestController
public class OrderZkController {
    @Autowired
    private RestTemplate restTemplate;
    private static final String url = "http://cloud-provider-payment";

    @RequestMapping("/consumer/payment/{id}")
    public CommonResult consume(@PathVariable("id") Integer id) {
        log.info("接收到请求：id={}",id);
        return restTemplate.getForObject(
                //第一个记得加/
            url + "/payment/zk",
                CommonResult.class
        );
    }

}
