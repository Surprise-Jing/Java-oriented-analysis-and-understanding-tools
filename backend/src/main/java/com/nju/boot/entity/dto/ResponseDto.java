package com.nju.boot.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(value = "api返回结果")
@AllArgsConstructor
public class ResponseDto<T> {

    private Integer code;

    private boolean success;

    private String msg;

    private T data;

    //默认正确处理，返回值为20000
    public ResponseDto() {
        this.code = 20000;
        this.success = true;
        this.msg = null;
        this.data = null;
    }

    //不附带msg返回数据
    public ResponseDto(T data) {
        this.code = 20000;
        this.success = true;
        this.msg = null;
        this.data = data;
    }

    //默认附带msg返回数据
    public ResponseDto(String msg, T data) {
        this.code = 20000;
        this.success = true;
        this.msg = msg;
        this.data = data;
    }

    //默认的错误返回
    public ResponseDto(Integer code, String msg) {
        this.code = code;
        this.success = false;
        this.msg = msg;
        this.data = null;
    }
}
