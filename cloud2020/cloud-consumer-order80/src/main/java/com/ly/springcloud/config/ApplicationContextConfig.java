package com.ly.springcloud.config;

import com.ly.myrule.MySelfRule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * FileName:ApplicationContextConfig.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 配置类，给容器中注入RestTemplate
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    //@LoadBalanced //否则无法直接识别eureka的服务名
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
