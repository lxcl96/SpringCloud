package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * FileName:PaymentMain8004.class
 * Author:ly
 * Date:2022/12/15 0015
 * Description: provider8004 主启动类
 */
//该注解用于向使用consul或者zookeeper作为注册中心时注册服务
@EnableDiscoveryClient//谁连接，谁提供用
@SpringBootApplication
public class PaymentMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8004.class,args);
    }
}
