package com.ly.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName:MySelfRule.class
 * Author:ly
 * Date:2022/12/20 0020
 * Description: 自定义ribbon负载均衡选择服务器的规则（不与主项目同包）
 */
@Slf4j
@Configuration
public class MySelfRule {

    //组件RandomRule放入的不是spring的上下文，而是ribbon自己的上下文，所以不随spring容器初始化而创建，仅在调用负载均衡服务时创建
    //@Bean
    public IRule myRule(){
        //给ioc容器加入随机组件
        log.info("RandomRule 成功加入ioc。。。");
        return new RandomRule();
    }

    @Bean
    public IRule myRoundRule(){
        //给ioc容器加入随机组件
        log.info("MyRoundStrategy 成功加入ribbon ioc。。。");
        return new MyRoundStrategy();
    }
}
