package com.ly.springcloud.service.impl;


import cn.hutool.core.util.IdUtil;
import com.ly.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * FileName:PaymentServiceImpl.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String ok() {
        log.info("线程：{}模拟正常业务逻辑...",Thread.currentThread().getName());
        return "ok";
    }

    @Override
    public String failure() throws InterruptedException {
        log.info("线程：{}【开始】模拟出错业务逻辑service...",Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);
        log.info("线程：{}【结束】模拟出错业务逻辑service...",Thread.currentThread().getName());
        return "ok";
    }

    @Override
    public String exception() {
        log.info("线程：{}【开始】模拟异常业务逻辑service...",Thread.currentThread().getName());
        int i = 10 / 0;
        log.info("线程：{}【结束】模拟异常业务逻辑service...",Thread.currentThread().getName());
        return "ok";
    }



    //=========服务熔断
    @HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback",
            commandProperties = {
                //是否启用断路器/服务熔断
                @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
                //触发断路后，该时间段内（20s）的任何请求（这一url，不管是否正确）都会被熔断即直接调用fallback返回
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "20000"),


                    /**
                     * 必须请求次数大于circuitBreaker.requestVolumeThreshold 即10
                     * 且失败率大于circuitBreaker.errorThresholdPercentage即60%才会触发服务降级
                     * 即 且/AND 关系
                     * 那么在设置的circuitBreaker.sleepWindowInMilliseconds 即20s时间内都会进行服务降级
                     * 超过20s后，断路器会进入半开状态，此时尝试调用正常服务，如果服务调用失败会重置为失败open状态
                     * （如果半开状态后紧接著调用正常，则短路器为close状态，【但还是会计算最近10s内，请求大于10，失败率大于60% 依然会进行服务熔断】）
                     */
                    //规定时间内统计，默认是最近10s 即快照时间窗 【下面requestVolumeThreshold和errorThresholdPercentage的规定时间就是这个】
                    //规定时间内统计，默认是最近10s 即快照时间窗 【下面requestVolumeThreshold和errorThresholdPercentage的规定时间就是这个】
                @HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "10"),
                //指定时间段内（默认10s）请求超过次阈值，熔断器（开关）将从close到open状态
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
                //指定时间段内（默认10s）请求次数的失败率（如异常）百分比达到此阈值，开启熔断
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
    })
    public String paymentCircuitBreaker(Integer id)
    {
        if(id < 0) throw new RuntimeException("******id 不能负数");
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }


}
