package com.nju.boot.handler;

import com.nju.boot.entity.dto.ResponseDto;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一拦截Controller中所有方法的返回值
 * 封装后返回ResponseResult<T>
 */
@ControllerAdvice(basePackages = "com.nju.boot")
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class c) {
        //如果方法上带有DisableBaseResponse注解， 不处理返回false
        return !methodParameter.hasMethodAnnotation(DisableBaseResponse.class);
    }

    /**
     * 全局代码返回值封装成ResponseDto格式，返回值不能直接为String类型，需要再进行封装
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public ResponseDto<Object> beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                               ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return new ResponseDto<>();
        }
        return new ResponseDto<>(o);
    }

}