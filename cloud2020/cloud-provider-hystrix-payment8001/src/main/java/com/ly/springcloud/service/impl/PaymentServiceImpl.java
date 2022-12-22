package com.ly.springcloud.service.impl;


import com.ly.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * FileName:PaymentServiceImpl.class
 * Author:ly
 * Date:2022/12/21 0021
 * Description:
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String ok() {
        log.info("线程：{}模拟正常业务逻辑...",Thread.currentThread().getName());
        return "ok";
    }

    @Override
    public String failure() throws InterruptedException {
        log.info("线程：{}【开始】模拟出错业务逻辑service...",Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);
        log.info("线程：{}【结束】模拟出错业务逻辑service...",Thread.currentThread().getName());
        return "ok";
    }

    @Override
    public String exception() {
        log.info("线程：{}【开始】模拟异常业务逻辑service...",Thread.currentThread().getName());
        int i = 10 / 0;
        log.info("线程：{}【结束】模拟异常业务逻辑service...",Thread.currentThread().getName());
        return "ok";
    }
}
