package com.ly.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName:CommonResult.class
 * Author:ly
 * Date:2022/12/13 0013
 * Description: 专门用于传递给前端的数据json封装体，可以理解为vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    //404 NOT FOUND
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code,message,null);
    }
}
