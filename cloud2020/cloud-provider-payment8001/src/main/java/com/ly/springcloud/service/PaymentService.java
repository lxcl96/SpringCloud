package com.ly.springcloud.service;

import com.ly.springcloud.entities.Payment;

/**
 * FileName:PaymentService.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 支付业务层
 */
public interface PaymentService {

    /**
     * 新增支付信息
     * @param payment 支付实体类
     * @return 结果
     */
    public int  create(Payment payment);

    /**
     * 根据id获取支付单据
     * @param id 支付单据id
     * @return payment
     */
    public Payment getPaymentById(Long id);
}
