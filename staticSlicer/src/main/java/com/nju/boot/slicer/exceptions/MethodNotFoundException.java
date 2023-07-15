package com.nju.boot.slicer.exceptions;

public class MethodNotFoundException extends RuntimeException{
    public MethodNotFoundException() {
        super("输入行号不合法");
    }
}
