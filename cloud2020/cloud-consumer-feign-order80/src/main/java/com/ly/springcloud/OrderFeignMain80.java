package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * FileName：OrderFeignMain80.java
 * Author：Ly
 * Date：2022/12/20
 * Description： 基于OpenFeign的consumer端主启动类
 */
@EnableFeignClients//代替@RibbonClient和@EurekaClient
@SpringBootApplication
public class OrderFeignMain80 {
    public static void main(String[] args){
      SpringApplication.run(OrderFeignMain80.class, args);
    }
}
