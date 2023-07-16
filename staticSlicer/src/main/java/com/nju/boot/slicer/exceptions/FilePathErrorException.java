package com.nju.boot.slicer.exceptions;

public class FilePathErrorException extends RuntimeException{
    public FilePathErrorException() {
        super("找不到指定的文件");
    }
}
