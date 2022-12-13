package com.ly.springcloud.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FileName:ResultEnum.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 返回结果的枚举类
 */
@Getter
@AllArgsConstructor
public enum ReturnResultEnum {
    NORMAL(200,"成功"),
    INNER_ERROR(500,"内部错误"),
    NOT_FOUND(404,"未找到");

    private final Integer code;
    private final String message;

}
