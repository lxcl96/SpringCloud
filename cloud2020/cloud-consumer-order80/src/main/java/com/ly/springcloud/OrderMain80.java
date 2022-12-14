package com.ly.springcloud;

import com.ly.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * FileName:OrderMain80.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 消费者端
 */
@EnableDiscoveryClient
//@EnableEurekaClient
@SpringBootApplication
@RibbonClient(name = "cloud-payment-service",configuration = MySelfRule.class)
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
