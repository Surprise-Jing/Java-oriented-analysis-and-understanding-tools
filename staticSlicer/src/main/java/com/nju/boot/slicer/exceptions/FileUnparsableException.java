package com.nju.boot.slicer.exceptions;

public class FileUnparsableException extends RuntimeException{
    public FileUnparsableException() {
        super("无法从指定的文件生成ast树");
    }
}
