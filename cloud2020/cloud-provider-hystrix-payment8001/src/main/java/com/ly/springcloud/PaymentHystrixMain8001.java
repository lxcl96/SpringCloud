package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * FileName:PaymentHystrixMain8001.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description: 基于Hystrix的主启动类
 */
@EnableEurekaClient//可写可不写，默认开启
//@EnableHystrix
@SpringBootApplication
public class PaymentHystrixMain8001 {

    public static void main(String[] args){
      SpringApplication.run(PaymentHystrixMain8001.class, args);
    }
}
