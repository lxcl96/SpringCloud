package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * FileName:OrderHystrixMain80.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@SpringBootApplication
@EnableFeignClients
public class OrderHystrixMain80 {
    public static void main(String[] args){
      SpringApplication.run(OrderHystrixMain80.class, args);
    }
}
