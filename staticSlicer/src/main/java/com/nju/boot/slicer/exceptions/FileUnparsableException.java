package com.nju.boot.slicer.exceptions;

/**
 * 异常类，文件非javaparser可parse的文件
 */
public class FileUnparsableException extends RuntimeException{
    public FileUnparsableException() {
        super("无法从指定的文件生成ast树");
    }
}
