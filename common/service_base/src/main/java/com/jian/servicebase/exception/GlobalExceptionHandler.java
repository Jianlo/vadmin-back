package com.jian.servicebase.exception;


import com.jian.commonutils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R common(Exception e){
        e.printStackTrace();
        return R.fail();
    }


    @ExceptionHandler(BusinessException.class)
    public R error(BusinessException e){
        e.printStackTrace();
        return R.fail().code(e.getCode()).message(e.getMsg());
    }
}
