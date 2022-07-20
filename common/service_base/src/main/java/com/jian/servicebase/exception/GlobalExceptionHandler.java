package com.jian.servicebase.exception;


import com.jian.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public R error(BusinessException e){
        e.printStackTrace();
        return R.fail().code(e.getCode()).message(e.getMsg());
    }
}
