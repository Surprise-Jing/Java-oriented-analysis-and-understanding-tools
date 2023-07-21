package com.nju.boot.slicer.exceptions;

/**
 * 异常类，切片时输入的行号不在任何自过程内
 */
public class MethodNotFoundException extends RuntimeException{
    public MethodNotFoundException() {
        super("输入行号不合法");
    }
}
