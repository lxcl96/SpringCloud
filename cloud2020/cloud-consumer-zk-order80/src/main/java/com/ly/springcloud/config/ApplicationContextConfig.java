package com.ly.springcloud.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * FileName:ApplicationContextConfig.class
 * Author:ly
 * Date:2022/12/15 0015
 * Description:
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * 使用@LoadBalanced注解的目的:
     *  1.负载均衡
     *  2.用zookeeper中服务名代替确定的ip:port
     * @return restTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
