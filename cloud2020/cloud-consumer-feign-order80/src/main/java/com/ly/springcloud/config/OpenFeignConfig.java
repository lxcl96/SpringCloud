package com.ly.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName:OpenFeignConfig.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@Configuration
public class OpenFeignConfig {

    @Bean
    public Logger.Level openFeignLogLevel() {
        //需要什么等级的日志，就将该等级对应的枚举类加入ioc容器
        return Logger.Level.FULL;
    }
}
