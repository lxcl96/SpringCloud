package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * FileName:OrderConsulMain80.class
 * Author:ly
 * Date:2022/12/19 0019
 * Description: 注册到consul的 consumer
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OrderConsulMain80 {
   public static void main(String[] args){
     SpringApplication.run(OrderConsulMain80.class, args);
   }
}
