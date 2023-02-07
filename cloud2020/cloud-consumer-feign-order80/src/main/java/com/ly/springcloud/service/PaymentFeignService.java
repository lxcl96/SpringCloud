package com.ly.springcloud.service;

import com.ly.springcloud.entities.CommonResult;
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
     *      返回值必须和service对应的controller的返回值完全一样，否则会导致数据无法封装为null
     * @param payment 参数对应的实体类
     * @return  结果集
     */
    @GetMapping("/payment/create")
    CommonResult create(Payment payment);

    /**
     * 相当于provider生产端的controller和service层合并
     *      返回值必须和service对应的controller的返回值完全一样，否则会导致数据无法封装为null
     *      |比如provider的返回值是 CommonResult|
     *      |我写 CommonResult<Payment> 错     |
     *      |    Payment 错                   |
     * @param id 查询id
     * @return 结果集
     */
    @GetMapping("/payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id") Long id);

    /**
     * 模拟业务端复杂耗时业务
     * @return 结果
     */
    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeout();
}
