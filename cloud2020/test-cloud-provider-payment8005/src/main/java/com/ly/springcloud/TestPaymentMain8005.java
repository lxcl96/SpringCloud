package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * FileName:TestPaymentMain8005.class
 * Author:ly
 * Date:2022/12/16 0016
 * Description:
 */
@EnableDiscoveryClient//只需要是需要连接到注册中心都加
@SpringBootApplication
public class TestPaymentMain8005 {
    public static void main(String[] args) {
        SpringApplication.run(TestPaymentMain8005.class,args);
    }
}
