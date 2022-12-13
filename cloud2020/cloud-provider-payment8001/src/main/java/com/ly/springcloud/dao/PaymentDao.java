package com.ly.springcloud.dao;

import com.ly.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * FileName:Payment.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: dao主类方便使用mapper文件（整合mybatis）
 *  使用@Mapper注解，代替配置文件映射
 */
@Mapper
public interface PaymentDao {

    /**
     * 新增支付信息
     * @param payment 支付实体类
     * @return 结果
     */
    public int create(Payment payment);

    /**
     * 根据id获取支付单据
     * @param id 支付单据id
     * @return payment
     */
    public Payment getPaymentById(@Param("id") Long id);
}
