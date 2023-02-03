package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * FileName:GateWayMain9527.class
 * Author:ly
 * Date:2023/2/3 0003
 * Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class GateWayMain9527 {
    public static void main(String[] args){
      SpringApplication.run(GateWayMain9527.class, args);
    }
}
