package com.ly.springcloud.service.impl;

import com.ly.springcloud.dao.PaymentDao;
import com.ly.springcloud.entities.Payment;
import com.ly.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FileName:PaymentServiceImpl.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description:
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;


    @Override
    public int create(Payment payment) {
        assert payment !=null :"error,payment is null!";
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        assert id != null:"error, id is null!";
        return paymentDao.getPaymentById(id);
    }
}
