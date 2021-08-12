package com.libv.exception;

import com.libv.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(IllegalStateException.class)
    public R illegalStateError() {
        return R.error().message("状态异常");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R illegalArgumentError() {
        return R.error().message("缺少必要参数");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R httpRequestMethodNotSupportedError() {
        return R.error().message("请求方法不支持");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R MethodArgumentTypeMismatchExceptionError() {
        return R.error().message("请求参数非法");
    }

    @ExceptionHandler(Exception.class)
    public R allError(Exception e) {
        log.error(e.getMessage());
        return R.error().message("出错了！请稍后再试");
    }
}
