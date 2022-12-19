package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * FileName:PaymentMain8006.class
 * Author:ly
 * Date:2022/12/19 0019
 * Description: 注册到consul的主启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain18006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain18006.class,args);
    }
}
