package com.ly.springcloud.lb.impl;

import com.ly.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FileName:MyRoundLoadBalancer.class
 * Author:ly
 * Date:2022/12/20 0020
 * Description: 基于自定义轮询规则的负载均衡器
 */
@Slf4j
@Component
public class MyRoundLoadBalancer implements LoadBalancer {
    private final AtomicInteger atomicInteger;

    public MyRoundLoadBalancer() {
        this(new AtomicInteger(0));
    }

    public MyRoundLoadBalancer(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    /**
     * 自定义轮询的核心算法(cas+自旋锁)
     * @param instances 所有在线的服务个数
     * @return 实例的下标
     */
    public int getAndIncrementIndex(int instances){
        int current;
        int next;

        do {
            current = atomicInteger.get() > Integer.MAX_VALUE ? 0 : atomicInteger.get();
            next = (current + 1) % instances;
        } while (!atomicInteger.compareAndSet(current,next));
        log.info("获取到实例下标为:{}",next);
        return next;
    }

    @Override
    public ServiceInstance getInstance(List<ServiceInstance> serviceInstances) {

        return serviceInstances.get(
                getAndIncrementIndex(serviceInstances.size())
        );
    }
}
