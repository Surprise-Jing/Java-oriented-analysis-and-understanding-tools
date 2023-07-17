package com.nju.boot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 封装传入的method方法，使用ResponseBody方式传输
 * 因为传入的method会带（）和{}，在使用url方式传输时无法识别全部的method
 */
@Data
@AllArgsConstructor
public class MethodDto {
    private int id;

    private String methodName;
}
