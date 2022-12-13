package com.ly.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * FileName:Payment.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 对应数据库表payment,支付表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private String serial;

}
