package com.jian.admin.security.filter;

import cn.hutool.json.JSONUtil;
import com.jian.commonutils.R;
import com.jian.servicebase.utils.RedisUtil;
import io.netty.util.internal.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 验证码过滤器
 */

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (url.equals("/login") && request.getMethod().equals("POST")){

            //校验验证码
            //获取前端传来的Redis的key
            String key = request.getParameter("key");
            //获取前端传来的验证码
            String code = request.getParameter("code");

            if (ObjectUtils.isEmpty(key) || ObjectUtils.isEmpty(code) || !redisUtil.hget("captcha",key).equals(code)){
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                String json = JSONUtil.toJsonStr(R.fail().message("验证码错误"));
                out.write(json);
                out.flush();
                out.close();
            }

        }

        //放行
        filterChain.doFilter(request,response);
    }
}
