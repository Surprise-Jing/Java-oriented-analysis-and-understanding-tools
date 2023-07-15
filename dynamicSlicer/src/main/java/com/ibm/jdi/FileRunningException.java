package com.ibm.jdi;

public class FileRunningException extends RuntimeException{
    public FileRunningException() {
        super("文件运行失败，请检查输入");
    }
}
