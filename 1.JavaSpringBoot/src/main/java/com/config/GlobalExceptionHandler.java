package com.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.utils.R;

/**
 * 全局异常处理器：将所有未捕获的异常信息返回给前端，避免返回空白 500 错误
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        e.printStackTrace();
        String msg = e.getMessage();
        if (msg == null || msg.isEmpty()) {
            msg = "服务器内部错误：" + e.getClass().getSimpleName();
        }
        return R.error(msg);
    }
}
