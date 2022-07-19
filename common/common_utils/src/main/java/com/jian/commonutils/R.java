package com.jian.commonutils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {

    private int code;
    private String message;
    private Object data;


    // 构造外部可调用的静态方法

    public static R success(){
        return new R(ResponseCode.SUCCESS, ResponseMsg.SUCCESS,null);
    }


    public static R fail(){
        return new R(ResponseCode.FAIL,ResponseMsg.FAIL,null);
    }

    //链式调用静态方法(自定义code、message、data)

    public R data(Object data){
        this.data = data;
        return this;
    }

    public R code(int code){
        this.code = code;
        return this;
    }

    public R message(String message){
        this.message = message;
        return this;
    }


}
