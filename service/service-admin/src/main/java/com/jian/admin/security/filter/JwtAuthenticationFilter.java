package com.jian.admin.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.admin.entity.User;
import com.jian.admin.service.UserService;
import com.jian.commonutils.ResponseCode;
import com.jian.servicebase.exception.BusinessException;
import com.jian.servicebase.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = JwtUtil.getRequestToken(request);
        if (ObjectUtils.isEmpty(jwt)){
            chain.doFilter(request,response);
            return;
        }

        //从jwt中获取用户id
        DecodedJWT decodedJWT = JwtUtil.verifyToken(jwt);
        String id = decodedJWT.getClaim("id").asString();
        if(id == null){
            throw new BusinessException(ResponseCode.FAIL,"jwt异常");
        }

        User user = userService.getOne(new QueryWrapper<User>().eq("userId", id));

        // TODO:获取用户权限信息

        //将认证信息封装给SpringSecurity
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request,response);


    }
}
