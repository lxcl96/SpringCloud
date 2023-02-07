package com.ly.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * FileName:HystrixDashboardMain9001.class
 * Author:ly
 * Date:2023/2/3 0003
 * Description:
 */
@EnableHystrixDashboard//开启hystrix dashboard
@SpringBootApplication
public class HystrixDashboardMain9001 {
    public static void main(String[] args){
      SpringApplication.run(HystrixDashboardMain9001.class, args);
    }
}
