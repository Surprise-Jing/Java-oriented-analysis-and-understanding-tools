package com.nju.boot.slicer.exceptions;

public class VariableNotFoundException extends RuntimeException{
    public VariableNotFoundException() {
        super("在指定行找不到指定的变量");
    }
}
