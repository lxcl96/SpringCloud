package com.ly.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * FileName:PaymentFallbackService.class
 * Author:ly
 * Date:2023/1/31 0031
 * Description: 继承openFeign接口，创建默认fallback方法（唯一区别就是名字相同）
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String ok() {
        return "openFeign的默认回调（服务端） ok方法降级";
    }

    @Override
    public String failure() {
        return "openFeign的默认回调（服务端） failure方法降级";
    }

    @Override
    public String exception() {
        return "openFeign的默认回调（服务端） exception方法降级";
    }
}
