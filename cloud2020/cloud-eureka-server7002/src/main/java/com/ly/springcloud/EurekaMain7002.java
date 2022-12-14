package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * FileName:EurekaMain7001.class
 * Author:ly
 * Date:2022/12/14 0014
 * Description: Eureka server主程序
 */
@SpringBootApplication
@EnableEurekaServer //表明当前服务是一个eureka的sever
public class EurekaMain7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7002.class,args);
    }
}
