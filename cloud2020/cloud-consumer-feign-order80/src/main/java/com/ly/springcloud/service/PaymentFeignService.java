package com.ly.springcloud.service;

import com.ly.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FileName：PaymentFeignService.java
 * Author：Ly
 * Date：2022/12/20
 * Description： 与Provider端的service端接口完全一样的feign接口,而且必须加入ioc容器方便使用
 */
@Component
@FeignClient(name = "cloud-payment-service")//provider服务名
public interface PaymentFeignService {

    /**
     * 对应provider端Service接口方法，OpenFeign可以识别Springmvc注解（相当于生产端的controller和service层合并）
     * @param payment 参数对应的实体类
     * @return 大于0表示成功
     */
    @GetMapping("/payment/create")
    public int create(Payment payment);

    /**
     * 相当于provider生产端的controller和service层合并
     * @param id 查询id
     * @return 对应实体类
     */
    @GetMapping("/payment/get/{id}")
    public Payment getPaymentById(@PathVariable("id") Long id);
}
