package com.ibm.jdi;

public class FileTimeLimitExceedException extends RuntimeException{
    public FileTimeLimitExceedException() {
        super("程序运行时间过长！");
    }
}
