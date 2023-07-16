package com.nju.boot.handler;

import com.nju.boot.entity.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 捕获controller异常
 * controller抛出异常执行下边的函数
 * 返回Response写入ApiResult
 */
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    @DisableBaseResponse
    public Object handleException(Exception e) {
        // 处理全局抛出的Exception
        if (e.getClass().equals(AccessDeniedException.class)){
            return new ResponseDto<>(403, "你没有访问权限");
        }
        logger.error(e.getMessage());
        return new ResponseDto<>(400, e.getMessage());
    }

}