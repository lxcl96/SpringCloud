package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * FileName:ConfigCenterMain3344.class
 * Author:ly
 * Date:2023/2/7 0007
 * Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer//启动配置中心
public class ConfigCenterMain3344 {
    public static void main(String[] args){
      SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}
