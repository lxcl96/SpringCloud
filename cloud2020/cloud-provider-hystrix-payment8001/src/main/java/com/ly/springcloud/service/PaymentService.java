package com.ly.springcloud.service;

/**
 * FileName:PaymentService.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description: 服务接口
 */
public interface PaymentService {

    /**
     * 模拟服务正常业务
     * @return 结果
     */
    String ok();

    /**
     * 模拟服务会出错业务,此出采用超时
     *   1.运行异常
     *   2.超时
     *   3.服务熔断
     *   4.线程池满了
     * @return 结果
     */
    String failure() throws InterruptedException;

    /**
     * 模拟服务会出错业务,此出采用运行异常
     *   1.运行异常
     *   2.超时
     *   3.服务熔断
     *   4.线程池满了
     * @return 结果
     */
    String exception();
}
