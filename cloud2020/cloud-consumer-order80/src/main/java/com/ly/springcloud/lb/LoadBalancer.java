package com.ly.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * FileName:LoadBalancer.class
 * Author:ly
 * Date:2022/12/20 0020
 * Description:
 */
public interface LoadBalancer {

    /**
     * 从指定服务中的所有实例中，按照自定义轮询规则取出 实例
     * @param serviceInstances 指定服务名的所有在线实例
     * @return 按照自定义轮询规则取出 可用的实例
     */
    ServiceInstance getInstance(List<ServiceInstance> serviceInstances);
}
