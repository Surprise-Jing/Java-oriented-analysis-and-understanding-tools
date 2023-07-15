package com.ibm.jdi;

public class FileUnexecutableException extends RuntimeException{
    public FileUnexecutableException() {
        super("文件不可执行，请检查是否有main函数");
    }
}
