package com.nju.boot.slicer.exceptions;

/**
 * 异常类，切片时输入的变量名不在指定行
 */
public class VariableNotFoundException extends RuntimeException{
    public VariableNotFoundException() {
        super("在指定行找不到指定的变量");
    }
}
