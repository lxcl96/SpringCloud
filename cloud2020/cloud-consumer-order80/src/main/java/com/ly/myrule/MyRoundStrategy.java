package com.ly.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FileName:MyRoundStrategy.class
 * Author:ly
 * Date:2022/12/20 0020
 * Description:
 */
@Slf4j
public class MyRoundStrategy extends AbstractLoadBalancerRule {
    private final AtomicInteger atomicInteger;

    public MyRoundStrategy() {
        this(new AtomicInteger(0));
    }

    public MyRoundStrategy(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    public MyRoundStrategy(ILoadBalancer lb) {
        this();
        setLoadBalancer(lb);
    }

    /**
     * 自定义轮询的核心算法(cas+自旋锁)
     * @param instances 所有在线的服务个数
     * @return 实例的下标
     */
    public int getAndIncrementIndex(int instances,Object key){
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
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
    public Server choose(ILoadBalancer lb,Object key) {
        int index = getAndIncrementIndex(lb.getAllServers().size(), key);
        return lb.getAllServers().get(index);
    }
    @Override
    public Server choose(Object key) {
        //log.info("key={}",key);
        return choose(getLoadBalancer(),key);
    }
}
