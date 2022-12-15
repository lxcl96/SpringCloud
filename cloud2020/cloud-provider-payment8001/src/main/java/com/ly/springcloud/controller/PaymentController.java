package com.ly.springcloud.controller;

import com.ly.springcloud.entities.CommonResult;
import com.ly.springcloud.entities.Payment;
import com.ly.springcloud.enums.ReturnResultEnum;
import com.ly.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName:PaymentController.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description:
 */
@Slf4j
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    // 搭配eureka server
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/discovery")
    public Object discovery() {
        //获取eureka server上注册在线的所有服务信息如cloud-payment-service和cloud-order-service
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("微服务名：{}",service);
        }

        //根据服务名，获取所有的微服务信息 如payment80001的所有信息
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        instances.forEach(consumer -> {
            log.info(
                    "微服务：host={},instanceId={},metaData={},port={},serviceId={},uri={}",
                    consumer.getHost(),
                    consumer.getInstanceId(),
                    consumer.getMetadata(),
                    consumer.getPort(),
                    consumer.getServiceId(),
                    consumer.getUri()
            );
        });
        return this.discoveryClient;
    }
    // provider端必须是从@RequestBody 请求体获取参数，因为请求被封在请求体中，而不是直链
    @PostMapping(path = {"/payment/create"})
    public CommonResult create(@RequestBody Payment payment) {
        //map存放返回的信息
        Map<String,Object> map = new HashMap<>();
        map.put("serverPort",serverPort);

        int ret = paymentService.create(payment);
        if (ret > 0) {
           //插入成功
            return new CommonResult(
                    ReturnResultEnum.NORMAL.getCode(),
                    ReturnResultEnum.NORMAL.getMessage(),
                    map
            );
        }

        log.error("插入数据库失败");
        return new CommonResult(
                ReturnResultEnum.INNER_ERROR.getCode(),
                ReturnResultEnum.INNER_ERROR.getMessage(),
                map
                );
    }

    @GetMapping(path = {"/payment/get/{id}"})
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        //map存放返回的信息
        Map<String,Object> map = new HashMap<>();
        map.put("serverPort",serverPort);

        if (null == id || 0 >= id) {
            log.info("查询的id {} 不正确",id);

            return new CommonResult(
                    ReturnResultEnum.NOT_FOUND.getCode(),
                    ReturnResultEnum.NOT_FOUND.getMessage(),
                    map
            );
        }


        Payment payment = paymentService.getPaymentById(id);
        if (null != payment) {
            map.put("payment_id_" + id,payment);
            return new CommonResult(
                    ReturnResultEnum.NORMAL.getCode(),
                    ReturnResultEnum.NORMAL.getMessage(),
                    map
            );
        }

        //失败
        return new CommonResult(
                ReturnResultEnum.NOT_FOUND.getCode(),
                ReturnResultEnum.NOT_FOUND.getMessage(),
                map
        );
    }
}
