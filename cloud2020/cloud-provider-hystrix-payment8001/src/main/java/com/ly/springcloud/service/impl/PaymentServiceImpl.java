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
        log.info("模拟正常业务逻辑...");
        return "ok";
    }

    @Override
    public String failure() throws InterruptedException {
        log.info("模拟出错业务逻辑...");
        TimeUnit.SECONDS.sleep(3);
        return "ok";
    }
}
