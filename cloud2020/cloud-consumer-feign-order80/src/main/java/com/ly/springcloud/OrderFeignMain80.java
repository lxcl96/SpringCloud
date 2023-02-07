package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * FileName：OrderFeignMain80.java
 * Author：Ly
 * Date：2022/12/20
 * Description： 基于OpenFeign的consumer端主启动类
 */
@EnableEurekaClient////加不加都可以，都会自动注册到eureka中，这是因为自动配置类的效果
@EnableFeignClients
@SpringBootApplication
public class OrderFeignMain80 {
    public static void main(String[] args){
      SpringApplication.run(OrderFeignMain80.class, args);
    }
}
