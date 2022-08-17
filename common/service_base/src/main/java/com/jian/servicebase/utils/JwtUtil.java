package com.jian.servicebase.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

public class JwtUtil {

    private static final String SECRET = "rewaf!Q@W#E$R";


    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("Authorization");
        return token;
    }

    /**
     * 生产token
     */
    public static String createToken(String id) {
        JWTCreator.Builder builder = JWT.create();

        //把用户id存在jwt的载荷中
        builder.withClaim("id",id);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); //默认7天过期
        builder.withExpiresAt(instance.getTime());//指定令牌的过期时间
        String token = builder.sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证token
     */
    public static DecodedJWT verifyToken(String token) {
        //如果有任何验证异常，此处都会抛出异常
        //verify方法用于校验token
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        return decodedJWT;
    }
}
