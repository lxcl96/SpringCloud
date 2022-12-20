package com.ly.springcloud.controller;

import com.ly.springcloud.entities.CommonResult;
import com.ly.springcloud.entities.Payment;
import com.ly.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * FileName:OrderController.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 消费者服务
 */
@Slf4j
@RestController
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001";
    //用eureka的服务名代替真实ip，当其中一个provider掉线或者繁忙就会自动调用另一个provider
    public static final String PAYMENT_URL = "http://cloud-payment-service";
    public static final String APPLICATION_NAME = "cloud-payment-service";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LoadBalancer loadBalancer;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("consumer 接收到一条新增payment消息 serial={}",payment.getSerial());
        //调用cloud-payment-service(请求地址，参数，返回消息的内容)
        return restTemplate.postForObject(
                PAYMENT_URL + "/payment/create",
                payment,
                CommonResult.class
        );
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        //log.info("consumer 接收到一条请求payment消息 id={}",id);
        //(请求地址，返回消息的内容)
        return restTemplate.getForObject(
                PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/lb/get/{id}")
    public CommonResult<Payment> getPaymentByIdOfLB(@PathVariable("id") Long id){

        ServiceInstance instance = loadBalancer.getInstance(
                discoveryClient.getInstances(APPLICATION_NAME)
        );

        return restTemplate.getForObject(
                instance.getUri() + "/payment/get/" + id, CommonResult.class);
    }
}
