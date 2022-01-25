package com.tunbor.beye.advice;

import com.tunbor.beye.annotation.IgnoreWrapResponse;
import com.tunbor.beye.dto.AppResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Olakunle Awotunbo
 */
@RestControllerAdvice(basePackages = "com.tunbor.beye.controller")
public class WrapResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterClass) {
        return methodParameter.getMethod() == null ||
                methodParameter.getMethod().getDeclaredAnnotation(IgnoreWrapResponse.class) == null ||
                methodParameter.getDeclaringClass().getDeclaredAnnotation(IgnoreWrapResponse.class) == null;
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return object instanceof AppResponse ? object : AppResponse.createSuccess(object);
    }
}
