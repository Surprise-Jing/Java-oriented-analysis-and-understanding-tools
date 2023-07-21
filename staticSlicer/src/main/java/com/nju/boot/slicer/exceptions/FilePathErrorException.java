package com.nju.boot.slicer.exceptions;

/**
 * 异常类，文件路径传输错误
 */

public class FilePathErrorException extends RuntimeException{
    public FilePathErrorException() {
        super("找不到指定的文件");
    }
}
